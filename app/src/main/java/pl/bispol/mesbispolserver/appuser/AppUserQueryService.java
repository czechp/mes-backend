package pl.bispol.mesbispolserver.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.appuser.dto.AppUserQueryDto;

import java.util.Optional;
import java.util.Set;

@Service()
public class AppUserQueryService implements UserDetailsService {
    private final AppUserQueryRepository appUserQueryRepository;

    @Autowired()
    AppUserQueryService(final AppUserQueryRepository appUserQueryRepository) {
        this.appUserQueryRepository = appUserQueryRepository;
    }

    Set<AppUserQueryDto> getAll() {
        return appUserQueryRepository.findBy();
    }

    @Override
    public UserDetails loadUserByUsername(final String s) {
        return appUserQueryRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(Statements.USER_NOT_EXISTS));
    }

    Optional<AppUserQueryDto> findByUsername(final String username) {
        return appUserQueryRepository.findQueryDtoByUsername(username);

    }

    Optional<AppUserQueryDto> findById(final long id) {
        return appUserQueryRepository.findById(id);
    }
}
