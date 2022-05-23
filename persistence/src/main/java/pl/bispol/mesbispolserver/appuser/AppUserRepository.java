package pl.bispol.mesbispolserver.appuser;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;
import pl.bispol.mesbispolserver.appuser.dto.AppUserQueryDto;

import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Repository()
interface AppUserRepository extends Repository<AppUser, Long> {
    AppUser save(AppUser appUser);

    Set<AppUserQueryDto> findBy();

    Optional<UserDetails> findByUsername(String s);

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<AppUser> findById(long userId);

    void deleteById(long userId);

    boolean existsById(long userId);

    @Query("SELECT a FROM AppUser a WHERE a.username = ?1")
    Optional<AppUser> findAppUserByUsername(String username);

    @Query("SELECT a FROM AppUser a WHERE a.username = ?1")
    Optional<AppUserQueryDto> findLoginDtoByUsername(String username);

    @Query("SELECT a FROM AppUser a WHERE a.id = ?1")
    Optional<AppUserQueryDto> findQueryById(long id);
}
