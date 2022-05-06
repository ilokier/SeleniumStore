import Pages.UserFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LogInTest extends BaseTest {

    @Test
    public void shouldRegisterUser() {
        homePage.goToLoginPage();
        loginPage.goToRegistrationForm();
        registerPage.fillRegisterForm(new UserFactory(driver).getRandomUser());
        String registeredUserData = registerPage.getUserData();
        registerPage.submitRegistrationForm();
        assertThat(registeredUserData, equalTo(homePage.getUserSignedName()));
    }

    @Test
    public void shouldLogInExistingUser() {
        homePage.goToLoginPage();
        loginPage.logIn(new UserFactory(driver).getRegisteredUser());
        assertThat(driver.getTitle(), equalTo(System.getProperty("expectedTitle")));
    }


}
