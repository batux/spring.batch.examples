package spring.batch.examples.file.sampler.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="book")
public class Book implements Serializable{

	private static final long serialVersionUID = 1931776268011117719L;

	private String name;
	private Double price;
	private String publishedYear;
	
	public Book() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(String publishedYear) {
		this.publishedYear = publishedYear;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", price=" + price + ", publishedYear=" + publishedYear + "]";
	}
	
}
