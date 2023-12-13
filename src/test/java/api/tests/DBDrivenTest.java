package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;
import api.utilities.*;

@Listeners(ExtentReportManager.class)

public class DBDrivenTest {
	User userPayload;
	public Logger log;
	@BeforeClass
	public void setup() {
		log= LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1,dataProvider="Data", dataProviderClass=DataProviders.class, testName="Create New users By reading data from Excel")
	public void testpostUser(String Id ,String UserName,String firstname,String lastname,String email,String password,String phone,String userstatus) {
		
		userPayload= new User();
		userPayload.setId(Integer.parseInt(Id));
		userPayload.setUsername(UserName);
		userPayload.setFirstName(firstname);
		userPayload.setLastName(lastname);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		userPayload.setUserStatus(Integer.parseInt(userstatus));
		
		Response response= UserEndpoints.createUser(userPayload);
		response.then().log().all();
		log.info("Username::"+userPayload.getUsername());
		log.info("Post Status code::"+response.statusCode());
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	@Test(priority=2,dataProvider="UserName", dataProviderClass=DataProviders.class/*, testName="Get Details of New users created by reading data from Excel"*/)
	public void testGetUser(String UserName) {
		Response response= UserEndpoints.readUser(UserName);
		response.then().log().all();
		log.info("Get Status code::"+response.statusCode());
		//Assert.assertEquals(response.getStatusCode(),200);
		
	}
	@Test(priority=3,dataProvider="UserName", dataProviderClass=DataProviders.class/*, testName="Delete Details of New users created by reading data from Excel"*/)
	public void testDeleteUser(String UserName) {
		Response response= UserEndpoints.deleteUser(UserName);
		response.then().log().all();
		System.out.println("Delete Status code::"+response.statusCode());
		Assert.assertEquals(response.statusCode(),200);
		
	}
}
