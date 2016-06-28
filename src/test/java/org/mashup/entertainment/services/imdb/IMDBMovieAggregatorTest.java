package org.mashup.entertainment.services.imdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mashup.entertainment.model.Movie;
import org.mashup.entertainment.services.IMovieSearchEndpoint;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import rx.Observable;

public class IMDBMovieAggregatorTest {
	
	private List<IMovieSearchEndpoint> searchEndpoints;

	@Before
	public void setUp() {
		searchEndpoints = Lists.newArrayList();
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testEndpointWithOneMovie() throws Exception {
		final String movieId = "tt0097576";
		final String movie = "movieName";
		
		//Mocking search endpoint to return a valid imdb ID
		searchEndpoints.add(new IMovieSearchEndpoint() {
			
			@Override
			public Set<String> searchMovie(String movieName) {
				if(movie.equals(movieName)) 
					return Sets.newHashSet(movieId);
				
				return Sets.newHashSet();
			}
		});
		
		IMDBMovieAggregator aggregator = new IMDBMovieAggregator(searchEndpoints);
		
		Observable<Movie> movies = aggregator.getMovies(movie);
		assertEquals("Should return one movie", 1, movies.count().toBlocking().first().intValue());
		
		Movie first = movies.toBlocking().first();
		Movie movieObj = new Movie("Indiana Jones and the Last Crusade","Steven Spielberg","1989");
		assertEquals("Should be equal", movieObj, first);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEndpointWithNoMovies() {		
		new IMDBMovieAggregator(Lists.newArrayList()); // no search end points registered
	}
	
	@Test
	public void testEndpointWithIDsNotRecognised() throws Exception {
		final String movieId = "wrong-movie-id";
		final String movie = "movieName";
		
		//Mocking search endpoint to return a valid imdb ID
		searchEndpoints.add(new IMovieSearchEndpoint() {
			
			@Override
			public Set<String> searchMovie(String movieName) {
				if(movie.equals(movieName)) 
					return Sets.newHashSet(movieId);
				
				return Sets.newHashSet();
			}
		});
		
		IMDBMovieAggregator aggregator = new IMDBMovieAggregator(searchEndpoints);
		
		Observable<Movie> movies = aggregator.getMovies(movie);
		assertTrue("Should return empty collection", movies.isEmpty().toBlocking().first());
		
	}
	
	@Test
	public void testMultipleEndpointsWithIDs() throws Exception {
		final String movieId1 = "tt0097576";
		final String movie = "movieName";
		
		//Mocking search endpoint to return a valid imdb ID
		searchEndpoints.add(new IMovieSearchEndpoint() {
			
			@Override
			public Set<String> searchMovie(String movieName) {
				if(movie.equals(movieName)) 
					return Sets.newHashSet(movieId1);
				
				return Sets.newHashSet();
			}
		});
		
		final String movieId2 = "tt0367882";
		searchEndpoints.add(new IMovieSearchEndpoint() {
			
			@Override
			public Set<String> searchMovie(String movieName) {
				if(movie.equals(movieName)) 
					return Sets.newHashSet(movieId2);
				
				return Sets.newHashSet();
			}
		});		
		
		IMDBMovieAggregator aggregator = new IMDBMovieAggregator(searchEndpoints);
		
		Observable<Movie> movies = aggregator.getMovies(movie);
		assertEquals("Should return two movies", 2, movies.count().toBlocking().first().intValue());
		
		Movie first = movies.toBlocking().first();
		Movie second = movies.toBlocking().last();
		Movie movie1Obj = new Movie("Indiana Jones and the Last Crusade","Steven Spielberg","1989");
		assertEquals("Should be equal", movie1Obj, first);
		
		Movie movie2Obj = new Movie("Indiana Jones and the Kingdom of the Crystal Skull","Steven Spielberg","2008");
		assertEquals("Should be equal", movie2Obj, second);
	}
	
	@Test
	public void testMultipleEndpointsWithSameIDs() throws Exception {
		final String movieId1 = "tt0097576";
		final String movie = "movieName";
		
		//Mocking search endpoint to return a valid imdb ID
		searchEndpoints.add(new IMovieSearchEndpoint() {
			
			@Override
			public Set<String> searchMovie(String movieName) {
				if(movie.equals(movieName)) 
					return Sets.newHashSet(movieId1);
				
				return Sets.newHashSet();
			}
		});
		
		final String movieId2 = movieId1;
		searchEndpoints.add(new IMovieSearchEndpoint() {
			
			@Override
			public Set<String> searchMovie(String movieName) {
				if(movie.equals(movieName)) 
					return Sets.newHashSet(movieId2);
				
				return Sets.newHashSet();
			}
		});		
		
		IMDBMovieAggregator aggregator = new IMDBMovieAggregator(searchEndpoints);
		
		Observable<Movie> movies = aggregator.getMovies(movie);
		assertEquals("Should return one movie", 1, movies.count().toBlocking().first().intValue());
		
		Movie first = movies.toBlocking().first();
		Movie movie1Obj = new Movie("Indiana Jones and the Last Crusade","Steven Spielberg","1989");
		assertEquals("Should be equal", movie1Obj, first);
	}
	
	@Test
	public void test() throws Exception {
		
	}

}
