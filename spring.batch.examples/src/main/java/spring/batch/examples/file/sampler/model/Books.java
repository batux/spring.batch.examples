package spring.batch.examples.file.sampler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="books")
@XmlAccessorType(XmlAccessType.FIELD)
public class Books implements Serializable {

	private static final long serialVersionUID = 7410345309719104514L;
	
	@XmlElement(name = "book")
	private List<Book> books;
	
	public Books() {
		this.setBooks(new ArrayList<Book>());
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Books [books=" + books + "]";
	}
	
}
