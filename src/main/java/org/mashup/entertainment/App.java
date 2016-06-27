package org.mashup.entertainment;

import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.mashup.entertainment.model.Movie;
import org.mashup.entertainment.output.ConsoleOutputRenderer;
import org.mashup.entertainment.output.IOutputRenderer;
import org.mashup.entertainment.services.IMovieService;
import org.mashup.entertainment.services.MovieServiceRegistry;

import rx.Observable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	CommandLineParser parser = new DefaultParser();
    	Options options = new Options();
    	
		Option option = Option.builder("D").numberOfArgs(2).valueSeparator().required().build();
		
		options.addOption(option);
		
		try {
			CommandLine parse = parser.parse(options, args);
			Properties props = parse.getOptionProperties("D");
			String api = (String) props.get("api");
			String movieName = (String) props.get("movie");
			
			MovieServiceRegistry registry = new MovieServiceRegistry();
			IMovieService service = registry.lookup(api);
			if(service == null) {
				System.out.println("Unsupported API option '"+api);
			}
			
			Observable<Movie> movies = filter(service.findMovies(movieName));
			IOutputRenderer renderer = new ConsoleOutputRenderer(System.out);
			renderer.render(movies);
		} catch (ParseException e) {
			// TODO : Exception handling
			e.printStackTrace();
		}
    }

	private static Observable<Movie> filter(Observable<Movie> movies) {
		return movies.filter(movie->{
			
			//filter is added with an assumption that movies with future dates should be excluded
			String yearStr = movie.getYear();
			
			if(StringUtils.isBlank(yearStr)) {
				return true;
			}
			
			int year = 0;
			try {
				year = Integer.parseInt(yearStr);
			}catch(NumberFormatException e) {
				return true;
			}
			
			return (year<=DateTime.now().getYear());
		});
	}
}
