package org.mashup.entertainment.services.imdb;

import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

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

import rx.Observable;

class IMDBMovieAggregator implements IMovieAggregator {
	
	public static final String MOVIE_URI="http://www.omdbapi.com/?";
	
	private List<IMovieSearchEndpoint> searchEndpoints;

	public IMDBMovieAggregator(List<IMovieSearchEndpoint> searchEndpoints) {
		Preconditions.checkArgument(!searchEndpoints.isEmpty());
		this.searchEndpoints = searchEndpoints;
	}
	
	public Observable<Movie> getMovies(String movieName) {
    	Set<String> movieIDs = Sets.newLinkedHashSet();
    	
    	for(IMovieSearchEndpoint endpoint: searchEndpoints) {
    		movieIDs.addAll(endpoint.searchMovie(movieName));
    	}
    	
    	List<Movie> movies = Lists.newArrayList();
		for(String id: movieIDs) {				
			JsonObject movieObj = getMovie(id);
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
	
	private JsonObject getMovie(String id) {
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
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        return null;
	}
	
	
}