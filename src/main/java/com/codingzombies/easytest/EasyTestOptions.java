package com.codingzombies.easytest;

import java.util.HashMap;
import java.util.Map;

import com.codingzombies.easytest.loggers.Logger;
import com.codingzombies.easytest.loggers.TestLogger;
import com.codingzombies.easytest.support.ui.ActionableDataTemplate;
import com.codingzombies.easytest.support.ui.ActionableTemplate;
import com.codingzombies.easytest.support.ui.BrowserSize;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class EasyTestOptions {

    private final Map<String, ActionableTemplate> actionTemplates = new HashMap<>();
    private final Map<String, ActionableDataTemplate<?>> actionDataTemplates = new HashMap<>();
    private BrowserSize browserSize = BrowserSize.MAXIMIZE;
    private Logger logger = new TestLogger();

    public void addActionTemplate(final String key, final ActionableTemplate template) {
        this.actionTemplates.put(key, template);
    }

    public void addActionDataTemplate(final String key, final ActionableDataTemplate<?> template) {
        this.actionDataTemplates.put(key, template);
    }

    public void addActionTemplate(final ActionableTemplate template) {
        this.actionTemplates.put(template.getClass().getName(), template);
    }

    public void addActionDataTemplate(final ActionableDataTemplate<?> template) {
        this.actionDataTemplates.put(template.getClass().getName(), template);
    }

    public Map<String, ActionableTemplate> getActionTemplates() {
        return actionTemplates;
    }

    public Map<String, ActionableDataTemplate<?>> getActionDataTemplates() {
        return actionDataTemplates;
    }

    public BrowserSize getBrowserSize() {
        return browserSize;
    }

    public void setBrowserSize(final BrowserSize browserSize) {
        this.browserSize = browserSize;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(final Logger logger) {
        this.logger = logger;
    }

}
