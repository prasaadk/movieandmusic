package org.mashup.entertainment.services;

import java.net.URLEncoder;
import java.util.List;

import org.mashup.entertainment.model.Movie;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import rx.Observable;

public class SimapiMovieService implements IMovieService {
	
	public static final String MOVIE_URI="https://simapi.p.mashape.com/m.php?";
	
	public Observable<Movie> findMovies(String movieName) {
        try {
        	String urlParameters = "t=" + URLEncoder.encode(movieName,"UTF-8") + "&type=movie&r=json";
            
            HttpResponse<String> response = Unirest.get(MOVIE_URI+urlParameters)
            		.header("X-Mashape-Key", "eJjgXsYqlwmshvkJLRn7AdXTMUmdp1AsY0ojsnX5O5OX7xBoVu")
            		.header("Accept", "text/plain")
            		.asString();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(response.getBody()); //Convert the input stream to a json element
            
            JsonArray findMovies = root.getAsJsonArray();
            List<Movie> movies = Lists.newArrayList();
			for(JsonElement element: findMovies) {
				String id = element.getAsJsonObject().get("id").getAsString();
				JsonObject movieObj = getMovie(id);
				
				if("movie".equals(nullOrValue(movieObj.get("type")))) {
					movies.add(new Movie(nullOrValue(movieObj.get("title")), 
							nullOrValue(movieObj.get("director")),
							nullOrValue(movieObj.get("year"))));
				}
			}
			return Observable.from(movies);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

		return null;
	}
	
	private String nullOrValue(JsonElement jsonElement) {
		return jsonElement!=null?jsonElement.getAsString():null;
	}
	private JsonObject getMovie(String id) {
        try {
        	String urlParameters = "i=" + URLEncoder.encode(id,"UTF-8") + "&type=movie&r=json";
            
            HttpResponse<String> response = Unirest.get(MOVIE_URI+urlParameters)
            		.header("X-Mashape-Key", "eJjgXsYqlwmshvkJLRn7AdXTMUmdp1AsY0ojsnX5O5OX7xBoVu")
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

	public static IMovieService instance() {
		return new SimapiMovieService();
	}
}
