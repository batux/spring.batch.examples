package spring.batch.examples.file.sampler.runnable;

import java.io.IOException;

import spring.batch.examples.file.sampler.BookProducer;
import spring.batch.examples.file.sampler.FileProcessor;
import spring.batch.examples.file.sampler.RecordedBookCounter;
import spring.batch.examples.file.sampler.adapter.BookTextFormatAdapter;
import spring.batch.examples.file.sampler.model.Book;

public class CsvBookRecorder implements Runnable{

	private final FileProcessor fileProcessor;
	
	private final BookProducer bookProducer;
	
	private final BookTextFormatAdapter<Book> bookTextFormatAdapter;
	
	private final RecordedBookCounter recordedBookCounter;
	
	
	public CsvBookRecorder(RecordedBookCounter recordedBookCounter, 
						BookTextFormatAdapter<Book> bookTextFormatAdapter, 
						BookProducer bookProducer, 
						FileProcessor fileProcessor) {
		
		this.bookProducer = bookProducer;
		this.fileProcessor = fileProcessor;
		this.recordedBookCounter = recordedBookCounter;
		this.bookTextFormatAdapter = bookTextFormatAdapter;
		
		this.fileProcessor.open();
	}
	
	public void run() {
		
		while(this.recordedBookCounter.getCurrentValue() < 100) {
			
			try {
				
				synchronized (this) {
					
					Book book = this.bookProducer.produceSampleBook();
					
					book.setName(this.recordedBookCounter.getCurrentValue() + "___" + book.getName());
					
					String formattedBookText = this.bookTextFormatAdapter.convertBookToText(book);
					
					this.fileProcessor.write(formattedBookText);
					
					this.recordedBookCounter.increment();
				}
				
				Thread.sleep(2);
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.fileProcessor.close();
	}

}
