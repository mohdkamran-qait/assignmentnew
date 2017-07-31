package com.qait.automation.assignment5;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class TatocAdvance {
	Statement st;
	WebDriver driver;
	String name;
	String passkey;
	String[] sessionid;

	String token;

	@Test
	public void makeDatabaseConnection()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://10.0.1.86/tatoc", "tatocuser", "tatoc01");
		st = con.createStatement();
		ResultSet identitytable = st.executeQuery("select * from identity");
		ResultSetMetaData rsmd = identitytable.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		String columnname1 = rsmd.getColumnLabel(1);
		String columnname2 = rsmd.getColumnLabel(2);

		while (identitytable.next()) {
			String itd = identitytable.getString(1);
			String itd2 = identitytable.getString(2);
		}
		ResultSet credentialstable = st.executeQuery("select * from credentials");
		ResultSetMetaData rsmd2 = credentialstable.getMetaData();
		columnsNumber = rsmd2.getColumnCount();
		columnname1 = rsmd2.getColumnLabel(1);
		columnname2 = rsmd2.getColumnLabel(2);
		String columnname3 = rsmd2.getColumnLabel(3);

		while (credentialstable.next()) {
			String cd1 = credentialstable.getString(1);
			String cd2 = credentialstable.getString(2);
			String cd3 = credentialstable.getString(3);
			System.out.print(cd1 + "		");
			System.out.print(cd2 + "		");
			System.out.println(cd3);
		}

	}

	@Test(dependsOnMethods = { "makeDatabaseConnection" })
	public void tatocAdvanceTest() throws SQLException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/Selenium_Drivers/chromedriver.exe");

		driver = new ChromeDriver();

		driver.get(" http://10.0.1.86/tatoc");
		driver.findElement(By.linkText("Advanced Course")).click();
		driver.findElement(By.xpath("//div [@class='menutop m2']")).click();
		driver.findElement(By.xpath("//span[text()='Go Next']")).click();
		String symbolname = driver.findElement(By.xpath("//div[@id='symboldisplay']")).getText();
		System.out.println(symbolname);
		ResultSet getid = st.executeQuery("select id from identity where symbol = '" + symbolname + "' ;");
		getid.next();
		String id = getid.getString(1);
		System.out.println(id);
		ResultSet getcredentials = st.executeQuery("select name,passkey from credentials where id = '" + id + "' ;");
		getcredentials.next();
		name = getcredentials.getString(1);
		passkey = getcredentials.getString(2);
		System.out.println(name);
		System.out.println(passkey);

	}

	@Test(dependsOnMethods = { "tatocAdvanceTest" })
	public void credentialsEntry() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys(name);
		driver.findElement(By.xpath("//input[@id='passkey']")).sendKeys(passkey);

	}

	@Test(dependsOnMethods = { "credentialsEntry" })
	public void getSessionId() {
		driver.get("http://10.0.1.86/tatoc/advanced/rest/#");
		sessionid = driver.findElement(By.xpath("//span[@id='session_id']")).getText().split(": ");

		System.out.println(sessionid[1]);

	}

	@Test(dependsOnMethods = { "getSessionId" })
	public void generateToken() {

		Response res = given().when().get("http://10.0.1.86/tatoc/advanced/rest/service/token/" + sessionid[1] + "")
				.then().contentType(ContentType.JSON).extract().response();
		Assert.assertEquals(res.statusCode(), 200);
		token = res.jsonPath().getString("token");
		System.out.println(token);

	}

	@Test(dependsOnMethods = { "generateToken" })
	public void registerForAccess() throws ClientProtocolException, IOException {

		String url = "http://10.0.1.86/tatoc/advanced/rest/service/register" + "?id=" + sessionid[1] + "&signature="
				+ token + "&allow_access=1";

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		// assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		client.close();
		driver.findElement(By.xpath("//a [@href = '#'] [text()='Proceed']")).click();
	}

	@Test(dependsOnMethods = { "registerForAccess" })
	public void fileHandle() throws FileNotFoundException {
		driver.findElement(By.xpath("//a[@href='/tatoc/advanced/file/handle/download']")).click();
		File file = new File("C:\\Users\\mohdkamran\\Downloads//file_handle_test.dat");
		if (file.exists())
			file.delete();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner sc = new Scanner(new File("C:\\Users\\mohdkamran\\Downloads//file_handle_test.dat"));
		String signature = "";
		while (sc.hasNext()) {
			signature = sc.next();
			System.out.println(signature);
		}
		driver.findElement(By.xpath("//input[@id ='signature']")).sendKeys(signature);
		driver.findElement(By.xpath("//input[@type='submit']")).click();

	}

}
