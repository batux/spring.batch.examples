package spring.batch.examples.file.sampler;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import spring.batch.examples.file.sampler.model.Book;

@Component
public class BookProducer {

	private final ThreadLocal<Random> randomValueGenerator;
	
	public BookProducer() {
		
		randomValueGenerator = new ThreadLocal<Random>(){
			@Override
			protected Random initialValue() {
				return new Random();
			}
		};
	}
	
	public Book produceSampleBook() {
		
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		double price = this.randomValueGenerator.get().nextDouble() * 80;
		String publishedYear = String.valueOf(ThreadLocalRandom.current().nextInt(1900, 2018));
		
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setPublishedYear(publishedYear);
		
		return book;
	}
	
}
