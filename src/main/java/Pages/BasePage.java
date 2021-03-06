package Pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BasePage {
    private static Logger log = LoggerFactory.getLogger("BasePage.class");
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;
    static Faker faker = new Faker();
    static String datePattern = "MM/dd/yyyy";
    static SimpleDateFormat format = new SimpleDateFormat(datePattern);
    protected JavascriptExecutor jse;


    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        actions = new Actions(driver);

    }

    public String getRandomFirstName() {
        return faker.name().firstName();
    }

    public String getRandomlastName() {
        return faker.name().lastName();
    }

    public String getRandomemail() {
        return faker.internet().emailAddress();
    }

    public String getRandomPassword() {
        return faker.internet().password(8, 16);
    }

    public String getRandomBirthDate() {
        String birthday = format.format(faker.date().birthday(8, 16));
        log.info("Birthday date: " + birthday);
        return birthday;
    }

    public String getRandomAdress() {
        return faker.address().streetName() + " " + faker.address().streetAddressNumber();
    }

    public String getRandomCity() {
        return faker.address().cityName();
    }

    public String getRandomPostalCode() {
        return faker.numerify("##-###");
    }

    public void clickOnElement(WebElement element) {
        try {
            highLightenerMethod(element);
            waitForLoad();
            waitForElementToBeClickable(element);
            element.click();
        } catch (StaleElementReferenceException e) {
            log.info("msg: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getRandomNumberValue(int bound) {
        String random = String.valueOf(new Random().nextInt(bound - 1) + 1);
        return random;
    }

    public WebElement getRandomListEl(List<WebElement> elements) {
        WebElement element = elements.get(new Random().nextInt(elements.size()));
        moveToElement(element);
        return element;
    }

    public String getRandomListElementText(List<WebElement> elements) {
        String result = elements.get(new Random().nextInt(elements.size())).getText();
        log.info("Search product is: " + result);
        return result;
    }

    public String getTodayDate() {
        String todayDate = format.format(new Date(System.currentTimeMillis()));
        log.info("Today date is: " + todayDate);
        return todayDate;
    }

    public void sendKeysToElement(WebElement element, String text) {
        waitToBeVisible(element);
        highLightenerMethod(element);
        element.clear();
        element.sendKeys(text);
    }

    public void sendKeysCombination(WebElement element, String text) {
        waitToBeVisible(element);
        highLightenerMethod(element);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }

    public String getElementAttribute(WebElement element, String attribute) {
        waitToBeVisible(element);
        highLightenerMethod(element);
        return element.getAttribute(attribute);
    }

    public String getElementText(WebElement element) {
        moveToElement(element);
        waitToBeVisible(element);
        highLightenerMethod(element);
        return element.getText();
    }

    public void moveToElement(WebElement element) {
        waitToBeVisible(element);
        actions.moveToElement(element).build();//wywali??am performa
    }

    public void moveAndWait(WebElement element) {
        actions.moveToElement(element);
        waitToBeVisible(element);
    }

    public void scrollAndClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        clickOnElement(element);

    }

    public void clickAndHold(WebElement element) {
        waitForElementToBeClickable(element);
        actions.clickAndHold(element).build();
    }

    public void release() {
        actions.release();
    }

    public double getDoubleValueFromPrice(String price) {
        price = price.replace("$", "");
        double priceValue = Double.parseDouble(price);
        return priceValue;
    }

    public String getQuantityValueOfProduct(String quantity) {
        quantity = quantity.replace("x", "");
        return quantity;
    }

    public void waitToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitToListVisible(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
    }

    public void highLightenerMethod(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        waitToBeVisible(element);
        js.executeScript("arguments[0].setAttribute('style', 'background: lightgreen; border: 5px solid green;')", element);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public boolean isPageLoaded() {
//        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete")
//                && ((JavascriptExecutor) driver).executeScript("return jQuery.active").toString().equals("0");//todo implement in slider
//    }

    void waitForLoad() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
    }

    public boolean isPageLoaded() {
        return isDOMLoaded() && isAjaxCompletedTasks();
    }

    public boolean isDOMLoaded() {
        String state = jse.executeScript("return document.readyState").toString();
        return state.equals("complete");
    }

    public boolean isAjaxCompletedTasks() {
        String state = jse.executeScript("return jQuery.active").toString();
        return state.equals("0");
    }

    public void waitForLoad2() {
        wait.until(driver -> isPageLoaded());
    }

}