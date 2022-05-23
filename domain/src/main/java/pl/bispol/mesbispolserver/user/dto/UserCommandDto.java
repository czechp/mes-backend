package pl.bispol.mesbispolserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import pl.bispol.mesbispolserver.user.UserRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
public class UserCommandDto {

    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3, message = "First name has to have minimum 3 characters")
    private String firstName;

    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3, message = "Second name has to have minimum 3 characters")
    private String secondName;

    @Length(min = 3, max = 15, message = "RFID ID has to have 11 characters")
    private String rfidId;

    @NotNull(message = "Role cannot be null")
    private UserRole userRole;

    @Override
    public String toString() {
        return "UserCommandDto{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", rfidId='" + rfidId + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserCommandDto that = (UserCommandDto) o;

        return new EqualsBuilder().append(firstName, that.firstName).append(secondName, that.secondName).append(rfidId, that.rfidId).append(userRole, that.userRole).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(firstName).append(secondName).append(rfidId).append(userRole).toHashCode();
    }
}
