package pl.bispol.mesbispolserver.user.dto;

import pl.bispol.mesbispolserver.user.UserRole;

public interface UserQueryDto {
    long getId();

    String getFirstName();

    String getSecondName();

    String getRfidId();

    UserRole getUserRole();
}
