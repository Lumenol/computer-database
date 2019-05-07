package com.excilys.cdb.selenium;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.excilys.cdb.database.ITDatabase;
import com.excilys.cdb.dto.ComputerDTO;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DashboardTest {

    public static final String NEXT = "»";
    public static final String PREVIOUS = "«";
    private static final int PAGE_SIZE_10 = 10;
    private static final long FIRST_ID_PAGE_4_SIZE_10 = 31;
    private static final long LAST_ID_PAGE_4_SIZE_10 = 40;
    private static final long FIRST_ID_PAGE_10_SIZE_10 = 91;
    private static final long LAST_ID_PAGE_10_SIZE_10 = 100;
    private static final int PAGE_SIZE_50 = 50;
    private static final int FIRST_ID_PAGE_1_SIZE_50 = 1;
    private static final int LAST_ID_PAGE_1_SIZE_50 = 50;
    private static final long FIRST_ID_PAGE_2_SIZE_50 = 51;
    private static final long LAST_ID_PAGE_2_SIZE_50 = 100;
    private static final int FIRST_ID_PAGE_4_SIZE_50 = 151;
    private static final int LAST_ID_PAGE_4_SIZE_50 = 200;
    private static final int FIRST_ID_PAGE_5_SIZE_50 = 201;
    private static final int LAST_ID_PAGE_5_SIZE_50 = 250;
    private static final int PAGE_SIZE_100 = 100;
    private static final long FIRST_ID_PAGE_1_SIZE_100 = 1;
    private static final long LAST_ID_PAGE_1_SIZE_100 = 100;
    private static final long FIRST_ID_PAGE_6_SIZE_100 = 501;
    private static final long LAST_ID_PAGE_6_SIZE_100 = 574;

    private static final long FIRST_ID_PAGE_1_SIZE_10_APPLE = 1;
    private static final long LAST_ID_PAGE_1_SIZE_10_APPLE = 16;
    private static final long FIRST_ID_PAGE_2_SIZE_10_APPLE = 17;
    private static final long LAST_ID_PAGE_2_SIZE_10_APPLE = 39;
    private static final long FIRST_ID_PAGE_5_SIZE_10_APPLE = 516;
    private static final long LAST_ID_PAGE_5_SIZE_10_APPLE = 574;

    private static final String BASE_URL = System.getProperty("integration-test.url",
	    "http://localhost:8080/computer-database");

    private final ITDatabase testDatabase = ITDatabase.getInstance();
    private WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
	WebDriverManager.phantomjs().setup();
    }

    @Before
    public void setUp() {
	driver = new PhantomJSDriver();
    }

    @After
    public void tearDown() {
	driver.quit();
    }

    private WebElement getPaginationPageContainer() {
	return driver.findElement(By.xpath("//ul[@class='pagination']"));
    }

    private List<WebElement> getPaginationElements() {
	return getPaginationPageContainer().findElements(By.xpath("li"));
    }

    private List<WebElement> getComputersTableLines() {
	return driver.findElements(By.xpath("//tbody[@id='results']/tr"));
    }

    private void assertEquals(ComputerDTO expectedComputerDTO, WebElement actualElement) {
	final String id = actualElement.findElement(By.xpath("td[1]/input")).getAttribute("value");
	Assert.assertEquals("id", Long.toString(expectedComputerDTO.getId()), id);

	final String name = actualElement.findElement(By.xpath("td[2]")).getText();
	Assert.assertEquals("nom", expectedComputerDTO.getName(), name);

	final String introduced = actualElement.findElement(By.xpath("td[3]")).getText();
	Assert.assertEquals("introduced", Objects.toString(expectedComputerDTO.getIntroduced(), ""), introduced);

	final String discontinued = actualElement.findElement(By.xpath("td[4]")).getText();
	Assert.assertEquals("discontinued", Objects.toString(expectedComputerDTO.getDiscontinued(), ""), discontinued);

	final String mannufacturerName = actualElement.findElement(By.xpath("td[5]")).getText();

	Assert.assertEquals("nom fabriquant", Objects.toString(expectedComputerDTO.getMannufacturer(), ""),
		mannufacturerName);
    }

    private void clickPaginationButton(String text) {
	getPaginationPageContainer().findElement(By.linkText(text)).click();
	pageLoadTimeout();
    }

    private void checkPagination(String... expectedPagination) {
	String[] actualTestPagination = getPaginationElements().stream().map(WebElement::getText)
		.toArray(String[]::new);
	Assert.assertArrayEquals("Pagination", expectedPagination, actualTestPagination);
    }

    private void checkComputers(int size, long firstID, long lastID) {

	List<WebElement> computersTableLines = getComputersTableLines();

	Assert.assertTrue("Taille de page", computersTableLines.size() <= size);
	assertEquals(testDatabase.findComputerById(firstID), computersTableLines.get(0));
	assertEquals(testDatabase.findComputerById(lastID), computersTableLines.get(computersTableLines.size() - 1));
    }

    private void clickButtonSize(int size) {
	driver.findElement(By.xpath("//button[.=" + size + "]")).click();
	pageLoadTimeout();
    }

    private void search(String s) {
	driver.findElement(By.id("searchbox")).sendKeys(s);
	driver.findElement(By.id("searchForm")).submit();
    }

    private void assertCount(long count) {
	final String homeTitle = driver.findElement(By.id("homeTitle")).getText();
	Assert.assertEquals(count + " Computers found", homeTitle);
    }

    @Test
    public void testSearch() {
	final int count = 46;
	driver.get(BASE_URL);
	search("Apple");
	clickButtonSize(10);
	// page 1 size 10 search Apple

	assertCount(count);
	checkPagination("1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_1_SIZE_10_APPLE, LAST_ID_PAGE_1_SIZE_10_APPLE);

	// next page 2 size 10
	clickPaginationButton(NEXT);
	assertCount(count);
	checkPagination(PREVIOUS, "1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_2_SIZE_10_APPLE, LAST_ID_PAGE_2_SIZE_10_APPLE);

	// go to page 5
	clickPaginationButton("5");
	assertCount(count);
	checkPagination(PREVIOUS, "1", "2", "3", "4", "5");
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_5_SIZE_10_APPLE, LAST_ID_PAGE_5_SIZE_10_APPLE);
    }

    @Test
    public void pagesSwitchDashboard() {
	final int count = 574;
	driver.get(BASE_URL);

	// page 1 size 50
	assertCount(count);
	checkPagination("1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_1_SIZE_50, LAST_ID_PAGE_1_SIZE_50);

	// next page 2 size 50
	clickPaginationButton(NEXT);
	assertCount(count);
	checkPagination(PREVIOUS, "1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_2_SIZE_50, LAST_ID_PAGE_2_SIZE_50);

	// go to page 5
	clickPaginationButton("5");
	assertCount(count);
	checkPagination(PREVIOUS, "3", "4", "5", "6", "7", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_5_SIZE_50, LAST_ID_PAGE_5_SIZE_50);

	// previous go to page 4
	clickPaginationButton("4");
	assertCount(count);
	checkPagination(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_4_SIZE_50, LAST_ID_PAGE_4_SIZE_50);

	// page size 10
	clickButtonSize(10);
	assertCount(count);
	checkPagination(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_4_SIZE_10, LAST_ID_PAGE_4_SIZE_10);

	// page 6
	clickPaginationButton("6");
	assertCount(count);
	checkPagination(PREVIOUS, "4", "5", "6", "7", "8", NEXT);

	// page 8
	clickPaginationButton("8");
	assertCount(count);
	checkPagination(PREVIOUS, "6", "7", "8", "9", "10", NEXT);

	// page 10
	clickPaginationButton("10");
	assertCount(count);
	checkPagination(PREVIOUS, "8", "9", "10", "11", "12", NEXT);
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_10_SIZE_10, LAST_ID_PAGE_10_SIZE_10);

	// page size 100 redirect to page 6
	clickButtonSize(100);
	assertCount(count);
	checkPagination(PREVIOUS, "2", "3", "4", "5", "6");
	checkComputers(PAGE_SIZE_100, FIRST_ID_PAGE_6_SIZE_100, LAST_ID_PAGE_6_SIZE_100);

	// go to page 2
	clickPaginationButton("2");
	assertCount(count);
	// previous to page 1
	clickPaginationButton(PREVIOUS);
	assertCount(count);
	checkPagination("1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_100, FIRST_ID_PAGE_1_SIZE_100, LAST_ID_PAGE_1_SIZE_100);

    }

    private void pageLoadTimeout() {
	driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }
}
