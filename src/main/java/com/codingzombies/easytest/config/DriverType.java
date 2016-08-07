package com.codingzombies.easytest.config;

import java.util.logging.Level;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public enum DriverType {
    // hover does not seem to work when using phantom js
    PHANTOMJS {
        @Override
        public Capabilities getCapabilities() {
            final DesiredCapabilities phantom = DesiredCapabilities.phantomjs();
            phantom.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:36.0) Gecko/20100101 Firefox/36.0 WebKit");
            phantom.setJavascriptEnabled(true);
            phantom.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--webdriver-loglevel=NONE" });
            return phantom;
        }
    },
    CHROME {
        @Override
        public Capabilities getCapabilities() {
            final DesiredCapabilities chrome = DesiredCapabilities.chrome();
            final LoggingPreferences loggingprefs = new LoggingPreferences();
            loggingprefs.enable(LogType.BROWSER, Level.ALL);
            loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);
            loggingprefs.enable(LogType.PROFILER, Level.ALL);
            chrome.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
            return chrome;
        }
    },
    FIREFOX {
        @Override
        public Capabilities getCapabilities() {
            return DesiredCapabilities.firefox();
        }
    },
    // driver.actions() not supported on safari
    SAFARI {
        @Override
        public Capabilities getCapabilities() {
            final DesiredCapabilities capability = DesiredCapabilities.safari();
            final SafariOptions safariOptions = new SafariOptions();
            safariOptions.setUseCleanSession(true);
            capability.setCapability(SafariOptions.CAPABILITY, safariOptions);
            return capability;
        }
    },
    IE {
        @Override
        public Capabilities getCapabilities() {
            return DesiredCapabilities.internetExplorer();
        }
    };

    public abstract Capabilities getCapabilities();

}