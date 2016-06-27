package org.mashup.entertainment.services;

import org.mashup.entertainment.model.Movie;

import rx.Observable;

public interface IMovieAggregator {
	
	/**
	 * 
	 * @param movieName
	 * @return
	 */
	Observable<Movie> getMovies(String movieName);
}
