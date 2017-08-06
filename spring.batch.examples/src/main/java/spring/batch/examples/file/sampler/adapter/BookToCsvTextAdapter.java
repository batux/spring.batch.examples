package spring.batch.examples.file.sampler.adapter;

import org.springframework.stereotype.Component;

import spring.batch.examples.file.sampler.model.Book;

@Component
public class BookToCsvTextAdapter implements BookTextFormatAdapter<Book>{

	public String convertBookToText(Book book) {
		
		if(book == null) return "";
		
		String bookInCsvFormat = book.getName() + ";" + book.getPrice() + ";" + book.getPublishedYear();
		
		return bookInCsvFormat;
	}
}
