package Pages;

import Models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RegisterPage extends BasePage {
    private static Logger log = LoggerFactory.getLogger("RegisterPage.class");

    @FindBy(css = ".custom-radio")
    private List<WebElement> socialTitleRadio;
    @FindBy(css = "[name='firstname']")
    private WebElement firstName;
    @FindBy(css = "[name='lastname']")
    private WebElement lastName;
    @FindBy(css = "[name='email']")
    private WebElement email;
    @FindBy(css = "[name='password']")
    private WebElement password;
    @FindBy(css = "[name='birthday']")
    private WebElement birthday;
    @FindBy(css = ".custom-checkbox")
    private List<WebElement> checkBoxValueList;
    @FindBy(css = ".custom-checkbox input")
    private List<WebElement> checkBoxList;
    @FindBy(css = ".custom-checkbox input[required]")
    private List<WebElement> requiredCheckBoxList;
    @FindBy(css = ".form-control-submit")
    private WebElement submitButton;
    private UserFactory userFactory;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }


    public RegisterPage fillRegisterForm(User user) {
        getRandomListEl(socialTitleRadio);
        sendKeysToElement(firstName, user.getFirstName());
        sendKeysToElement(lastName, user.getLastName());
        sendKeysToElement(email, user.getEmail());
        sendKeysToElement(password, user.getPassword());
        sendKeysToElement(birthday, user.getBirthDate());
        //checkRequiredBoxes();
        checkAllCheckBoxes();
        return this;
    }

    private void checkRequiredBoxes() {
        moveToElement(checkBoxValueList.get(2));
        for (WebElement requiredCheckBox : requiredCheckBoxList) {
            requiredCheckBox.click();
        }
    }

    public String getUserData() {
        String user = getElementAttribute(firstName, "value") + " " + getElementAttribute(lastName, "value");
        log.info("User name and lastname: " + user);
        return user;
    }

    private void checkAllCheckBoxes() {
        for (WebElement check : checkBoxValueList) {
            moveToElement(check);
            WebElement checkInput = check.findElement(By.cssSelector(".custom-checkbox input"));
            checkInput.click();
        }
    }

    public void submitRegistrationForm() {
        clickOnElement(submitButton);
    }
}
