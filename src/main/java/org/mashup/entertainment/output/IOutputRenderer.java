package org.mashup.entertainment.output;

import org.mashup.entertainment.model.Movie;

import rx.Observable;

public interface IOutputRenderer {
	
	public void render(Observable<Movie> movies);

}
