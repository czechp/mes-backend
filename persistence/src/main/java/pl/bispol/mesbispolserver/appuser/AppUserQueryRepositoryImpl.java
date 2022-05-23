package pl.bispol.mesbispolserver.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.appuser.dto.AppUserQueryDto;

import java.util.Optional;
import java.util.Set;

@Service()
class AppUserQueryRepositoryImpl implements AppUserQueryRepository {
    private final AppUserRepository appUserRepository;

    @Autowired()
    AppUserQueryRepositoryImpl(final AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Set<AppUserQueryDto> findBy() {
        return appUserRepository.findBy();
    }

    @Override
    public Optional<UserDetails> findByUsername(final String s) {
        return appUserRepository.findByUsername(s);
    }

    @Override
    public Optional<AppUserQueryDto> findQueryDtoByUsername(final String username) {
        return appUserRepository.findLoginDtoByUsername(username);
    }

    @Override
    public Optional<AppUserQueryDto> findById(final long id) {
        return appUserRepository.findQueryById(id);
    }
}
