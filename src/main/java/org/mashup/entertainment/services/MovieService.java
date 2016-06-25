package org.mashup.entertainment.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.mashup.entertainment.model.Movie;

import com.google.gson.Gson;

public class MovieService {
	
	public static final String MOVIE_URI="https://moviesapi.com/m.php?t=indian+jones&type=movie";
	
	public Movie findMovies(String movieName) {
        try {
            URL gamecenterUrl = new URL(MOVIE_URI);
            HttpURLConnection connection = (HttpURLConnection) gamecenterUrl.openConnection();
            String urlParameters = "t=" + movieName + "&type=movie";
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            
            return (Movie) new Gson().fromJson(new StringReader(IOUtils.toString(connection.getInputStream())),
    				Movie.class);
	    } catch (MalformedURLException e) {
	            //todo exception
	    } catch (IOException e) {
	            //todo exception
	    }


		return null;
	}
	
	public Movie getMovie(String id) {
		return null;
	}
}
