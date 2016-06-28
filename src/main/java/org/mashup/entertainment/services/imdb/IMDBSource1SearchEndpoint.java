package org.mashup.entertainment.services.imdb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import org.mashup.entertainment.SearchEndpointException;
import org.mashup.entertainment.services.IMovieSearchEndpoint;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/** 
 * IMDB Open search endpoints
 * @author prasad
 *
 */
public class IMDBSource1SearchEndpoint implements IMovieSearchEndpoint {

	public static final String MOVIE_URI="http://www.omdbapi.com/?";
	
	@Override
	public Set<String> searchMovie(String movieName) throws SearchEndpointException {
        try {
        	String urlParameters = "s="+URLEncoder.encode(movieName,"UTF-8")+"&plot=short&r=json";
            
            HttpResponse<String> response = Unirest.get(MOVIE_URI+urlParameters)
            		.header("Accept", "text/plain")
            		.asString();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(response.getBody()); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            
            Set<String> movies = Sets.newHashSet();
            JsonArray findMovies = rootobj.get("Search").getAsJsonArray();
			for(JsonElement element: findMovies) {
				
				JsonObject asJsonObject = element.getAsJsonObject();
				String id = asJsonObject.get("imdbID").getAsString();
				movies.add(id);
			}
			
            return movies;
		} catch (UnsupportedEncodingException e) {
			// Unlikely, as UTF-8 is a standard encoding. If the system has a
			// log framework configured, a warning should be added.
		} catch (UnirestException e) {
			throw new SearchEndpointException("Unable to find movie for the name '"+movieName, e);
		}            
        return null;
	}
	
}