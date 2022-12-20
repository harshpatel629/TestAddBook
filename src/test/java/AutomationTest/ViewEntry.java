package AutomationTest;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class ViewEntry {

	WebDriver driver = null;

	// TC16_TestViewEntryButton
	// Verify View Entry Button
	@Test
	public void TestViewEntryButton() throws IOException {

		WebElement viewList = driver.findElement(By.linkText("List All Entries"));
		viewList.click();
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Boolean checkViewButton = driver.getPageSource().contains("View Details");
		WebElement viewDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(viewDetails.isEnabled());
		viewDetails.click();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC16.png"));

		assertTrue(checkViewButton);
	}

	// TC17_TestReturnButton
	// Verify whether the Return button on the page navigates back to the previous
	// page
	@Test
	public void TestReturnButton() throws IOException {

		WebElement viewList = driver.findElement(By.linkText("List All Entries"));
		viewList.click();
		WebElement viewDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		viewDetails.click();
		WebElement returnButton = driver.findElement(By.xpath("//a[contains(text(),'Return')]"));
		returnButton.click();
		String expectedUrl = "http://localhost/allList.php";
		String actualUrl = driver.getCurrentUrl();

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC17.png"));

		assertEquals(expectedUrl, actualUrl);

	}

	// TC18_TestDisplayedResults
	// Verify whether the all the field names are displayed on clicking the View
	// Details button
	@Test
	public void TestDisplayedResults() throws IOException {

		WebElement viewList = driver.findElement(By.linkText("List All Entries"));
		viewList.click();
		WebElement viewDetails = driver.findElement(By.xpath("//tbody/tr[2]/td[4]/form[2]/input[3]"));
		viewDetails.click();

		// created an array to store all field name labels for reference on checking if
		// the page loaded everything correctly
		String[] fieldNames = { "Entry Type", "First Name", "Last Name", "Business Name", "Address Line 1",
				"Address Line 2", "Address Line 3", "City", "Province", "Country", "Postal Code", "Email 1", "Email 2",
				"Email 3", "Phone 1", "Phone 3", "Web Site 1", "Web Site 2", "Web Site 3" };

		// used a for loop to be able to check all fields, and to shorten the code
		for (int x = 0; x < fieldNames.length; x++) {
			Boolean checkField = driver.getPageSource().contains(fieldNames[x]); // checks if the fieldName from the
																					// array is existing in the page
			assert (checkField = true); // once the it's checked, it will assert if its true that the fieldName is
										// existing in the page
		}

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(".//Screenshots/screen_TC18.png"));

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
