import Configuration.AppProperties;
import Configuration.DriverHandle;
import Pages.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    private static Logger log = LoggerFactory.getLogger("BaseTest.class");
    protected static WebDriver driver;
    protected static DriverHandle driverHandle;
    private static AppProperties appProperties;
    BasePage basePage;
    HomePage homePage;
    RegisterPage registerPage;
    LoginPage loginPage;
    CategoriesPage categoriesPage;
    CartPage cartPage;
    ProductPage productPage;
    CheckoutPage checkoutPage;
    OrderConfirmationPage orderConfirmationPage;
    OrderHistoryPage orderHistoryPage;

    @BeforeAll
    static void beforeAll() {
        appProperties = AppProperties.getInstance();
        driverHandle = new DriverHandle();
        driver = driverHandle.getDriver();
        log.info("<<<<<Driver initialized>>>");
        driver.get(System.getProperty("appUrl"));
    }

    @BeforeEach
    public void beforeEach() {
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        categoriesPage = new CategoriesPage(driver);
        cartPage = new CartPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
        orderConfirmationPage = new OrderConfirmationPage(driver);
        orderHistoryPage = new OrderHistoryPage(driver);
        basePage = new BasePage(driver);


    }

    @AfterAll
    static void quit() {
        driver.quit();
        log.debug("<<<<<<driver closed properly>>>>>");
    }
}