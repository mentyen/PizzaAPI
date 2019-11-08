package com.sample.test.demo.tests;

import static io.restassured.RestAssured.given;

import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import junit.framework.Assert;

public class TC_001_getHappyPath {

	public Response response;

	@BeforeClass
	public void getResponse() {

		RestAssured.baseURI = "https://my-json-server.typicode.com/sa2225/demo/orders";

		response = given().when().request(Method.GET);

	}

	@Test
	public void getStatusCode() {

		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test
	public void getStatusLine() {

		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
	}

	@Test
	public void getHeader() {

		String header = response.getHeader("Content-type");

		Assert.assertEquals("application/json; charset=utf-8", header);

	}

	@Test
	public void getTime() {

		long time = response.getTime();

		Assert.assertTrue(time < 3000L);

	}

	@Test
	public void getBody() {

		String body = response.getBody().asString();

		Assert.assertTrue(body != null);

		String responseString = response.body().asString();
		
		JSONArray responseArray = new JSONArray(responseString);
		
		Assert.assertTrue(responseArray.get(1).toString().contains("Small 6 Slices - no toppings"));
			

	}

}
