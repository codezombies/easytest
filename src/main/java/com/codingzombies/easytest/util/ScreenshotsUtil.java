package com.codingzombies.easytest.util;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.codingzombies.easytest.DeviceWebDriver;
import com.google.common.io.Files;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public final class ScreenshotsUtil {

    private ScreenshotsUtil() {
    }

    public static File takeScreenshot(final DeviceWebDriver driver) {
        try {
            final String path = ScreenshotsUtil.class.getClassLoader().getResource("").getPath();
            final File rootPath = new File(path).getParentFile();
            final File screenshotsPath = new File(rootPath, "screenshots");
            if (!screenshotsPath.exists()) {
                screenshotsPath.mkdirs();
            }
            final File movedFile = new File(screenshotsPath, System.currentTimeMillis() + "_FAILED.png");
            takeScreenshotWithSelenium(driver, movedFile);
            return movedFile;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void takeScreenshotWithSelenium(final WebDriver driver, final File file) throws Exception {
        final File seleniumFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(seleniumFile, file);
    }

}
