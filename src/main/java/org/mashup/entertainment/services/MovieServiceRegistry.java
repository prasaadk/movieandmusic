package org.mashup.entertainment.services;

import java.util.Map;

import org.mashup.entertainment.services.imdb.IMDbMovieService;

import com.google.common.collect.Maps;

/**
 * Registers several movie finder APIs
 * @author prasad
 *
 */
public class MovieServiceRegistry {
	
	public Map<String, IMovieService> map = Maps.newHashMap();
	
	public MovieServiceRegistry() {
		init();
	}
	
	private void init() {
		map.put("imdb", IMDbMovieService.instance());
		map.put("simapi", SimapiMovieService.instance());
	}
	
	public IMovieService lookup(String serviceId) {
		return map.get(serviceId);	
	}
}
