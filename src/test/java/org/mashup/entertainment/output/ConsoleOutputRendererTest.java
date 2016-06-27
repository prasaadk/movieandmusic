package org.mashup.entertainment.output;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.mashup.entertainment.model.Movie;

import com.google.common.collect.Lists;

import rx.Observable;

public class ConsoleOutputRendererTest {

	@Test
	public void testSingleMovie() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ConsoleOutputRenderer renderer = new ConsoleOutputRenderer(out);
		
		Observable<Movie> movies = Observable.from(Lists.newArrayList(new Movie("Anaconda","Steven","2003")));
		renderer.render(movies);
		assertEquals("should be equal", "Anaconda - 2003 - Steven\n",new String(out.toByteArray()));
	}
	
	@Test
	public void testTwoMovies() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ConsoleOutputRenderer renderer = new ConsoleOutputRenderer(out);
		
		Observable<Movie> movies = Observable.from(Lists.newArrayList(new Movie("Jaws","Steven","1983"), new Movie("JawsII","Steven","1986")));
		renderer.render(movies);
		assertEquals("should be equal", "Jaws - 1983 - Steven\nJawsII - 1986 - Steven\n",new String(out.toByteArray()));
	}	

}
