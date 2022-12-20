package AutomationTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import base.Base;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class EditEntry extends Base {

	WebDriver driver = null;

	// TC09_TestEditContactButton
	// Verify if the edit contact button is visible on the page
	@Test
	public void TestEditContactButton() throws InterruptedException, IOException {

		WebElement viewList = driver.findElement(By.linkText("List All Entries"));
		viewList.click();
		Boolean checkEditButton = driver.getPageSource().contains("Edit Details");
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[1]/input[2]"));
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(editDetails.isEnabled());
		editDetails.click();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC09.png"));

		assertTrue(checkEditButton);

	}

	// TC10_TestEditContactURL
	// Verify whether the Edit Contact page is opened on clicking the Edit Contact
	// button
	@Test
	public void TestEditContactURL() throws IOException {

		WebElement listAllEntries = driver.findElement(By.linkText("List All Entries"));
		listAllEntries.click();
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		editDetails.click();

		String listAllEntriesExpectedURL = "http://localhost/editEntry.php";

		String listAllEntriesActualURL = driver.getCurrentUrl();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC10.png"));

		assertEquals(listAllEntriesExpectedURL, listAllEntriesActualURL);

	}

	// TC11_TestClearButton
	// Verify whether the Clear Form button is working for the Edit functionality
	@Test
	public void TestClearButton() throws IOException {

		WebElement listAllEntries = driver.findElement(By.linkText("List All Entries"));
		listAllEntries.click();
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		editDetails.click();
		WebElement clearButton = driver.findElement(By.id("reset_button"));
		clearButton.click();

		String[] fieldIDs = { "addr_type", "addr_first_name", "addr_last_name", "addr_business", "addr_addr_line_1",
				"addr_addr_line_2", "addr_addr_line_3", "addr_city", "addr_region", "addr_country", "addr_post_code",
				"addr_email_1", "addr_email_2", "addr_email_3", "addr_phone_1_type", "addr_phone_1",
				"addr_phone_2_type", "addr_phone_2", "addr_phone_3_type", "addr_phone_3", "addr_web_url_1",
				"addr_web_url_2", "addr_web_url_3" };
		Boolean clear = true;

		for (int i = 0; i < fieldIDs.length; i++) {

			WebElement inputBox = driver.findElement(By.id(fieldIDs[i]));
			if (inputBox.getAttribute("value") != null) {
				clear = false;
			}
		}

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC11.png"));

		assertTrue(clear);

	}

	// TC12_TestEditFunctionality
	// Verify whether the end-to-end edit contact functionality is working upon
	// entering valid data for all fields
	@Test
	public void TestEditFunctionality() throws IOException {

		WebElement listAllEntries = driver.findElement(By.linkText("List All Entries"));
		listAllEntries.click();
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		editDetails.click();

		String[] fieldIDs = { "addr_first_name", "addr_last_name", "addr_business", "addr_addr_line_1",
				"addr_addr_line_2", "addr_addr_line_3", "addr_city", "addr_region", "addr_country", "addr_post_code",
				"addr_email_1", "addr_email_2", "addr_email_3", "addr_phone_1", "addr_phone_2", "addr_phone_3",
				"addr_web_url_1", "addr_web_url_2", "addr_web_url_3" };
		String[] fieldValues = { "Harsh R", "Patel", "Business", "3/190, Mittahalli pudhur.", "63, Becher street",
				"234, Wharncliffe at baseline", "London", "Ontario", "London", "N6C 1A3", "hhhhn@gmail.com",
				"harshp@gmail.com", "harsh12000@gmail.com", "965754556", "3243334455", "6546546777", "chrome",
				"Firefox", "safari" };

		for (int i = 0; i < fieldIDs.length; i++) {

			WebElement element = driver.findElement(By.id(fieldIDs[i]));
			element.click();
			element.clear();
		}

		Select entryType = new Select(driver.findElement(By.id("addr_type")));
		entryType.selectByIndex(2);

		Select phone1Type = new Select(driver.findElement(By.id("addr_phone_1_type")));
		entryType.selectByIndex(1);

		Select phone2Type = new Select(driver.findElement(By.id("addr_phone_2_type")));
		phone2Type.selectByIndex(2);

		Select phone3Type = new Select(driver.findElement(By.id("addr_phone_3_type")));
		phone3Type.selectByIndex(2);

		for (int i = 0; i < fieldIDs.length; i++) {

			WebElement element = driver.findElement(By.id(fieldIDs[i]));
			element.sendKeys(fieldValues[i]);
		}

		driver.findElement(By.id("submit_button")).click();
		String expectedMessage = "The address book entry was updated successfully";
		String actualMessage = driver
				.findElement(By.xpath("//h2[contains(text(),'The address book entry was updated successfully')]"))
				.getText();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC12.png"));

		assertEquals(expectedMessage, actualMessage);

	}

	// TC13_TestEditNamesValidation
	// Verify name entry validation for edit contact functionality
	@Test
	public void TestEditNamesValidation() throws IOException {

		WebElement listAllEntries = driver.findElement(By.linkText("List All Entries"));
		listAllEntries.click();
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		editDetails.click();

		String[] fieldIDs = { "addr_first_name", "addr_last_name", "addr_business", "addr_addr_line_1",
				"addr_addr_line_2", "addr_addr_line_3", "addr_city", "addr_region", "addr_country", "addr_post_code",
				"addr_email_1", "addr_email_2", "addr_email_3", "addr_phone_1", "addr_phone_2", "addr_phone_3",
				"addr_web_url_1", "addr_web_url_2", "addr_web_url_3" };

		for (int i = 0; i < fieldIDs.length; i++) {

			WebElement element = driver.findElement(By.id(fieldIDs[i]));
			element.click();
			element.clear();
		}

		driver.findElement(By.id("addr_addr_line_1")).sendKeys("456 York St");
		driver.findElement(By.id("submit_button")).click();
		String expectedMessage = "An person's name or business name must be specified.";
		String actualMessage = driver.findElement(By.xpath("//body/p[1]")).getText();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC13.png"));

		assertEquals(expectedMessage, actualMessage);

	}

	// TC14_TestEditEmailAddressPhoneValidation
	// Verify email,address,phone entry validation for edit contact functionality
	@Test
	public void TestEditEmailAddressPhoneValidation() throws IOException {

		WebElement listAllEntries = driver.findElement(By.linkText("List All Entries"));
		listAllEntries.click();
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		editDetails.click();

		String[] fieldIDs = { "addr_first_name", "addr_last_name", "addr_business", "addr_addr_line_1",
				"addr_addr_line_2", "addr_addr_line_3", "addr_city", "addr_region", "addr_country", "addr_post_code",
				"addr_email_1", "addr_email_2", "addr_email_3", "addr_phone_1", "addr_phone_2", "addr_phone_3",
				"addr_web_url_1", "addr_web_url_2", "addr_web_url_3" };

		for (int i = 0; i < fieldIDs.length; i++) {

			WebElement element = driver.findElement(By.id(fieldIDs[i]));
			element.click();
			element.clear();
		}

		driver.findElement(By.id("addr_first_name")).sendKeys("abc");
		driver.findElement(By.id("submit_button")).click();
		String expectedMessage = "At least one of the following must be entered: street/mailing address, email address, phone number or web site url.";
		String actualMessage = driver.findElement(By.xpath("//body/p[1]")).getText();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC14.png"));

		assertEquals(expectedMessage, actualMessage);

	}

	// TC15_TestCancelButton
	// Verify Return/Cancel button for the edit entry page
	@Test
	public void TestCancelButton() throws IOException {

		WebElement listAllEntries = driver.findElement(By.linkText("List All Entries"));
		listAllEntries.click();
		WebElement editDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		editDetails.click();

		WebElement returnButton = driver.findElement(By.xpath("//a[contains(text(),'Return (Cancel)')]"));
		returnButton.click();
		String expectedUrl = "http://localhost/allList.php";
		String actualUrl = driver.getCurrentUrl();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC15.png"));

		assertEquals(expectedUrl, actualUrl);
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
