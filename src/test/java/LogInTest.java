import Pages.UserFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LogInTest extends BaseTest {
    UserFactory userFactory = new UserFactory(driver);


    @Test
    public void shouldRegisterUser() {
        homePage.goToLoginPage();
        loginPage.goToRegistrationForm();
        registerPage.fillRegisterForm(userFactory.getRandomUser());
        //todo asertion
    }

    @Test
    public void shouldLogInExistingUser() {
        homePage.goToLoginPage();
        loginPage.logIn(userFactory.getRegisteredUser());
        assertThat(driver.getTitle(), equalTo(System.getProperty("expectedTitle")));
    }


}
