package spring.batch.examples.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import spring.batch.examples.application.JobRunner;
import spring.batch.examples.file.sampler.model.Book;
import spring.batch.examples.file.sampler.model.Books;
import spring.batch.examples.file.sampler.writer.CsvBookWriter;
import spring.batch.examples.file.sampler.writer.XmlBookWriter;

@Configuration
@EnableBatchProcessing
@ComponentScan("spring.batch.examples")
public class SpringConfiguration {

	@Autowired
    private JobBuilderFactory jobBuilders;
     
    @Autowired
    private StepBuilderFactory stepBuilders;
    
    

    @Bean
    public JobRunner jobRunner() {
    	
    	return new JobRunner();
    }
    
    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) {
    	
    	SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
    	simpleJobLauncher.setJobRepository(jobRepository);
    	
    	return simpleJobLauncher;
    }
    
    @Bean
    public Job jobForXmlReadingProcess() {
    	
        return jobBuilders.get("jobForXmlReadingProcess")
                .start(stepForXmlReadingProcess())
                .next(stepForCsvReadingProcess())
                .build();
    }
     
    @Bean
    public Step stepForXmlReadingProcess() {
    	
        return stepBuilders.get("stepForXmlProcess")
        		.<Books,Books>chunk(1)
        		.reader(bookReaderFromXmlFile())
        		.writer(bookWriterFromXmlFile())
                .build();
    }
    
    @Bean
    public Step stepForCsvReadingProcess() {
    	
        return stepBuilders.get("stepForCsvProcess")
        		.<Book,Book>chunk(1)
        		.reader(bookReaderFromCsvFile())
        		.writer(bookWriterFromCsvFile())
                .build();
    }
    
    @Bean
    public ItemReader<Books> bookReaderFromXmlFile() {
    	
    	StaxEventItemReader<Books> xmlFileReader = new StaxEventItemReader<Books>();
        xmlFileReader.setResource(new ClassPathResource("Books.xml"));
        xmlFileReader.setFragmentRootElementName("books");
  
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(Books.class);
        xmlFileReader.setUnmarshaller(studentMarshaller);
  
        return xmlFileReader;
    }
    
    @Bean
    public ItemReader<Book> bookReaderFromCsvFile() {
    	
        FlatFileItemReader<Book> csvFileReader = new FlatFileItemReader<Book>();
        csvFileReader.setResource(new ClassPathResource("Books.csv"));
        csvFileReader.setLinesToSkip(1);
 
        LineMapper<Book> studentLineMapper = createStudentLineMapper();
        csvFileReader.setLineMapper(studentLineMapper);
 
        return csvFileReader;
    }
 

    @Bean 
    public ItemWriter<Book> bookWriterFromCsvFile() {
    	
    	return new CsvBookWriter();
    }
    
    
    @Bean 
    public ItemWriter<Books> bookWriterFromXmlFile() {
    	
    	return new XmlBookWriter();
    }
    
	
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		
	  Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
	  jaxb2Marshaller.setPackagesToScan("spring.batch.examples");
	  
	  Map<String,Object> properties = new HashMap<String,Object>();
	  properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	  properties.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	  
	  jaxb2Marshaller.setMarshallerProperties(properties);
         
	  return jaxb2Marshaller;
	}
	
	@Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public MapJobRepositoryFactoryBean mapJobRepositoryFactory(ResourcelessTransactionManager txManager) throws Exception {
        
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(txManager);
        factory.afterPropertiesSet();
        
        return factory;
    }

    @Bean
    public JobRepository jobRepository(MapJobRepositoryFactoryBean factory) throws Exception {
        return factory.getObject();
    }
    
    
    // INFO: Helper Functions ...
    private LineMapper<Book> createStudentLineMapper() {
    	
        DefaultLineMapper<Book> studentLineMapper = new DefaultLineMapper<>();
 
        LineTokenizer studentLineTokenizer = createStudentLineTokenizer();
        studentLineMapper.setLineTokenizer(studentLineTokenizer);
 
        FieldSetMapper<Book> studentInformationMapper = createStudentInformationMapper();
        studentLineMapper.setFieldSetMapper(studentInformationMapper);
 
        return studentLineMapper;
    }
 
    private LineTokenizer createStudentLineTokenizer() {
    	
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(";");
        studentLineTokenizer.setNames(new String[]{"name", "price", "publishedYear"});
        
        return studentLineTokenizer;
    }
 
    private FieldSetMapper<Book> createStudentInformationMapper() {
    	
        BeanWrapperFieldSetMapper<Book> studentInformationMapper = new BeanWrapperFieldSetMapper<>();
        studentInformationMapper.setTargetType(Book.class);
        return studentInformationMapper;
    }

}
