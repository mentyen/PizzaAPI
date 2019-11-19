package com.sample.test.demo.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

public class JsonUtil {

	private static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
	}

	public static String convertJavaToJson(Object object) {

		String jsonResult = "";

		try {
			jsonResult = mapper.writeValueAsString(object);
		} catch (IOException e) {
			System.out.println("Exception Occured while converting Java Object into Json " + e.getMessage());
		}

		return jsonResult;
	}

	// <T> T is generic type and can take any type of object
	public static <T> T convertJsonToJava(String jsonString, Class<T> cls) {
		T result = null;
		try {
			result = mapper.readValue(jsonString, cls);
		} catch (JsonParseException e) {
			System.out.println("Exception Occured while converting Json Object into Java " + e.getMessage());

		} catch (JsonMappingException e) {
			System.out.println("Exception Occured while converting Json Object into Java " + e.getMessage());

		} catch (IOException e) {
			System.out.println("Exception Occured while converting Json Object into Java " + e.getMessage());

		}
		return result;

	}

}
