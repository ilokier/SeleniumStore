package Pages;

import Models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[name='email']")
    private WebElement email;
    @FindBy(css = "[name='password']")
    private WebElement password;
    @FindBy(id = "submit-login")
    private WebElement signInButton;
    @FindBy(css = ".no-account a")
    private WebElement createAccountLink;

    public LoginPage goToRegistrationForm() {
        clickOnElement(signInButton);
        clickOnElement(createAccountLink);
        return this;
    }

    public LoginPage logIn(User user) {
        sendKeysToElement(email, user.getEmail());
        sendKeysToElement(password, user.getPassword());
        clickOnElement(signInButton);
        return this;
    }
}
