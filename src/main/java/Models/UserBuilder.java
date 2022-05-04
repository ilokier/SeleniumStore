package Models;

public class UserBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;


    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public User build() {
        return new User(firstName, lastName, email, password, birthDate);
    }

}

