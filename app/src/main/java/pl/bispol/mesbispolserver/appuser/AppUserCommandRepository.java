package pl.bispol.mesbispolserver.appuser;

import java.util.Optional;

interface AppUserCommandRepository {
    boolean existsByUsernameOrEmail(String username, String email);

    AppUser save(AppUser toEntity);

    Optional<AppUser> findById(long userId);

    void deleteUserById(long userId);

    boolean existsById(long userId);

    Optional<AppUser> findByUsername(String username);
}
