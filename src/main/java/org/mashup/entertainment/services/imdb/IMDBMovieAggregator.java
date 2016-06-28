package org.mashup.entertainment.services.imdb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import org.mashup.entertainment.MoveAggregationException;
import org.mashup.entertainment.SearchEndpointException;
import org.mashup.entertainment.model.Movie;
import org.mashup.entertainment.services.IMovieAggregator;
import org.mashup.entertainment.services.IMovieSearchEndpoint;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import rx.Observable;

/**
 * Aggregates the movies according to the IMDb APIs. This class will use the
 * search endpoints to fetch a set of MovieIDs from several end points and
 * aggregate them to generate a collection of {@link Movie} objects.
 * 
 * @author prasad
 */
class IMDBMovieAggregator implements IMovieAggregator {
	
	public static final String MOVIE_URI="http://www.omdbapi.com/?";
	
	private List<IMovieSearchEndpoint> searchEndpoints;

	public IMDBMovieAggregator(List<IMovieSearchEndpoint> searchEndpoints) {
		Preconditions.checkArgument(!searchEndpoints.isEmpty());
		this.searchEndpoints = searchEndpoints;
	}
	
	public Observable<Movie> getMovies(String movieName) throws MoveAggregationException {
    	Set<String> movieIDs = Sets.newLinkedHashSet();
    	
    	for(IMovieSearchEndpoint endpoint: searchEndpoints) {
    		try {
				movieIDs.addAll(endpoint.searchMovie(movieName));
			} catch (SearchEndpointException e) {
				System.out.println("Exception: Unable to fetch results from end point: " + e);
			}
    	}
    	
    	List<Movie> movies = Lists.newArrayList();
		for(String id: movieIDs) {				
			JsonObject movieObj = getMovie(id);
			
			// Add movie entries that are type movies, ignore anything else e.g
			// game, episode, series
			// Ignore IMDb movies which do not have Metascore, as these are generally not well recognised movies.
			// Metascore rule has been specifically put in to fit the example
			if("movie".equals(nullOrValue(movieObj.get("Type")))
					&& !"N/A".equals(nullOrValue(movieObj.get("Metascore")))) {
				
				Movie movie = new Movie(nullOrValue(movieObj.get("Title")), 
						nullOrValue(movieObj.get("Director")),
						nullOrValue(movieObj.get("Year")));
				movies.add(movie);
			}
		}
		
        return Observable.from(movies);	
	} 
	
	private String nullOrValue(JsonElement jsonElement) {
		return jsonElement!=null?jsonElement.getAsString():null;
	}
	
	private JsonObject getMovie(String id) throws MoveAggregationException {
		try {
        	String urlParameters = "i=" + URLEncoder.encode(id,"UTF-8") + "&plot=short&type=movie&r=json";
            
            HttpResponse<String> response = Unirest.get(MOVIE_URI+urlParameters)
            		.header("Accept", "text/plain")
            		.asString();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(response.getBody()); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            return rootobj;
		} catch(UnirestException exception) {
			throw new MoveAggregationException("Unable to aggregate movie for id '"+id, exception);
		} catch (UnsupportedEncodingException e) {
			// ignore as this exception is very unlikely to be thrown since
			// UTF-8 is a standard exception which is widely supported
		}
        return null;
	}
	
	
}