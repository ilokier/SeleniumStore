package Pages;

import Models.User;
import Models.UserBuilder;
import org.openqa.selenium.WebDriver;

public class UserFactory extends BasePage {
    public UserFactory(WebDriver driver) {
        super(driver);
    }

    public User getRandomUser() {
        User randomUser = new UserBuilder()
                .firstName(getRandomFirstName())
                .lastName(getRandomlastName())
                .email(getRandomemail())
                .password(getRandomPassword())
                .birthDate(getRandomBirthDate())
                .build();
        return randomUser;
    }

    public User getRegisteredUser() {
        User registeredUser = new UserBuilder()
                .email(System.getProperty("email"))
                .password(System.getProperty("password"))
                .build();
        return registeredUser;
    }

}
