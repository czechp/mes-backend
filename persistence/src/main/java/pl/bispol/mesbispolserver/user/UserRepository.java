package pl.bispol.mesbispolserver.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Repository()
interface UserRepository extends Repository<User, Long> {
    User save(User user);

    Set<UserQueryDto> findBy();

    boolean existsByRfidId(String rfidId);

    Optional<User> findById(long userId);

    boolean existsById(long userId);

    void deleteById(long userId);

    Optional<UserQueryDto> findByRfidId(String rfidId);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<UserQueryDto> findQueryById(long userId);
}
