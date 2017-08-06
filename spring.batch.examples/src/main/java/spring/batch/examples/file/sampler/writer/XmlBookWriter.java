package spring.batch.examples.file.sampler.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import spring.batch.examples.file.sampler.model.Book;
import spring.batch.examples.file.sampler.model.Books;

@Component
public class XmlBookWriter implements ItemWriter<Books>{


	@Override
	public void write(List<? extends Books> books) throws Exception {
		
		if(books == null) {
			System.out.println("Books is empty!");
			return;
		}
		
		for(Book book : books.get(0).getBooks()) {
			System.out.println(book);
		}

	}

}
