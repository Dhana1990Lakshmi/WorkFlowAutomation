package api.tests;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.github.javafaker.Faker;
import io.restassured.response.Response;

import api.endpoints.UserEndpoints;
import api.payload.*;
import api.utilities.ExtentReportManager;

import org.apache.logging.log4j.*;
@Listeners(ExtentReportManager.class)
public class UserTests {
	Faker faker;
	User userPayload= new User();
	public Logger log;
	@BeforeClass
	public void setupData() {
		faker=new Faker();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		userPayload.setUserStatus(1);
		
		log= LogManager.getLogger(this.getClass());
		
				
	}
	
	@Test(priority=1,testName = "Send Post Request to create new user")
	public void testPostUser() {
		log.info("Creating user");
		Response response= UserEndpoints.createUser(userPayload);
		response.then().log().all();
		log.info("Username::"+userPayload.getUsername());
		log.info("Post Status code::"+response.statusCode());
		Assert.assertEquals(response.getStatusCode(),200);
		log.info("Completed Creating user");
		
	}
	@Test(priority=2,testName = "Get User details for given username")
	public void testGetUser() {
		log.info("Retriving user details based on name");
		Response response= UserEndpoints.readUser(userPayload.getUsername());
		response.then().log().all();
		log.info(response.then().log().all());
		System.out.println("Get Status code::"+response.statusCode());
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	@Test(priority=3,testName = "Update User details for given username")
	public void testUpdateUser() {
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		Response response= UserEndpoints.updateUser(userPayload.getUsername(),userPayload);
		response.then().log().all();
		System.out.println("Put Status code::"+response.statusCode());
		Assert.assertEquals(response.statusCode(),200);
		testGetUser();		
	}
	@Test(priority=4,testName = "Delete User details for given username")
	public void testDeleteUser() {
		Response response= UserEndpoints.deleteUser(userPayload.getUsername());
		response.then().log().all();
		System.out.println("Delete Status code::"+response.statusCode());
		Assert.assertEquals(response.statusCode(),200);
		
	}
}
