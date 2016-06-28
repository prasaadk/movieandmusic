package org.mashup.entertainment.services;

import org.mashup.entertainment.MoveAggregationException;
import org.mashup.entertainment.model.Movie;

import rx.Observable;

public interface IMovieAggregator {
	
	/**
	 * This method aggregates and returns a list of movies for the given movie name
	 * @param movieName
	 * @return collection of movies
	 * @throws MoveAggregationException 
	 */
	Observable<Movie> getMovies(String movieName) throws MoveAggregationException;
}
