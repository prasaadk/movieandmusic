package org.mashup.entertainment.services.imdb;

import org.junit.Test;
import org.mashup.entertainment.model.Movie;
import org.mashup.entertainment.services.imdb.IMDbMovieService;

import junit.framework.TestCase;
import rx.Observable;

public class IMDbMovieServiceTest extends TestCase {

	@Test
	public void testFindMovies() throws Exception {
		Observable<Movie> movies = new IMDbMovieService().findMovies("iron+man");
		assertEquals("should find 4 movies" , 5, movies.count().toBlocking().first().intValue());
	}
	
}
