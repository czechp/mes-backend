package pl.bispol.mesbispolserver.appuser;

import org.springframework.security.core.userdetails.UserDetails;
import pl.bispol.mesbispolserver.appuser.dto.AppUserQueryDto;

import java.util.Optional;
import java.util.Set;

public interface AppUserQueryRepository {
    Set<AppUserQueryDto> findBy();

    Optional<UserDetails> findByUsername(String s);

    Optional<AppUserQueryDto> findQueryDtoByUsername(String username);

    Optional<AppUserQueryDto> findById(long id);
}
