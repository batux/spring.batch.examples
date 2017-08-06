package spring.batch.examples.file.sampler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {

	private File file;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	
	private volatile boolean opened;
	
	private String filePath;
	private String newLineCharacter = System.getProperty("line.separator");
	
	public FileProcessor(String fileName) {
		
		this.filePath = "src/main/resources/" + fileName;
		this.setOpened(false);
	}
	
	public synchronized void write(String data) throws IOException {

		this.bufferedWriter.write(newLineCharacter + data);
		this.bufferedWriter.flush();
	}
	
	public synchronized void open() {
		
		try {
			
			this.file = new File(this.filePath);
			
			if (!this.file.exists()) {
				this.file.createNewFile();
			}
			
			this.fileWriter = new FileWriter(this.file.getAbsolutePath(), true);
			this.bufferedWriter = new BufferedWriter(this.fileWriter);
			
			this.setOpened(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void close() {
		
		try {
			
			if(this.bufferedWriter != null) {
				this.setOpened(false);
				this.bufferedWriter.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public synchronized boolean isOpened() {
		return opened;
	}

	public synchronized void setOpened(boolean opened) {
		this.opened = opened;
	}

}
