package org.mashup.entertainment.output;

import java.io.OutputStream;
import java.io.PrintStream;

import org.mashup.entertainment.model.Movie;

import rx.Observable;

/**
 * This class will render the text output as per the required format. 
 * @author prasad
 */
public class ConsoleOutputRenderer implements IOutputRenderer {
	
	private PrintStream stream;

	public ConsoleOutputRenderer(OutputStream stream) {
		this.stream = new PrintStream(stream);
	}

	public void render(Observable<Movie> movies) {
		movies.subscribe(movie->{
			stream.print(movie.getTitle());
			stream.print(" - "+movie.getYear());
			stream.println(" - "+movie.getDirector());
		});
	}

}
