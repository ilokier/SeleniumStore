import Configuration.AppProperties;
import Configuration.DriverFactory;
import Models.Order;
import Pages.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseTest {
    private static Logger log = LoggerFactory.getLogger("BaseTest.class");
    protected WebDriver driver;
    protected static DriverFactory driverFactory;
    private static AppProperties appProperties;
    SoftAssertions softAssertions;
    protected Order orderConfirmed;
    protected Order historyOrder;
    protected BasePage basePage;
    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected LoginPage loginPage;
    protected CategoriesPage categoriesPage;
    protected CartPage cartPage;
    protected ProductPage productPage;
    protected CheckoutPage checkoutPage;
    protected OrderConfirmationPage orderConfirmationPage;
    protected OrderHistoryPage orderHistoryPage;

    @BeforeAll
    static void beforeAll() {
        appProperties = AppProperties.getInstance();
        driverFactory = new DriverFactory();
    }

    @BeforeEach
    public void beforeEach() {
        softAssertions = new SoftAssertions();
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

    protected void addRandomProductsToCart(int numberOfProducts) {
        for (int i = 0; i < numberOfProducts; i++) {
            homePage.goToRandomCategory();
            categoriesPage.goToRandomProduct();
            productPage.setRandomQuantity(5);
            productPage.addToCart();
            if (i < numberOfProducts - 1) {
                productPage.continueShoping();
                driver.get(System.getProperty("appUrl"));
            } else {
                productPage.proceedToCheckout();
            }

        }
    }

    protected static boolean assertValueIsBetween(List<Double> values, double min, double max) {
        boolean condition = false;
        if (values.isEmpty()) {
            condition = true;
        } else {
            for (Double value : values) {
                if (value >= min && value <= max) {
                    condition = true;
                }
            }
        }
        return condition;
    }
}