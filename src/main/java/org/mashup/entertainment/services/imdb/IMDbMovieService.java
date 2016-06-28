package org.mashup.entertainment.services.imdb;

import java.util.List;

import org.mashup.entertainment.MoveAggregationException;
import org.mashup.entertainment.MovieServiceException;
import org.mashup.entertainment.model.Movie;
import org.mashup.entertainment.services.IMovieAggregator;
import org.mashup.entertainment.services.IMovieSearchEndpoint;
import org.mashup.entertainment.services.IMovieService;

import com.google.common.collect.Lists;

import rx.Observable;

/**
 * This is an implementation of movie service for IMDb movies. It uses custom
 * search end points to search movies only in the IMDb ecosystem
 * 
 * @author prasad
 *
 */
public class IMDbMovieService implements IMovieService {
	
	public static final String MOVIE_URI="http://www.omdbapi.com/?";
	
	public Observable<Movie> findMovies(String movieName) throws MovieServiceException {
		
		List<IMovieSearchEndpoint> searchEndpoints = Lists.newArrayList(
				new IMDBSource1SearchEndpoint(), 
				new IMDBSource2SearchEndpoint());
		
		IMovieAggregator aggregator = new IMDBMovieAggregator(searchEndpoints);

		try {
			return aggregator.getMovies(movieName);
		} catch (MoveAggregationException e) {
			throw new MovieServiceException("Unable to find movies for the given movieName", e);
		}
	}


	public static IMovieService instance() {
		return new IMDbMovieService();
	}
	
}
