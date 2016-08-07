package com.codingzombies.easytest.loggers;


/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public final class TestLogger implements Logger {

    private static final Logger logger = new SysoutLogger();
    
    @Override
    public void logHeading(final String message) {
        logger.logHeading(message);
    }

    @Override
    public void logItems(final String message) {
        logger.logItems(message);
    }
    
    @Override
    public void logItems(final int spaces, final String message) {
        logger.logItems(spaces, message);
    }
}
