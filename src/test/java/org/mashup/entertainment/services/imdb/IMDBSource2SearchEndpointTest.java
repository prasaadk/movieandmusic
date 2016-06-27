package org.mashup.entertainment.services.imdb;

import static org.junit.Assert.*;

import org.junit.Test;

public class IMDBSource2SearchEndpointTest {

	@Test
	public void test() {
		assertEquals("should return 10 results", 10, new IMDBSource2SearchEndpoint().searchMovie("jaws").size());
	}

}
