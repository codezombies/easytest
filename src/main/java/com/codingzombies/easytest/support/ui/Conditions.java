package com.codingzombies.easytest.support.ui;

import java.util.function.Function;
import java.util.function.Predicate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public final class Conditions {

	private Conditions() {}
	
	public static Predicate<WebElement> displayed() {
		return(element) -> element.isDisplayed();
	}

	public static Predicate<WebElement> enabled() {
		return (element) -> element.isEnabled();
	}
	
	public static Predicate<WebDriver> urlChanged(final String currentURL) {
	    return (driver) -> !driver.getCurrentUrl().equalsIgnoreCase(currentURL);
	}

	public static Function<WebDriver, Boolean> ajaxComplete() {
	    return (driver) -> (Boolean)((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
	}
	
	public static Function<WebDriver, Boolean> pageLoaded() {
	    return (driver) -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	}

}
