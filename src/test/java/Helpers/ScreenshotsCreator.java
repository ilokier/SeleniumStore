package Helpers;

import Tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static java.lang.System.getProperty;

public class ScreenshotsCreator extends BaseTest {
    public void capture(String tesMethodName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(getProperty("user.dir") + "src/main/resources/screenshots" + tesMethodName + ".jpg");
        try {
            FileUtils.copyFile(source, target);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
