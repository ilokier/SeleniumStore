package steps;

import Models.User;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.RegisterPage;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class RegisterStep extends HomePage {
    RegisterPage registerPage = new RegisterPage(driver);
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = new LoginPage(driver);

    public RegisterStep(WebDriver driver) {
        super(driver);
    }

    public void registerUser(User user) {
        homePage.goToLoginPage();
        loginPage.goToRegistrationForm();
        registerPage.fillRegisterForm(user);
        String registeredUserData = registerPage.getUserData();
        registerPage.submitRegistrationForm();

        assertThat(registeredUserData, equalTo(homePage.getUserSignedName()));

    }
}
