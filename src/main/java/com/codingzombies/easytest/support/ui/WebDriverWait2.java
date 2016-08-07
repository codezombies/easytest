package com.codingzombies.easytest.support.ui;

import java.util.function.Function;
import java.util.function.Predicate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * instance of webdriverwait that can use java.util.Function and java.util.Predicate
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class WebDriverWait2 extends WebDriverWait {

    public WebDriverWait2(final WebDriver driver, final long timeOutInSeconds) {
        super(driver, timeOutInSeconds);
    }

    public WebDriverWait2(final WebDriver driver, final long timeOutInSeconds, final long sleepInMillis) {
        super(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
    }
    
    @SuppressWarnings("unchecked")
    public <V> V until(final Function<? super WebDriver, V> isTrue) {
        return (V) super.until((com.google.common.base.Function<? super WebDriver, Object>) input -> isTrue);
    }
    
    public void until(final Predicate<WebDriver> isTrue) {
        super.until((com.google.common.base.Predicate<WebDriver>) input -> isTrue.test(input));
    }
}
