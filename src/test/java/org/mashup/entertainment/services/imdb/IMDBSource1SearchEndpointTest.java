package org.mashup.entertainment.services.imdb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IMDBSource1SearchEndpointTest {

	@Test
	public void test() {
		assertEquals("should return 10 results", 10, new IMDBSource1SearchEndpoint().searchMovie("jaws").size());
	}

}
