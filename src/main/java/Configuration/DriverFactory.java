package Configuration;

import Models.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {
    private static Logger log = LoggerFactory.getLogger("BrowserEnvironment.class");
    private WebDriver driver;
    private Browser browser;

    public WebDriver getDriver() {
        this.browser = AppProperties.getInstance().yamlReader.getConfig().getBrowser();
        log.info("Chosen browser: " + browser);
        switch (this.browser) {
            case CHROME:
                driver = getChromeDriver();
                break;
            case FIREFOX:
                driver = getFireFoxDriver();
                break;
            case IE:
                driver = getIeDriver();
                break;
            case EDGE:
                driver = getEdgeDriver();
                break;
            default:
                driver = getChromeDriver();
        }

        return this.driver;
    }


    private WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        chromeOptions.addArguments("start-maximized");
        return new ChromeDriver(chromeOptions);
    }

    private WebDriver getFireFoxDriver() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriverManager.firefoxdriver().setup();
        firefoxOptions.addArguments("start-maximized");
        return new FirefoxDriver(firefoxOptions);
    }

    private WebDriver getIeDriver() {
        InternetExplorerOptions ieOptions = new InternetExplorerOptions().ignoreZoomSettings();
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver(ieOptions);
    }

    private WebDriver getEdgeDriver() {
        EdgeOptions edgeOptions = new EdgeOptions();
        WebDriverManager.edgedriver().setup();
        edgeOptions.addArguments("start-maximized");
        return new EdgeDriver(edgeOptions);
    }

}


