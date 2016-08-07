package com.codingzombies.easytest.drivers;

import java.io.File;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.safari.SafariDriver;

import com.codingzombies.easytest.DeviceWebDriver;
import com.codingzombies.easytest.config.Config;
import com.codingzombies.easytest.config.DriverType;

import net.anthavio.phanbedder.Phanbedder;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public final class DriverFactory {

    /**
     * default is phantom as it will auto initialize all the needed paths
     * 
     * @return
     */
    public static final DeviceWebDriver getDriver() throws Exception {
        return getDriver(DriverType.PHANTOMJS);
    }

    public static final DeviceWebDriver getDriver(final DriverType driverType) {
        WebDriver driver = null;
        switch (driverType) {
            case PHANTOMJS:
                if (System.getProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY) == null) {
                    final File unpack = Phanbedder.unpack();
                    System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, unpack.getAbsolutePath());
                }
                driver = new PhantomJSDriver(driverType.getCapabilities());
                break;
            case CHROME:
                initializeChromeVariables();
                driver = new ChromeDriver(driverType.getCapabilities());
                break;
            case FIREFOX:
                driver = new FirefoxDriver(driverType.getCapabilities());
                break;
            case SAFARI:
                driver = new SafariDriver(driverType.getCapabilities());
                break;
            case IE:
                driver = new InternetExplorerDriver(driverType.getCapabilities());
                break;
        }
        
        return new DeviceWebDriver(driver);
    }

    public static final DeviceWebDriver getDriver(final WebDriver webDriver) {
        return new DeviceWebDriver(webDriver);
    }

    private static void initializeChromeVariables() {
        final String environment = StringUtils.lowerCase(Config.get("environment", environment()));
        final URL resource = DriverFactory.class.getClassLoader().getResource("drivers");
        final String chromeExecutableFolder = resource.getPath() + File.separator + environment;
        if (environment.equals("windows")) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeExecutableFolder + File.separator + "chromedriver.exe");
        }
        else {
            final File chromeExecutable = new File(chromeExecutableFolder, "chromedriver");
            chromeExecutable.setExecutable(true);
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeExecutable.getAbsolutePath());
        }
    }

    private static String environment() {
        final String osname = System.getProperty("os.name").toLowerCase();
        if (osname.contains("win")) {
            return "windows";
        }
        else if (osname.contains("mac os")) {
            return "osx";
        }
        else if (osname.contains("linux")) {
            final String osarch = System.getProperty("os.arch");
            if (osarch.equals("i386")) {
                return "linux32";
            }
            else {
                return "linux64";
            }
        }
        return null;
    }

}
