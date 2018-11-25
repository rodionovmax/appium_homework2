import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.*;

//package com.mkyong;

import org.junit.Test;

import javax.swing.text.html.CSS;
import java.util.List;

import static net.sourceforge.htmlunit.corejs.javascript.ScriptableObject.hasProperty;
import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class TestExercise2 {
    public AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        capabilities.setCapability("app", "/Users/rodionovmax/IdeaProjects/JavaAppiumAutomation_homework2/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

//    @Test
//    public void testSearchFieldAndQuit()
//    {
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find 'Search Wikipedia'",
//                5
//        );
//
//        waitForElementPresent(
//                By.xpath("//*[contains(@text, 'Search…')]"),
//                "Cannot find 'Search…' input",
//                5
//        );
//    }
//
//    @Test
//    public void testSearchWordAndCancelSearch()
//    {
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find 'Search Wikipedia'",
//                5
//        );
//
//        waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Appium",
//                "Cannot find 'Search...' field",
//                5
//        );
//
//        waitForManyElementsPresent(
//                By.id("org.wikipedia:id/page_list_item_container"),
//                "Cannot find search results",
//                5
//        );
//
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "Cannot find 'Close' button",
//                5
//        );
//
//        waitForManyElementsNotPresent(
//                "org.wikipedia:id/page_list_item_title",
//                "Results still present on the screen",
//                10
//        );
//    }

    @Test
    public void testSearchAndVerifyResults()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "iphone",
                "Cannot find 'Search...' field",
                5
        );

        List <WebElement> title_elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        String search_titles = String.valueOf(title_elements);

        for(WebElement title:title_elements)
        {
            System.out.println(title.getAttribute("text"));
            Assert.assertThat(search_titles,
                hasItem(title.getAttribute("text")), is("Iphone"));
        }

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private List<WebElement> waitForManyElementsPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    private boolean waitForManyElementsNotPresent(String by, String error_message, long timeoutInSeconds)
    {
        List<WebElement> searchResults = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfAllElements(searchResults)
        );
    }

}
