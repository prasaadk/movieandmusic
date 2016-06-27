package org.mashup.entertainment.services;

import java.util.Set;


public interface IMovieSearchEndpoint {
	
	/**
	 * This method searches for a movie and returns a set of movieIDs
	 * @param movieName
	 * @return set of movie IDs
	 */
	Set<String> searchMovie(String movieName);
	
}
