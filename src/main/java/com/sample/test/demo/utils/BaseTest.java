package com.sample.test.demo.utils;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class BaseTest {
	
	public static Pizza pizza;
	public static Order order;
	public static String json;

	public static Pizza getPizza(String item, String pizzaSize,List<String> toppings) {

		pizza = new Pizza();
		pizza.setPizza(pizzaSize);
		pizza.setItem(item);
		pizza.setToppings(toppings);

		return pizza;
	}

	public static Order getOrder(String orderId,String item,String pizzaSize,List<String>toppings) {

		order = new Order();
		order.setId(orderId);
		order.setItems(Arrays.asList(getPizza(item, pizzaSize,toppings)));

		return order;
	}

	public static String getJson(String orderId,String item,String pizzaSize,List<String>toppings) {

		json = new Gson().toJson(getOrder(orderId,item,pizzaSize,toppings));

		return json;
	}

}
