package pl.bispol.mesbispolserver.appuser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
public class AppUserLoginDto {
    private String username;
    private String password;


    @Override
    public String toString() {
        return "AppUserLoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AppUserLoginDto that = (AppUserLoginDto) o;

        return new EqualsBuilder().append(username, that.username).append(password, that.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).append(password).toHashCode();
    }
}
