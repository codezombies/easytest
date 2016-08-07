package com.codingzombies.easytest.loggers;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class SysoutLogger implements Logger {
	
    @Override
    public final void logHeading(final String message) {
    	logWithContext("- " + message);
    }

    @Override
    public final void logItems(final String message) {
        logItems(0, message);
    }
    
    @Override
    public void logItems(final int spaces, final String message) {
        logWithContext(StringUtils.repeat(" ", spaces * 2) + "  - " + message);
    }
    
    private final void logWithContext(final String message) {
		System.out.println(message);
    }
}
