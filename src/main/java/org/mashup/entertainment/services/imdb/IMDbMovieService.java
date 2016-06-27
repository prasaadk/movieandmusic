package org.mashup.entertainment.services.imdb;

import java.util.List;

import org.mashup.entertainment.model.Movie;
import org.mashup.entertainment.services.IMovieAggregator;
import org.mashup.entertainment.services.IMovieSearchEndpoint;
import org.mashup.entertainment.services.IMovieService;

import com.google.common.collect.Lists;

import rx.Observable;

public class IMDbMovieService implements IMovieService {
	
	public static final String MOVIE_URI="http://www.omdbapi.com/?";
	
	public Observable<Movie> findMovies(String movieName) {
		
		List<IMovieSearchEndpoint> searchEndpoints = Lists.newArrayList(
				new IMDBSource1SearchEndpoint(), 
				new IMDBSource2SearchEndpoint());
		
		IMovieAggregator aggregator = new IMDBMovieAggregator(searchEndpoints);

		return aggregator.getMovies(movieName);
	}


	public static IMovieService instance() {
		return new IMDbMovieService();
	}
	
}
