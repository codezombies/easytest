package com.codingzombies.easytest.support.ui;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;

public enum BrowserSize {

    DEFAULT {
        @Override
        public void resizeWindow(final WebDriver driver) {
            // do nothing
        }
    }, 
    MAXIMIZE {
        @Override
        public void resizeWindow(final WebDriver driver) {
            driver.manage().window().maximize();
        }
    },
    TABLET_PORTRAIT {
        @Override
        public void resizeWindow(final WebDriver driver) {
            final Window window = driver.manage().window();
            window.setSize(new Dimension(768, 1024));
            window.setPosition(new Point(10,  0));
        }
    },
    TABLET_LANDSCAPE {
        @Override
        public void resizeWindow(final WebDriver driver) {
            final Window window = driver.manage().window();
            window.setSize(new Dimension(1024, 768));
            window.setPosition(new Point(10,  0));
        }
    },
    MOBILE_PORTRAIT {
        @Override
        public void resizeWindow(final WebDriver driver) {
            final Window window = driver.manage().window();
            window.setSize(new Dimension(414, 736));
            window.setPosition(new Point(10,  0));
        }
    },
    MOBILE_LANDSCAPE {
        @Override
        public void resizeWindow(final WebDriver driver) {
            final Window window = driver.manage().window();
            window.setSize(new Dimension(736, 414));
            window.setPosition(new Point(10,  0));
        }
    }
    ;
    
    public abstract void resizeWindow(WebDriver driver);
    
}
