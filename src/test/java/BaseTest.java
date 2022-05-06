import Configuration.AppProperties;
import Configuration.DriverFactory;
import Pages.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    private static Logger log = LoggerFactory.getLogger("BaseTest.class");
    protected WebDriver driver;
    protected static DriverFactory driverFactory;
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
        driverFactory = new DriverFactory();
    }

    @BeforeEach
    public void beforeEach() {
        driver = driverFactory.getDriver();
        log.info("<<<<<Driver initialized>>>");
        driver.get(System.getProperty("appUrl"));
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

    @AfterEach
    void quit() {
        driver.quit();
        log.debug("<<<<<<driver closed properly>>>>>");
    }
}