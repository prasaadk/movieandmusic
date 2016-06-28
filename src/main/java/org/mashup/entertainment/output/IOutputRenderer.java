package org.mashup.entertainment.output;

import org.mashup.entertainment.model.Movie;

import rx.Observable;

/**
 * A general interface for rendering a collection of movies
 * @author prasad
 *
 */
public interface IOutputRenderer {
	
	/**
	 * Render the given movies
	 * @param movies
	 */
	public void render(Observable<Movie> movies);

}
