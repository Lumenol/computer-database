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

public class PagesSwitchDashboard {

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

    public static final String NEXT = "»";
    public static final String PREVIOUS = "«";

    private final ITDatabase testDatabase = ITDatabase.getInstance();
    private WebDriver driver;

    private static final String BASE_URL = System.getProperty("integration-test.url",
	    "http://localhost:8080/computer-database");

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

	String expectedMannufacturerName = "";
	if (Objects.nonNull(expectedComputerDTO.getMannufacturer())) {
	    expectedMannufacturerName = expectedComputerDTO.getMannufacturer().getName();
	}
	Assert.assertEquals("nom fabriquant", expectedMannufacturerName, mannufacturerName);
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

    @Test
    public void pagesSwitchDashboard() {
	driver.get(BASE_URL);

	// page 1 size 50
	checkPagination("1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_1_SIZE_50, LAST_ID_PAGE_1_SIZE_50);

	// next page 2 size 50
	clickPaginationButton(NEXT);
	checkPagination(PREVIOUS, "1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_2_SIZE_50, LAST_ID_PAGE_2_SIZE_50);

	// go to page 5
	clickPaginationButton("5");
	checkPagination(PREVIOUS, "3", "4", "5", "6", "7", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_5_SIZE_50, LAST_ID_PAGE_5_SIZE_50);

	// previous go to page 4
	clickPaginationButton("4");
	checkPagination(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
	checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_4_SIZE_50, LAST_ID_PAGE_4_SIZE_50);

	// page size 10
	clickButtonSize(10);
	checkPagination(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_4_SIZE_10, LAST_ID_PAGE_4_SIZE_10);

	// page 6
	clickPaginationButton("6");
	checkPagination(PREVIOUS, "4", "5", "6", "7", "8", NEXT);

	// page 8
	clickPaginationButton("8");
	checkPagination(PREVIOUS, "6", "7", "8", "9", "10", NEXT);

	// page 10
	clickPaginationButton("10");
	checkPagination(PREVIOUS, "8", "9", "10", "11", "12", NEXT);
	checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_10_SIZE_10, LAST_ID_PAGE_10_SIZE_10);

	// page size 100 redirect to page 6
	clickButtonSize(100);
	checkPagination(PREVIOUS, "2", "3", "4", "5", "6");
	checkComputers(PAGE_SIZE_100, FIRST_ID_PAGE_6_SIZE_100, LAST_ID_PAGE_6_SIZE_100);

	// go to page 2
	clickPaginationButton("2");
	// previous to page 1
	clickPaginationButton(PREVIOUS);
	checkPagination("1", "2", "3", "4", "5", NEXT);
	checkComputers(PAGE_SIZE_100, FIRST_ID_PAGE_1_SIZE_100, LAST_ID_PAGE_1_SIZE_100);

    }

    private void pageLoadTimeout() {
	driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }
}
