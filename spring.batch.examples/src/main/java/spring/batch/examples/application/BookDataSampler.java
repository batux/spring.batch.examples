package spring.batch.examples.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import spring.batch.examples.configuration.SpringConfiguration;
import spring.batch.examples.file.sampler.BookProducer;
import spring.batch.examples.file.sampler.FileProcessor;
import spring.batch.examples.file.sampler.RecordedBookCounter;
import spring.batch.examples.file.sampler.adapter.BookTextFormatAdapter;
import spring.batch.examples.file.sampler.adapter.BookToCsvTextAdapter;
import spring.batch.examples.file.sampler.adapter.BookToXmlTextAdapter;
import spring.batch.examples.file.sampler.model.Book;
import spring.batch.examples.file.sampler.model.Books;
import spring.batch.examples.file.sampler.runnable.CsvBookRecorder;
import spring.batch.examples.file.sampler.runnable.XmlBookRecorder;

@Component
public class BookDataSampler {

	
	public void generateSampleData() throws InterruptedException {
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);

		
		BookProducer bookProducer = applicationContext.getBean(BookProducer.class);
		RecordedBookCounter recordedBookCounter = applicationContext.getBean(RecordedBookCounter.class);
		BookTextFormatAdapter<Books> bookXmlTextFormatAdapter = applicationContext.getBean(BookToXmlTextAdapter.class);
		BookTextFormatAdapter<Book> bookCsvTextFormatAdapter = applicationContext.getBean(BookToCsvTextAdapter.class);
		
		
		
		CsvBookRecorder bookRecorderForCsvFiles = new CsvBookRecorder(recordedBookCounter, bookCsvTextFormatAdapter, bookProducer, new FileProcessor("Books.csv"));
		
		Thread bookRecorderThreadForCsvFiles = new Thread(bookRecorderForCsvFiles);
		bookRecorderThreadForCsvFiles.setDaemon(true);
		bookRecorderThreadForCsvFiles.start();
		
		
		XmlBookRecorder bookRecorderForXmlFiles = new XmlBookRecorder(recordedBookCounter, bookXmlTextFormatAdapter, bookProducer, new FileProcessor("Books.xml"));
		
		Thread bookRecorderThreadForXmlFiles = new Thread(bookRecorderForXmlFiles);
		bookRecorderThreadForXmlFiles.setDaemon(true);
		bookRecorderThreadForXmlFiles.start();
		
		
		Thread.sleep(1000);
		
	}
	
}
