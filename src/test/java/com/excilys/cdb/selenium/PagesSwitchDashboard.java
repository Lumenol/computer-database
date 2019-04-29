package com.excilys.cdb.selenium;

import com.excilys.cdb.database.ITDatabase;
import com.excilys.cdb.dto.ComputerDTO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PagesSwitchDashboard {

    public static final String NEXT = "»";
    public static final String PREVIOUS = "«";
    private final ITDatabase testDatabase = ITDatabase.getInstance();
    private WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        WebDriverManager.firefoxdriver().setup();

    }

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
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

    private void assertEquals(WebElement element, ComputerDTO computerDTO) {
        final String id = element.findElement(By.xpath("td[1]/input")).getAttribute("value");
        Assert.assertEquals(Long.toString(computerDTO.getId()), id);

        final String name = element.findElement(By.xpath("td[2]")).getText();
        Assert.assertEquals(computerDTO.getName(), name);

        final String introduced = element.findElement(By.xpath("td[3]")).getText();
        Assert.assertEquals(Objects.toString(computerDTO.getIntroduced(), ""), introduced);

        final String discontinued = element.findElement(By.xpath("td[4]")).getText();
        Assert.assertEquals(Objects.toString(computerDTO.getDiscontinued(), ""), discontinued);

        final String mannufacturerName = element.findElement(By.xpath("td[5]")).getText();
        if (Objects.nonNull(computerDTO.getMannufacturer())) {
            Assert.assertEquals(computerDTO.getMannufacturer().getName(), mannufacturerName);
        } else {
            Assert.assertEquals("", mannufacturerName);
        }
    }

    private void clickPaginationButton(String text) {
        getPaginationPageContainer().findElement(By.linkText(text)).click();
        pageLoadTimeout();
    }

    @Test
    public void pagesSwitchDashboard() {
        driver.get("http://localhost:8080/computer-database/");

        // get list of pagination items

        List<String> actualTestPagination = getPaginationElements().stream().map(WebElement::getText)
                .collect(Collectors.toList());
        List<String> expectedTexPagination = Arrays.asList("1", "2", "3", "4", "5", NEXT);
        Assert.assertEquals(expectedTexPagination, actualTestPagination);

        List<WebElement> computersTableLines = getComputersTableLines();
        Assert.assertEquals(computersTableLines.size(), 50);
        for (int i = 0; i < 20; i++) {
            assertEquals(computersTableLines.get(i), testDatabase.findComputerById(i + 1));
        }
        assertEquals(computersTableLines.get(49), testDatabase.findComputerById(50));

        //go to page 4
        clickPaginationButton("4");

        actualTestPagination = getPaginationElements().stream().map(WebElement::getText)
                .collect(Collectors.toList());

        expectedTexPagination = Arrays.asList(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
        Assert.assertEquals(expectedTexPagination, actualTestPagination);

        computersTableLines = getComputersTableLines();
        Assert.assertEquals(computersTableLines.size(), 50);


        /*
         * // 4 | click | css=li:nth-child(7) span | |
         * driver.findElement(By.cssSelector("li:nth-child(7) span")).click(); // 5 |
         * click | linkText=5 | | driver.findElement(By.linkText("5")).click(); // 6 |
         * click | linkText=7 | | driver.findElement(By.linkText("7")).click(); // 7 |
         * click | linkText=« | | driver.findElement(By.linkText("«")).click(); // 8 |
         * click | linkText=8 | | driver.findElement(By.linkText("8")).click();
         */
    }

    private void pageLoadTimeout() {
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }
}
