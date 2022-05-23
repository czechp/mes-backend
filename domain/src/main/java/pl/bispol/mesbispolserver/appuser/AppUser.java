package pl.bispol.mesbispolserver.appuser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;

@Entity()
@Table(name = "appusers")
@Getter()
@Setter(AccessLevel.PACKAGE)
public class AppUser implements UserDetails {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username cannot be blank")
    @Length(min = 2, message = "Username has to have minimum 2 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 5, message = "Password has to have minimum 5 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "It isn't correct email format")
    private String email;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private boolean enabled;

    @PersistenceConstructor()
    AppUser() {
    }

    AppUser(@NotBlank(message = "Username cannot be blank")
            @Length(min = 2, message = "Username has to have minimum 2 characters") final String username,
            @NotBlank(message = "Password cannot be blank")
            @Length(min = 5, message = "Password has to have minimum 5 characters") final String password,
            @NotBlank(message = "Email cannot be blank") @Email(message = "It isn't correct email format") final String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.appUserRole = AppUserRole.USER;
        this.enabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.appUserRole));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", appUserRole=" + appUserRole +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AppUser appUser = (AppUser) o;

        return new EqualsBuilder().append(id, appUser.id).append(enabled, appUser.enabled).append(username, appUser.username).append(password, appUser.password).append(email, appUser.email).append(appUserRole, appUser.appUserRole).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(username).append(password).append(email).append(appUserRole).append(enabled).toHashCode();
    }
}
