package org.mashup.entertainment.services;

import java.util.Set;

import org.mashup.entertainment.SearchEndpointException;


public interface IMovieSearchEndpoint {
	
	/**
	 * This method searches for a movie and returns a set of movieIDs
	 * @param movieName
	 * @return set of movie IDs
	 * @throws SearchEndpointException 
	 */
	Set<String> searchMovie(String movieName) throws SearchEndpointException;
	
}
