package Pages;

import Models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RegisterPage extends BasePage {

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
    @FindBy(css = ".custom-checkbox input")
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

//    private void checkRequiredBoxes() {
//        for (WebElement checkBox : checkBoxValueList) {
//            scrollToElement(checkBox);
//            WebElement requiredCheckBox = checkBox.findElement(By.cssSelector(".custom-checkbox input"));
//            if (element.hasSttribute.equalTo("required")) {
//                requiredCheckBox.click();
//            }
//        }
//    }

    private void checkAllCheckBoxes() {
        for (WebElement check : checkBoxValueList) {
            scrollToElement(check);
            WebElement checkInput = check.findElement(By.cssSelector(".custom-checkbox input"));
            checkInput.click();
        }
    }

    public void submitRegistrationForm() {
        clickOnElement(submitButton);
    }
}
