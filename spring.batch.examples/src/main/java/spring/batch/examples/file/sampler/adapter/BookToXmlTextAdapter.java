package spring.batch.examples.file.sampler.adapter;

import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import spring.batch.examples.file.sampler.model.Books;

@Component
public class BookToXmlTextAdapter implements BookTextFormatAdapter<Books>{

	@Autowired
	private Jaxb2Marshaller jaxb2Marshaller;
	
	
	public String convertBookToText(Books bookHolder) {
		
		if(bookHolder == null) return "";
		
		StreamResult streamResult = new StreamResult(new StringWriter());
		
		jaxb2Marshaller.marshal(bookHolder, streamResult);
		
		return streamResult.getWriter().toString();
	}
}
