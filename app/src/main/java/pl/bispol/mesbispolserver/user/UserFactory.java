package pl.bispol.mesbispolserver.user;

import pl.bispol.mesbispolserver.user.dto.UserCommandDto;

class UserFactory {
    UserFactory() {
    }

    static User toEntity(UserCommandDto userCommandDto) {
        return new User(userCommandDto.getFirstName(),
                userCommandDto.getSecondName(),
                userCommandDto.getRfidId(),
                userCommandDto.getUserRole());

    }
}
