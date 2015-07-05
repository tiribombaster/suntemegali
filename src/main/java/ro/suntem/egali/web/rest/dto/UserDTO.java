package ro.suntem.egali.web.rest.dto;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;


    private Boolean fizice;
    private Boolean somatic;
    private Boolean auditiv;
    private Boolean vizual;
    private Boolean psihic;
    private Boolean boliRare;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(min = 2, max = 5)
    private String langKey;

    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(String login, String password, String firstName, String lastName, String email, String langKey, Boolean fizice,
                   Boolean somatic,
                   Boolean auditiv,
                   Boolean vizual,
                   Boolean psihic,
                   Boolean boliRare,

                   List<String> roles) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.langKey = langKey;
        this.roles = roles;
        this.boliRare=boliRare;
        this.auditiv=auditiv;
        this.somatic=somatic;
        this.vizual=vizual;
        this.psihic=psihic;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getLangKey() {
        return langKey;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Boolean isFizice() {
        return fizice;
    }

    public Boolean isSomatic() {
        return somatic;
    }

    public Boolean isAuditiv() {
        return auditiv;
    }

    public Boolean isVizual() {
        return vizual;
    }

    public Boolean isPsihic() {
        return psihic;
    }

    public Boolean isBoliRare() {
        return boliRare;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", langKey='" + langKey + '\'' +
            ", roles=" + roles +
            '}';
    }
}
