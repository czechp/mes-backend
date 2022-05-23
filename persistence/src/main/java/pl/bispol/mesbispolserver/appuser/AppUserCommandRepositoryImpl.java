package pl.bispol.mesbispolserver.appuser;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
class AppUserCommandRepositoryImpl implements AppUserCommandRepository {
    private final AppUserRepository appUserRepository;

    AppUserCommandRepositoryImpl(final AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public boolean existsByUsernameOrEmail(final String username, final String email) {
        return appUserRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public AppUser save(final AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public Optional<AppUser> findById(final long userId) {
        return appUserRepository.findById(userId);
    }

    @Override
    public void deleteUserById(final long userId) {
        appUserRepository.deleteById(userId);
    }

    @Override
    public boolean existsById(final long userId) {
        return appUserRepository.existsById(userId);
    }

    @Override
    public Optional<AppUser> findByUsername(final String username) {
        return appUserRepository.findAppUserByUsername(username);
    }
}
