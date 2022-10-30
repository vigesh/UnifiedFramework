package testCases;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicApiTest extends BaseTest {

    @Test
    @Step("Verify get request with 200 status code")
    public void T01_StatusCodeAndGetClientsTest() {
        res = utils.RestAssuredUtil.getResponse("/gen/clients");
        testUtil.checkStatusIs200(res);
        jp = utils.RestAssuredUtil.getJsonPath(res);
        System.out.println(testUtil.getClients(jp));
    }

    @Test
    @Step("Verify get request with 200 status code")
    public void T02_GetAndroidModelPackageOptions() {
        res = utils.RestAssuredUtil.getResponse("/gen/clients/android");
        testUtil.checkStatusIs200(res);
        jp = utils.RestAssuredUtil.getJsonPath(res);
        System.out.println("Opt: " + jp.get("modelPackage.opt"));
        System.out.println("Description: " + jp.get("modelPackage.description"));
        System.out.println("Type: " + jp.get("modelPackage.type"));
    }

    @Test
    @Step("Verify post request with 201 status code")
    public void T03_PostRequestToCreateUser() {  //  ui//bl/db/======  business

        //specify the URI
        RestAssured.baseURI="https://reqres.in/api/users";

        //Request object
        RequestSpecification httpsRequest=RestAssured.given();

        //Request Payload sending with Post request

        JSONObject requestParams=new JSONObject();
        requestParams.put( "name", "morpheus1234");
        requestParams.put( "job", "TechLead");

        //Headers

        httpsRequest.header("Content-Type","application/json");

        httpsRequest.body(requestParams.toJSONString());

        //Sending Request

        Response responce=httpsRequest.request(Method.POST,"");

        //validating body
        String responseBody=responce.getBody().asString();
        System.out.println("Response body is "+responseBody);

        int statusCode=responce.getStatusCode();
        System.out.println(statusCode);

        //Validating Status code
        Assert.assertEquals(statusCode, 201);
    }

    @Test
    @Step("Verify Delete request with 200 status code")
    public void T04_DeleteRequestOnBookTable() {
        String userId= "de5d75d1-59b4-487e-b632-f18bc0665c0d";
        String baseUrl="https://demoqa.com/swagger/";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InRlc3RpbmcxMjMiLCJwYXNzd29yZCI6IlBhc3N3b3JkQDEiLCJpYXQiOjE2Mjg1NjQyMjF9.lW8JJvJF7jKebbqPiHOBGtCAus8D9Nv1BK6IoIIMJQ4";
        String isbn ="9781449337711";

        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        //Calling the Delete API with request body
        Response res = httpRequest.body("{ \"isbn\": \"" + isbn + "\", \"userId\": \"" + userId + "\"}").delete("/BookStore/v1/Book");

        //Fetching the response code from the request and validating the same
        System.out.println("The response code is - " +res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(),200);

    }

    @Test
    @Step("Verify user details using get request")
    public void T05_GetUserData() {
        //Using the preemptive directive of basic auth to send credentials to the server
        RequestSpecification httpRequest = RestAssured.given().auth().preemptive().basic("postman", "password");
        Response res = httpRequest.get("https://postman-echo.com/basic-auth");
        ResponseBody body = res.body();
        //Converting the response body to string
        String rbdy = body.asString();
        System.out.println("Data from the GET API- "+rbdy);
    }
}
