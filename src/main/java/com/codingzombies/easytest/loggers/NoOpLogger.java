package com.codingzombies.easytest.loggers;

public class NoOpLogger implements Logger {

    @Override
    public void logHeading(final String message) {
        // noop
    }

    @Override
    public void logItems(final String message) {
        // noop
    }

    @Override
    public void logItems(final int spaces, final String message) {
        // noop
    }

}
