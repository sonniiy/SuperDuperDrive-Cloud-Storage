package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL = "http://localhost:";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get(baseURL + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void userSignupLogin() {
		String userName = "TestUsername";
		String password = "TestPassword";
		String firstName = "TestfirstName";
		String lastName = "TestlastName";

		driver.get(baseURL + this.port + "/signup");

		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signup(firstName, lastName, userName, password);
		signUpPage.clickLogin();


		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
	}

	@Test
	@Order(2)
	public void testUserSignupLogin() {

		WebDriverWait wait = new WebDriverWait(driver, 1);

		userSignupLogin();

		HomePage homePage = new HomePage(driver);
		//wait.until(ExpectedConditions.titleContains("Home"));

		Assertions.assertEquals("Home", driver.getTitle());

		homePage.logOut();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login",driver.getTitle());

		driver.get(baseURL + this.port + "/home");

		wait.until(ExpectedConditions.titleContains("Login"));

		Assertions.assertEquals("Login", driver.getTitle());


	}


}
