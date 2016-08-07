package com.codingzombies.easytest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.easytest.config.DriverType;
import com.codingzombies.easytest.drivers.DriverFactory;
import com.codingzombies.easytest.support.ui.ActionablePage;
import com.codingzombies.easytest.support.ui.Conditions;
import com.codingzombies.easytest.support.ui.InPageActions;
import com.codingzombies.easytest.support.ui.VoidPageActions;
import com.codingzombies.easytest.support.ui.WebDriverWait2;
import com.codingzombies.easytest.util.ScreenshotsUtil;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class EasyTest implements AutoCloseable {

    private final WebDriverWait2 driverWait;
    private final DeviceWebDriver driver;
    private final EasyTestOptions options;
    private boolean firstPageLoaded = false;
    
    public EasyTest(final WebDriver driver) {
        this(driver, new EasyTestOptions());
    }

    public EasyTest(final WebDriver driver, final EasyTestOptions options) {
        this(DriverFactory.getDriver(driver), options);
    }

    public EasyTest(final DriverType driverType) {
        this(driverType, new EasyTestOptions());
    }

    public EasyTest(final DriverType driverType, final EasyTestOptions options) {
        this(DriverFactory.getDriver(driverType), options);
    }

    private EasyTest(final DeviceWebDriver driver, final EasyTestOptions options) {
        super();
        this.driver = driver;
        this.driverWait = new WebDriverWait2(driver, 30);
        this.options = options;
    }

    public void start(final String rootUrl) {
        driver.get(rootUrl);
        options.getBrowserSize().resizeWindow(driver);
        driverWait.until(Conditions.pageLoaded());
    }

    @Override
    public void close() throws Exception {
        runQuietly(() -> {
            Thread.sleep(3000); // delay browser closing
        });
        driver.quit();
    }

    public <T> T newPage(final InPageActions<T> actions) {
        try {
            waitForPageToLoad();
            return actions.execute(new ActionablePage(this));
        }
        catch (final Exception e) {
            ScreenshotsUtil.takeScreenshot(driver);
            throw e;
        }
    }

    public void newPage(final VoidPageActions actions) {
        try {
            waitForPageToLoad();
            actions.execute(new ActionablePage(this));
        }
        catch (final Exception e) {
            ScreenshotsUtil.takeScreenshot(driver);
            throw e;
        }
    }

    private void waitForPageToLoad() {
        if(!firstPageLoaded) {
            // we don't care about the staleness of body during first page loaded on the browser
            firstPageLoaded = true;
            return;
        }
        final WebElement body = driver.findElement(By.tagName("body"));

        // calling the first newPage with waitForPageToLoad() will fail because
        // body will never become stale
        runQuietly(() -> {
            driverWait.until(Conditions.pageLoaded());
            new WebDriverWait2(driver, 5).until(ExpectedConditions.stalenessOf(body));
        });
    }

    private void runQuietly(final SilentCall run) {
        try {
            run.call();
        }
        catch (final Exception e) {
            /* ignore */ }
    }

    public DeviceWebDriver getDriver() {
        return driver;
    }

    public WebDriverWait2 getDriverWait() {
        return driverWait;
    }

    public EasyTestOptions getOptions() {
        return options;
    }

    @FunctionalInterface
    private static interface SilentCall {
        void call() throws Exception;
    }
}
