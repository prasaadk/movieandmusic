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
 * IMDB Open search endpoints source 2
 * @author prasad
 *
 */
public class IMDBSource2SearchEndpoint implements IMovieSearchEndpoint {

	@Override
	public Set<String> searchMovie(String movieName) throws SearchEndpointException {
        HttpResponse<String> response;
        Set<String> movies = Sets.newHashSet();
		try {
			response = Unirest.get("http://imdb.wemakesites.net/api/search?q="+URLEncoder.encode(URLEncoder.encode(movieName,"UTF-8"),"UTF-8"))
					.header("Accept", "text/plain")
					.asString();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(response.getBody()); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
        
        rootobj.get("data").getAsJsonObject().get("results").getAsJsonObject().get("titles").getAsJsonArray();
        //JsonArray findMovies = rootobj.get("Search").getAsJsonArray();
        JsonArray findMovies = rootobj.get("data").getAsJsonObject().get("results").getAsJsonObject().get("titles").getAsJsonArray();
        
        for(JsonElement element: findMovies) {
        	JsonObject asJsonObject = element.getAsJsonObject();
			String id = asJsonObject.get("id").getAsString();
			movies.add(id);
        }
		} catch (UnsupportedEncodingException e) {
			// Unlikely, as UTF-8 is a standard encoding. If the system has a
			// log framework configured, a warning should be added.
		} catch (UnirestException e) {
			throw new SearchEndpointException("Unable to find movie for the name '"+movieName, e);
		}    
        return movies;
	}
	
}