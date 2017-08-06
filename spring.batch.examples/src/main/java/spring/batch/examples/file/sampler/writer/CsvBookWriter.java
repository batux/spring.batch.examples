package spring.batch.examples.file.sampler.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import spring.batch.examples.file.sampler.model.Book;

@Component
public class CsvBookWriter implements ItemWriter<Book>{

	@Override
	public void write(List<? extends Book> books) throws Exception {

		if(books == null) {
			System.out.println("Books is empty!");
			return;
		}
		
		for(Book book : books) {
			System.out.println(book);
		}
		
	}

}
