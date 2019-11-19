package com.sample.test.demo.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sample.test.demo.utils.JsonUtil;
import com.sample.test.demo.utils.Order;
import com.sample.test.demo.utils.Pizza;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class TC_001_getHappyPath {

	public Response response;
	public static Map<Integer, Integer> expectedOrderItemsQuantity;
	public static Order order;
	public static String expectedOrderId;
	public static JSONArray responseArray;

	@BeforeClass
	public void getResponse() {

		RestAssured.baseURI = "https://my-json-server.typicode.com/sa2225/demo/orders";

		response = given().when().request(Method.GET);

	}

	@Test
	public void validateStatusCode() {

		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test
	public void validateStatusLine() {

		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
	}

	@Test
	public void validateHeader() {

		String header = response.getHeader("Content-type");

		Assert.assertEquals("application/json; charset=utf-8", header);

	}

	@Test
	public void validateTime() {

		long time = response.getTime();

		Assert.assertTrue(time < 3000L);

	}

	// For the API, the big question is how would you handle the validation of the
	// get orders response object. There are several ways you can do this. One is to
	// use one of the libraries to convert the response into an array of objects and
	// validate that. Another way would be to convert to a jsonarray of objects then
	// walk that array validating the existents and values of the objects. There are
	// pros and cons to each way. One of the things to think about is how do handle
	// an order that contains a pizza with no toppings.

	@Test
	public void validateBodyIsNotEmpty() {

		String responseString = response.body().asString();

		responseArray = new JSONArray(responseString);

		Assert.assertTrue(responseString != null);

	}

	@Test
	public void validateResponseArraySize() {

		Assert.assertTrue(isResponseArraySizeIsExpected(responseArray, 3));
	}

	@Test
	public void validateOrderItemsQuantity() {

		for (int i = 0; i < responseArray.length() - 1; i++) {

			int expectedItemsQuantity = getExpectedOrderItemsQantity(i + 1);

			Assert.assertTrue(isOrderItemsQuantityIsCorrect(i, expectedItemsQuantity));

		}
		;

	}

	@Test
	public void validatingToppingsQuantity() {

		for (int i = 0; i < responseArray.length() - 1; i++) {

			String responseObject = responseArray.get(i).toString();

			Assert.assertTrue(isToppingsQuantityAsExpected(responseObject), "Order id: "+(i+1)+" has incorrect number of toppings");
		}
		;

	}

	public static boolean isResponseArraySizeIsExpected(JSONArray responseArray, int expectedSize) {

		if (responseArray.length() == expectedSize) {
			return true;
		} else {
			return false;
		}

	}

	public static Integer getExpectedOrderItemsQantity(int orderId) {

		expectedOrderItemsQuantity = new HashMap<Integer, Integer>();
		expectedOrderItemsQuantity.put(1, 2);
		expectedOrderItemsQuantity.put(2, 1);
		expectedOrderItemsQuantity.put(3, 1);

		return expectedOrderItemsQuantity.get(orderId);

	}

	public static boolean isToppingsQuantityAsExpected(String jsonObject) {

		order = JsonUtil.convertJsonToJava(jsonObject, Order.class);

		boolean isToppingQuantityAsExpected = false;

		for (Pizza pizza : order.getItems()) {

			if (pizza.getPizza().contains("no toppings")) {

				if (pizza.getToppings().isEmpty()) {
					isToppingQuantityAsExpected = true;
				}

			}
			if (pizza.getPizza().contains("1 topping")) {

				if (pizza.getToppings().size() == 1) {
					isToppingQuantityAsExpected = true;
				}

			}
			if (pizza.getPizza().contains("2 toppings")) {

				if (pizza.getToppings().size() == 2) {
					isToppingQuantityAsExpected = true;
				}
			}

		}
		return isToppingQuantityAsExpected;

	}

	public static boolean isOrderItemsQuantityIsCorrect(int orderId, int itemsQuantity) {

		order = JsonUtil.convertJsonToJava(responseArray.get(orderId).toString(), Order.class);

		if (order.getItems().size() == itemsQuantity) {
			return true;
		} else {
			return false;
		}
	}

}
