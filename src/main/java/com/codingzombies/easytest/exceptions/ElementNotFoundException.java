package com.codingzombies.easytest.exceptions;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.codingzombies.easytest.util.EasyTestUtil;

public class ElementNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3637340438175897811L;
    
    public ElementNotFoundException(final String selector) {
        super("No element/s found with the given selector: " + selector);
    }

    public ElementNotFoundException(final String selector, final List<WebElement> unFilteredElements) {
        super("No element/s found with the given selector: " + selector + " using predicate.");
        System.err.println("Elements before filter: ");
        System.err.println(EasyTestUtil.toString(unFilteredElements));
    }
    
}
