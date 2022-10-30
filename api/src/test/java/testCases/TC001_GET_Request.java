package testCases;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC001_GET_Request {

	@Test
	public void TC001_GetRequest() {  //  ui//bl/db/======  business 
		
		//specify the URI
		RestAssured.baseURI="https://reqres.in/api/users?page=2";
		
		//Request object
		RequestSpecification httpsRequest=RestAssured.given();
		
		//Responce Object
		Response responce=httpsRequest.request(Method.GET,"");
		
		String responseBody=responce.getBody().asString();
		System.out.println("Response body is "+responseBody);
		
		int statusCode=responce.getStatusCode();
		System.out.println(statusCode);
		
		//validation of status code
		Assert.assertEquals(statusCode, 200);
		
		String statusLine=responce.getStatusLine();
		System.out.println(statusLine);
		
		//validation of statusLine
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
}
