package Tests;

import Pages.UserFactory;
import org.testng.annotations.Test;
import steps.RegisterStep;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LogInTest extends BaseTest {

    @Test
    public void shouldRegisterUser() {
        new RegisterStep(driver).registerUser(new UserFactory(driver).getRandomUser());
    }

    @Test
    public void shouldLogInExistingUser() {
        homePage.goToLoginPage();
        loginPage.logIn(new UserFactory(driver).getRegisteredUser());
        assertThat(driver.getTitle(), equalTo(System.getProperty("expectedTitle")));
    }


}
