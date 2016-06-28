package org.mashup.entertainment.services;

import org.mashup.entertainment.MovieServiceException;
import org.mashup.entertainment.model.Movie;

import rx.Observable;

public interface IMovieService {

	/**
	 * Find movies for the given move name
	 * @param movieName
	 * @return List of movies
	 * @throws MovieServiceException 
	 */
	Observable<Movie> findMovies(String movieName) throws MovieServiceException;

}
