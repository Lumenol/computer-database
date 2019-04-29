package com.excilys.cdb.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PagesSwitchDashboard {
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

    private List<WebElement> getPaginationElements() {
	return driver.findElements(By.xpath("//ul[@class='pagination']/li"));
    }
    
    private List<WebElement> getComputersTableLines(){
	return driver.findElements(By.xpath("//tbody[@id='results']/tr"));
    }

    private void assertEquals(WebElement element,ComputerDTO computerDTO) {
	final String id = element.findElement(By.xpath("td/input")).getAttribute("value");
	Assert.assertEquals(Objects.toString(computerDTO.getId()), id);
	
	
    }
    
    @Test
    public void pagesSwitchDashboard() {
	// Test name: pagesSwitchDashboard
	// Step # | name | target | value | comment
	// 1 | open | /computer-database/ | |
	driver.get("http://localhost:8080/computer-database/");

	// get list of pagination item
	List<WebElement> paginatiotnElementsPage = getPaginationElements();

	List<String> actualTestPagination = paginatiotnElementsPage.stream().map(WebElement::getText)
		.collect(Collectors.toList());
	List<String> expectedTexPagination = Arrays.asList("1", "2", "3", "4", "5", "»");
	Assert.assertEquals(expectedTexPagination, actualTestPagination);

	
	final List<WebElement> computersTableLines = getComputersTableLines();
	final ComputerDTO expectedComputer1 = new ComputerDTO();
	expectedComputer1.setId(1);
	expectedComputer1.setName("MacBook Pro 15.4 inch");
	final CompanyDTO expectedMannufacturer1 = new CompanyDTO();
	expectedComputer1.setName("Apple Inc.");
	expectedComputer1.setMannufacturer(expectedMannufacturer1);
	
	assertEquals(computersTableLines.get(0),expectedComputer1);
	
	
	/*
	 * // 4 | click | css=li:nth-child(7) span | |
	 * driver.findElement(By.cssSelector("li:nth-child(7) span")).click(); // 5 |
	 * click | linkText=5 | | driver.findElement(By.linkText("5")).click(); // 6 |
	 * click | linkText=7 | | driver.findElement(By.linkText("7")).click(); // 7 |
	 * click | linkText=« | | driver.findElement(By.linkText("«")).click(); // 8 |
	 * click | linkText=8 | | driver.findElement(By.linkText("8")).click();
	 */
	fail("pas fini");
    }
}
