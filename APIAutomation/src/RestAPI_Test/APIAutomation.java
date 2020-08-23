package RestAPI_Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.http.Method;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;



public class APIAutomation {
	private WebDriver driver;
	private String baseUrl;
	String baseURI_POST = "http://dummy.restapiexample.com/api/v1/create";
	String baseURI = "http://dummy.restapiexample.com/";
	String JsonDataInFile = "F:\\NewProject\\APIAutomation\\src\\RestAPI_Test\\Employee.json";
	String JsonDataInFile_Update = "APIAutomation/src/RestAPI_Test/EmployeeUpdate.json";

	@Test
	public void APIAutomation() throws ClientProtocolException, IOException, ParseException {
	
				// Creating a File instance 
				File jsonDataInFile = new File(JsonDataInFile);
				File jsonDataInFile_Update = new File(JsonDataInFile_Update);
				
				//Post the employee
				RequestSpecification httpRequest=RestAssured.given();
				httpRequest.baseUri(baseURI_POST);
				httpRequest.contentType(ContentType.JSON);
				httpRequest.body(jsonDataInFile);
				System.out.println(jsonDataInFile);
				Response response =  httpRequest.post();
				JsonPath jsonPathEvaluator = response.jsonPath();
				int ID = jsonPathEvaluator.get("id");
				String name = jsonPathEvaluator.getString("name");
				
				System.out.println("ID is --"+ ID);
				int status = httpRequest.post().statusCode();
				System.out.println(status);
				
				//Get the created employee details
				RequestSpecification HttpGet=RestAssured.given();
				HttpGet.baseUri(baseURI);
				Response Getresponse = HttpGet.get("/api/v1/employee/'"+ID+"'");
				JsonPath jsonPathEvaluator_get = response.jsonPath();
				String Employee_name = jsonPathEvaluator_get.get("employee_name");
				
				if(Employee_name.equals(name)) {
					System.out.println("Employee name validation is passed");
				}
				else
				{
					System.out.println("Employee name validation is failed");
				}
				
				//Update the employee
				
				RequestSpecification HttpPut=RestAssured.given();
				HttpGet.baseUri(baseURI);
				Response Putresponse = HttpGet.put("/api/v1/update/'"+ID+"'");
				HttpPut.contentType(ContentType.JSON);
				HttpPut.body(jsonDataInFile_Update);
				JsonPath jsonPathEvaluator_Put = Putresponse.jsonPath();
				int ID_Put = jsonPathEvaluator_Put.get("id");
				String name_Put = jsonPathEvaluator.getString("name");
	}
}
