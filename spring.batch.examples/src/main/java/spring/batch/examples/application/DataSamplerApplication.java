package spring.batch.examples.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.batch.examples.configuration.SpringConfiguration;

public class DataSamplerApplication {

	public static void main(String[] args) throws InterruptedException {

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		
		BookDataSampler bookDataSampler = applicationContext.getBean(BookDataSampler.class);
		bookDataSampler.generateSampleData();
		
	}

}
