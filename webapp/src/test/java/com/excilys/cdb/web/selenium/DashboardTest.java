package com.excilys.cdb.web.selenium;

import com.excilys.cdb.shared.dto.ComputerDTO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DashboardTest {

    public static final String NEXT = "»";
    public static final String PREVIOUS = "«";
    private static final int PAGE_SIZE_10 = 10;
    private static final long FIRST_ID_PAGE_4_SIZE_10_BY_NAME_ASC = 413;
    private static final long LAST_ID_PAGE_4_SIZE_10_BY_NAME_ASC = 10;
    private static final long FIRST_ID_PAGE_10_SIZE_10_BY_NAME_ASC = 367;
    private static final long LAST_ID_PAGE_10_SIZE_10_BY_NAME_ASC = 407;
    private static final int PAGE_SIZE_50 = 50;
    private static final int FIRST_ID_PAGE_1_SIZE_50_BY_NAME_ASC = 381;
    private static final int LAST_ID_PAGE_1_SIZE_50_BY_NAME_ASC = 355;
    private static final long FIRST_ID_PAGE_2_SIZE_50_BY_NAME_ASC = 385;
    private static final long LAST_ID_PAGE_2_SIZE_50_BY_NAME_ASC = 407;
    private static final int FIRST_ID_PAGE_4_SIZE_50_BY_NAME_ASC = 522;
    private static final int LAST_ID_PAGE_4_SIZE_50_BY_NAME_ASC = 160;
    private static final int FIRST_ID_PAGE_5_SIZE_50_BY_NAME_ASC = 281;
    private static final int LAST_ID_PAGE_5_SIZE_50_BY_NAME_ASC = 162;
    private static final int PAGE_SIZE_100 = 100;
    private static final long FIRST_ID_PAGE_1_SIZE_100_BY_NAME_ASC = 381;
    private static final long LAST_ID_PAGE_1_SIZE_100_BY_NAME_ASC = 407;
    private static final long FIRST_ID_PAGE_6_SIZE_100_BY_NAME_ASC = 155;
    private static final long LAST_ID_PAGE_6_SIZE_100_BY_NAME_ASC = 323;

    private static final long FIRST_ID_PAGE_1_SIZE_10_APPLE_BY_NAME_ASC = 55;
    private static final long LAST_ID_PAGE_1_SIZE_10_APPLE_BY_NAME_ASC = 13;
    private static final long FIRST_ID_PAGE_2_SIZE_10_APPLE_BY_NAME_ASC = 403;
    private static final long LAST_ID_PAGE_2_SIZE_10_APPLE_BY_NAME_ASC = 314;
    private static final long FIRST_ID_PAGE_5_SIZE_10_APPLE_BY_NAME_ASC = 79;
    private static final long LAST_ID_PAGE_5_SIZE_10_APPLE_BY_NAME_ASC = 566;

    private static final String BASE_URL = System.getProperty("integration-test.url",
            "http://localhost:8080/computer-database");

    private WebDriver driver;

    private ITDatabase database = ITDatabase.getInstance();

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.phantomjs().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new PhantomJSDriver();
        driver.manage().window().setSize(new Dimension(1366, 768));
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        final int random = ThreadLocalRandom.current().nextInt();
        String username = "testUser" + random;
        String password = "testUserPassword" + random;
        registration(username, password);
        login(username, password);
    }

    public void registration(String username, String password) {
        driver.get(BASE_URL + "/users");
        driver.findElement(By.id("login")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("passwordCheck")).sendKeys(password);
        driver.findElement(By.id("add")).click();
    }

    public void login(String username, String password) {
        driver.get(BASE_URL + "/login");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login")).click();
    }

    @AfterEach
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
        Assertions.assertEquals(Long.toString(expectedComputerDTO.getId()), id, "id");

        final String name = actualElement.findElement(By.xpath("td[2]")).getText();
        Assertions.assertEquals(expectedComputerDTO.getName(), name, "nom");

        final String introduced = actualElement.findElement(By.xpath("td[3]")).getText();
        Assertions.assertEquals(Objects.toString(expectedComputerDTO.getIntroduced(), ""), introduced, "introduced");

        final String discontinued = actualElement.findElement(By.xpath("td[4]")).getText();
        Assertions.assertEquals(Objects.toString(expectedComputerDTO.getDiscontinued(), ""), discontinued, "discontinued");

        final String manufacturerName = actualElement.findElement(By.xpath("td[5]")).getText();

        Assertions.assertEquals(Objects.toString(expectedComputerDTO.getmanufacturer(), ""),
                manufacturerName, "nom fabriquant");
    }

    private void clickPaginationButton(String text) {
        getPaginationPageContainer().findElement(By.linkText(text)).click();
    }

    private void checkPagination(String... expectedPagination) {
        String[] actualTestPagination = getPaginationElements().stream().map(WebElement::getText)
                .toArray(String[]::new);
        Assertions.assertArrayEquals(expectedPagination, actualTestPagination, "Pagination");
    }

    private void checkComputers(int size, long firstID, long lastID) {

        List<WebElement> computersTableLines = getComputersTableLines();

        Assertions.assertTrue(computersTableLines.size() <= size, "Taille de page");
        assertEquals(database.findComputerById(firstID), computersTableLines.get(0));
        assertEquals(database.findComputerById(lastID), computersTableLines.get(computersTableLines.size() - 1));
    }

    private void clickButtonSize(int size) {
        driver.findElement(By.xpath("//button[.=" + size + "]")).click();
    }

    private void search(String s) {
        driver.findElement(By.id("searchbox")).sendKeys(s);
        driver.findElement(By.id("searchForm")).submit();
    }

    private void assertCount(long count) {
        final String homeTitle = driver.findElement(By.id("homeTitle")).getText();
        Assertions.assertTrue(homeTitle.startsWith(Long.toString(count)), "Le nombre d'ordinateur est faux");
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
        checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_1_SIZE_10_APPLE_BY_NAME_ASC,
                LAST_ID_PAGE_1_SIZE_10_APPLE_BY_NAME_ASC);

        // next page 2 size 10
        clickPaginationButton(NEXT);
        assertCount(count);
        checkPagination(PREVIOUS, "1", "2", "3", "4", "5", NEXT);
        checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_2_SIZE_10_APPLE_BY_NAME_ASC,
                LAST_ID_PAGE_2_SIZE_10_APPLE_BY_NAME_ASC);

        // go to page 5
        clickPaginationButton("5");
        assertCount(count);
        checkPagination(PREVIOUS, "1", "2", "3", "4", "5");
        checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_5_SIZE_10_APPLE_BY_NAME_ASC,
                LAST_ID_PAGE_5_SIZE_10_APPLE_BY_NAME_ASC);
    }

    @Test
    public void pagesSwitchDashboard() {
        final int count = 574;
        driver.get(BASE_URL);

        // page 1 size 50
        assertCount(count);
        checkPagination("1", "2", "3", "4", "5", NEXT);
        checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_1_SIZE_50_BY_NAME_ASC, LAST_ID_PAGE_1_SIZE_50_BY_NAME_ASC);

        // next page 2 size 50
        clickPaginationButton(NEXT);
        assertCount(count);
        checkPagination(PREVIOUS, "1", "2", "3", "4", "5", NEXT);
        checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_2_SIZE_50_BY_NAME_ASC, LAST_ID_PAGE_2_SIZE_50_BY_NAME_ASC);

        // go to page 5
        clickPaginationButton("5");
        assertCount(count);
        checkPagination(PREVIOUS, "3", "4", "5", "6", "7", NEXT);
        checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_5_SIZE_50_BY_NAME_ASC, LAST_ID_PAGE_5_SIZE_50_BY_NAME_ASC);

        // previous go to page 4
        clickPaginationButton("4");
        assertCount(count);
        checkPagination(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
        checkComputers(PAGE_SIZE_50, FIRST_ID_PAGE_4_SIZE_50_BY_NAME_ASC, LAST_ID_PAGE_4_SIZE_50_BY_NAME_ASC);

        // page size 10
        clickButtonSize(10);
        assertCount(count);
        checkPagination(PREVIOUS, "2", "3", "4", "5", "6", NEXT);
        checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_4_SIZE_10_BY_NAME_ASC, LAST_ID_PAGE_4_SIZE_10_BY_NAME_ASC);

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
        checkComputers(PAGE_SIZE_10, FIRST_ID_PAGE_10_SIZE_10_BY_NAME_ASC, LAST_ID_PAGE_10_SIZE_10_BY_NAME_ASC);

        // page size 100 redirect to page 6
        clickButtonSize(100);
        assertCount(count);
        checkPagination(PREVIOUS, "2", "3", "4", "5", "6");
        checkComputers(PAGE_SIZE_100, FIRST_ID_PAGE_6_SIZE_100_BY_NAME_ASC, LAST_ID_PAGE_6_SIZE_100_BY_NAME_ASC);

        // go to page 2
        clickPaginationButton("2");
        assertCount(count);
        // previous to page 1
        clickPaginationButton(PREVIOUS);
        assertCount(count);
        checkPagination("1", "2", "3", "4", "5", NEXT);
        checkComputers(PAGE_SIZE_100, FIRST_ID_PAGE_1_SIZE_100_BY_NAME_ASC, LAST_ID_PAGE_1_SIZE_100_BY_NAME_ASC);

    }

}
