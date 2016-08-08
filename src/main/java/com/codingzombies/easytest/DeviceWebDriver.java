package com.codingzombies.easytest;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class DeviceWebDriver implements WebDriver, JavascriptExecutor, TakesScreenshot, HasInputDevices, HasCapabilities, HasTouchScreen {

    private final WebDriver driver;
    private final WebDriverWait driverWait;
    private final Actions actions;

    public DeviceWebDriver(final WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(driver, 30);
        this.actions = new Actions(driver);
    }

    public WebDriver driver() {
        return this.driver;
    }

    public WebDriverWait driverWait() {
        return this.driverWait;
    }

    public Actions actions() {
        return this.actions;
    }

    public void moveTo(final WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void scrollTo(final WebElement element) {
        executeScript("arguments[0].scrollIntoView()", element);
    }

    /* delegated methods */

    @Override
    public Keyboard getKeyboard() {
        return ((HasInputDevices) driver).getKeyboard();
    }

    @Override
    public Mouse getMouse() {
        return ((HasInputDevices) driver).getMouse();
    }

    @Override
    public void get(final String paramString) {
        driver.get(paramString);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(final By paramBy) {
        return driver.findElements(paramBy);
    }

    @Override
    public WebElement findElement(final By paramBy) {
        return driver.findElement(paramBy);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    @Override
    public Object executeAsyncScript(final String script, final Object... args) {
        return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    }

    @Override
    public Object executeScript(final String script, final Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public File getScreenshot() throws WebDriverException {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    @Override
    public <X> X getScreenshotAs(final OutputType<X> target) throws WebDriverException {
        return ((TakesScreenshot) driver).getScreenshotAs(target);
    }

    @Override
    public Capabilities getCapabilities() {
        return ((HasCapabilities) driver).getCapabilities();
    }

    @Override
    public TouchScreen getTouch() {
        return ((HasTouchScreen) driver).getTouch();
    }
}
