package org.mashup.entertainment.services;

import org.junit.Test;
import org.mashup.entertainment.model.Movie;

import junit.framework.TestCase;
import rx.Observable;

public class SimApiMovieServiceTest extends TestCase {

	@Test
	public void testFindMovies() throws Exception {
		Observable<Movie> movies = new SimapiMovieService().findMovies("iron+man");
		assertEquals("should find 3 movies" , 3, movies.count().toBlocking().first().intValue());
	}
}
