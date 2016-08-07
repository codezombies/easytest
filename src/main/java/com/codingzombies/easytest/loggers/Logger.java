package com.codingzombies.easytest.loggers;

/**
 * logs on this framework are organized so all loggers used should implement this interface
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public interface Logger {

    /** log statements usually used when landing on a pgge */
    void logHeading(String message);

    /** log statements used within items from a page or component, example of an item is a product on a listing page */
    void logItems(String message);

    /** log statements used within items from a page or component, example of an item is a product on a listing page */
    void logItems(int spaces, String message);
    
    
}
