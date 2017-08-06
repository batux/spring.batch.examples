package spring.batch.examples.file.sampler.adapter;

public interface BookTextFormatAdapter<T> {

	public String convertBookToText(T object);
}
