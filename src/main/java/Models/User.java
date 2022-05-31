package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;

    public String getFirstName() {
        return firstName;
    }

}
