package spring.batch.examples.file.sampler;

import org.springframework.stereotype.Component;

@Component
public class RecordedBookCounter {

	private final ThreadLocal<Integer> recordedBookCounter;
	
	
	public RecordedBookCounter() {
		
		this.recordedBookCounter = new ThreadLocal<Integer>() {
			@Override
			protected Integer initialValue() {
				return 0;
			}
		};
	}
	
	public Integer getCurrentValue() {
		
		return this.recordedBookCounter.get();
		
	}
	
	public void increment() {
		
		recordedBookCounter.set(recordedBookCounter.get() + 1);
		
	}
	
}
