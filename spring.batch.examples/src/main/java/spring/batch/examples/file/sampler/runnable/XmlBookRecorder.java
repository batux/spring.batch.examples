package spring.batch.examples.file.sampler.runnable;

import java.io.IOException;

import spring.batch.examples.file.sampler.BookProducer;
import spring.batch.examples.file.sampler.FileProcessor;
import spring.batch.examples.file.sampler.RecordedBookCounter;
import spring.batch.examples.file.sampler.adapter.BookTextFormatAdapter;
import spring.batch.examples.file.sampler.model.Book;
import spring.batch.examples.file.sampler.model.Books;

public class XmlBookRecorder implements Runnable{

	private final FileProcessor fileProcessor;
	
	private final BookProducer bookProducer;
	
	private final BookTextFormatAdapter<Books> bookTextFormatAdapter;
	
	private final RecordedBookCounter recordedBookCounter;
	
	
	public XmlBookRecorder(RecordedBookCounter recordedBookCounter, 
						BookTextFormatAdapter<Books> bookTextFormatAdapter, 
						BookProducer bookProducer, 
						FileProcessor fileProcessor) {
		
		this.bookProducer = bookProducer;
		this.fileProcessor = fileProcessor;
		this.recordedBookCounter = recordedBookCounter;
		this.bookTextFormatAdapter = bookTextFormatAdapter;
		
		this.fileProcessor.open();
	}
	
	public void run() {
		
		final Books bookHolder = new Books();
		
		while(this.recordedBookCounter.getCurrentValue() < 100) {
			
			try {
				
				synchronized (this) {
					
					Book book = this.bookProducer.produceSampleBook();
					
					book.setName(this.recordedBookCounter.getCurrentValue() + "___" + book.getName());
					
					bookHolder.getBooks().add(book);
					
					this.recordedBookCounter.increment();
				}
				
				Thread.sleep(2);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		try {

			String formattedBookText = this.bookTextFormatAdapter.convertBookToText(bookHolder);
		
			this.fileProcessor.write(formattedBookText);
			
			this.fileProcessor.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
