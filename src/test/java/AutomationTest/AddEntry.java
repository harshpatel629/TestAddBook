package AutomationTest;

import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;

import base.Base;
//import demo.TestUtil;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class AddEntry {

	WebDriver driver = null;

	// TC01_AddNewEntry
	@Test(dataProvider = "getTestData")
	public void TestAddNewEntry(String type, String firstName, String lastName, String businessName, String add1,
			String add2, String add3, String city, String province, String country, String postalCode, String email1,
			String email2, String email3, String phone1Type, String phone1, String phone2Type, String phone2,
			String phone3Type, String phone3, String website1, String website2, String website3)
			throws InterruptedException, IOException {


		driver.findElement(By.linkText("Add New Entry")).click();
		

		String[] fieldIDs = { "addr_type", "addr_first_name", "addr_last_name", "addr_business", "addr_addr_line_1",
				"addr_addr_line_2", "addr_addr_line_3", "addr_city", "addr_region", "addr_country", "addr_post_code",
				"addr_email_1", "addr_email_2", "addr_email_3", "addr_phone_1_type", "addr_phone_1",
				"addr_phone_2_type", "addr_phone_2", "addr_phone_3_type", "addr_phone_3", "addr_web_url_1",
				"addr_web_url_2", "addr_web_url_3" };

		String[] fieldWebElements = { "entryType", "firstName", "lastName", "businessName", "addressLine1",
				"addressLine2", "addressLine3", "city", "province", "country", "postalCode", "email1", "email2",
				"email3", "phone1Type", "phone1", "phone2Type", "phone2", "phone3Type", "phone3", "website1",
				"website2", "website3" };

		String[] fieldValues = { type, firstName, lastName, businessName, add1, add2, add3, city, province, country,
				postalCode, email1, email2, email3, phone1Type, phone1, phone2Type, phone2, phone3Type, phone3,
				website1, website2, website3 };

		for (int x = 0; x < fieldIDs.length; x++) {

			String fieldID = fieldIDs[x];
			String fieldWebElement = fieldWebElements[x];
			String fieldValue = fieldValues[x];

			WebElement fieldWebElement2 = driver.findElement(By.name(fieldID));
			fieldWebElement2.sendKeys(fieldValue);

		}

		WebElement saveAddressDetails = driver.findElement(By.name("submit_button"));
		saveAddressDetails.click();

		Thread.sleep(1000);

		Boolean checkSuccesspage = driver.getPageSource().contains("The new address book entry was added successfully");

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC01.png"));

		assertTrue(checkSuccesspage);

	}

	// TC02_AddressBookAccessMainMenuPage
	@Test
	void AddressBookAccessMainMenuPage() throws IOException, InterruptedException

	{
		

		String actualTitle = "Address Book";

		
		String expectedTitle = driver.getTitle();

		assertEquals(expectedTitle, actualTitle);

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC06.png"));

		Thread.sleep(1000);

	}
	// TC03_AddressBookListAllEntries

	/**
	 * To check if the list all entries link is working and being redirected to the
	 * actual page successfully
	 * 
	 * @throws InterruptedException
	 **/
	@Test
	void AddressBookListAllEntries() throws IOException, InterruptedException

	{
		
		String actualTitle = "Address Book - All Entries\r\n" + "";

		Boolean checkTitle = driver.getPageSource().contains(actualTitle); // checks if the title used is existing in
																			// the page
		assert (checkTitle = true);

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC02.png"));

		Thread.sleep(1000);

	}
	// TC04_AddressBookAccessAddNewEntryPage

	/**
	 * Tests if all the add new entry page loaded correctly when the link in the
	 * main menu page is clicked
	 * 
	 * @throws InterruptedException
	 **/
	@Test
	void AddressBookAccessAddNewEntryPage() throws IOException, InterruptedException {

		WebElement addNewEntry = driver.findElement(By.linkText("Add New Entry"));
		addNewEntry.click();

		String addNewEntryExpectedURL = "http://localhost/newEntry.php";

		String addNewEntryActualURL = driver.getCurrentUrl();

		assertEquals(addNewEntryExpectedURL, addNewEntryActualURL);

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC03.png"));

		Thread.sleep(1000);

	}

	// TC05_CheckFieldsAddNewEntryPage
	/**
	 * Tests if all the fields needed for the add new entry page is complete and
	 * loaded correctly on the add new entry page
	 * 
	 * @throws InterruptedException
	 **/
	@Test
	void CheckFieldsAddNewEntryPage() throws IOException, InterruptedException {

		WebElement addNewEntry = driver.findElement(By.linkText("Add New Entry"));
		addNewEntry.click(); // clicks the add new entry link in the main menu page

		// created an array to store all field name labels for reference on checking if
		// the page loaded everything correctly
		String[] fieldNames = { "Entry Type", "First Name", "Last Name", "Business Name", "Address Line 1",
				"Address Line 2", "Address Line 3", "City", "Province", "Country", "Postal Code", "Email 1", "Email 2",
				"Email 3", "Phone 1 Type", "Phone 1 NUmber", "Phone 2 Type", "Phone 3 Type", "Phone 3 Number",
				"Web Site 1", "Web Site 2", "Web Site 3" };

		// used a for loop to be able to check all fields, and to shorten the code
		for (int x = 0; x < fieldNames.length; x++) {
			Boolean checkField = driver.getPageSource().contains(fieldNames[x]); // checks if the fieldName from the
																					// array is existing in the page
			assert (checkField = true); // once the it's checked, it will assert if its true that the fieldName is
										// existing in the page
		}

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC04.png"));

		Thread.sleep(1000);

	}

	// TC06_TestNameFieldsValidation
	@Test
	public void TestNameFieldsValidation() throws IOException {


		WebElement addNewEntry = driver.findElement(By.linkText("Add New Entry"));
		addNewEntry.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.findElement(By.id("addr_addr_line_1")).sendKeys("123 York St");
		driver.findElement(By.id("submit_button")).click();
		String expectedMessage = "An person's name or business name must be specified.";
		String actualMessage = driver.findElement(By.xpath(" //body/p[1]")).getText();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC07.png"));

		assertEquals(expectedMessage, actualMessage);

	}

	// TC07_TestEmailPhoneWebValidation
	@Test
	public void TestEmailPhoneWebValidation() throws IOException {


		WebElement addNewEntry = driver.findElement(By.linkText("Add New Entry"));
		addNewEntry.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.findElement(By.id("addr_first_name")).sendKeys("abc");
		driver.findElement(By.id("submit_button")).click();
		String expectedMessage = "At least one of the following must be entered: street/mailing address, email address, phone number or web site url.";
		String actualMessage = driver.findElement(By.xpath("//body/p[1]")).getText();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC08.png"));

		assertEquals(expectedMessage, actualMessage);
	}

	@BeforeMethod
	public void beforeMethod() {

		driver = new ChromeDriver();
		driver.get("http://localhost/index.php");
		driver.manage().window().maximize();
		
	}

	@AfterMethod
	public void afterMethod() {

		driver.close();
	}

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
	}

	@DataProvider
	public Iterator<Object[]> getTestData() throws IOException, CsvException {
		ArrayList<Object[]> testData = Base.getDataRead_CSV();
		return testData.iterator();
	}

	@AfterClass
	public void afterClass() {
		
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	@BeforeSuite
	public void beforeSuite() {
	}

	@AfterSuite
	public void afterSuite() {
	}

}
