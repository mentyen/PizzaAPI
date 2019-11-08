package com.sample.test.demo.tests;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sample.test.demo.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class TC_002_postHappyPath extends BaseTest{
	
	public Response response;


	@BeforeClass
	public void getResponse() {
	
		RestAssured.baseURI = "https://my-json-server.typicode.com/sa2225/demo/orders";
		
		String body=getJson(null, "1", "Small 6 Slices - 1 topping", Arrays.asList("Diced Mango"));

		response = given().accept(ContentType.JSON).header("Content-type", "application/json").body(body).when()
				.request(Method.POST);
		
	}

	@Test

	public void getStatus() {
		Assert.assertEquals(response.getStatusCode(), 201);
	}

	@Test
	public void getStatusLine() {

		Assert.assertEquals("HTTP/1.1 201 Created", response.getStatusLine());
	}

	@Test
	public void getTime() {

		Assert.assertTrue(response.getTime() < 2000L);
	}

	@Test
	public void getHeader() {

		Assert.assertEquals("application/json; charset=utf-8", response.getHeader("Content-Type"));
	}

	@Test
	public void getBody() {

		Assert.assertTrue(response.getBody() != null);
			
		JsonPath jpath = response.jsonPath();

		String responsePizza = jpath.get("items[0].pizza");
		
		List<String> actualToppings =jpath.get("items[0].toppings");
		
		Assert.assertEquals(pizza.getPizza(), responsePizza);
		
		Assert.assertTrue(pizza.getToppings().size()==actualToppings.size());

	}

}
