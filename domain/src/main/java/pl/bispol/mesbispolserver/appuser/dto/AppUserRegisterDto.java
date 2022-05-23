package pl.bispol.mesbispolserver.appuser.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter()
@Setter()
public class AppUserRegisterDto {
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 2, message = "Username has to have minimum 2 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 5, message = "Password has to have minimum 5 characters")
    private String password;


    @NotBlank(message = "Password cannot be blank")
    @Length(min = 5, message = "Password has to have minimum 5 characters")
    private String passwordConf;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "It isn't correct email format")
    private String email;


    public AppUserRegisterDto() {
    }

    public AppUserRegisterDto(@NotBlank(message = "Username cannot be blank") @Length(min = 2, message = "Username has to have minimum 2 characters") final String username, @NotBlank(message = "Password cannot be blank") @Length(min = 5, message = "Password has to have minimum 5 characters") final String password, @NotBlank(message = "Password cannot be blank") @Length(min = 5, message = "Password has to have minimum 5 characters") final String passwordConf, @NotBlank(message = "Email cannot be blank") @Email(message = "It isn't correct email format") final String email) {
        this.username = username;
        this.password = password;
        this.passwordConf = passwordConf;
        this.email = email;
    }

    @Override
    public String toString() {
        return "AppUserRegisterDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AppUserRegisterDto that = (AppUserRegisterDto) o;

        return new EqualsBuilder().append(username, that.username).append(password, that.password).append(email, that.email).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).append(password).append(email).toHashCode();
    }
}
