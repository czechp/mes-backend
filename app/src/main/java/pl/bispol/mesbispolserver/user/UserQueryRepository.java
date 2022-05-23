package pl.bispol.mesbispolserver.user;

import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.util.Optional;
import java.util.Set;

interface UserQueryRepository {
    Set<UserQueryDto> findBy();

    Optional<UserQueryDto> findByRfidId(String rfidId);

    Optional<UserQueryDto> findById(long userId);
}
