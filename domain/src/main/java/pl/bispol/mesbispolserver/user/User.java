package pl.bispol.mesbispolserver.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity()
@Table(name = "users")
@Getter()
@Setter(AccessLevel.PACKAGE)
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3, message = "First name has to have minimum 3 characters")
    private String firstName;

    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3, message = "Second name has to have minimum 3 characters")
    private String secondName;

    @Length(min = 3, max = 15, message = "RFID ID has to have between 3 and 15 characters")
    private String rfidId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @PersistenceConstructor()
    User() {
    }

    User(@NotBlank(message = "First name cannot be blank")
         @Length(min = 3, message = "First name has to have minimum 3 characters") final String firstName,
         @NotBlank(message = "First name cannot be blank")
         @Length(min = 3, message = "Second name has to have minimum 3 characters") final String secondName,
         @Length(min = 11, max = 11, message = "RFID ID has to have 11 characters") final String rfidId,
         final UserRole userRole) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.rfidId = rfidId;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", rfidId='" + rfidId + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder().append(id, user.id).append(firstName, user.firstName).append(secondName, user.secondName).append(rfidId, user.rfidId).append(userRole, user.userRole).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(firstName).append(secondName).append(rfidId).append(userRole).toHashCode();
    }
}
