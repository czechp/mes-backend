package pl.bispol.mesbispolserver.user;

import java.util.Optional;

interface UserCommandRepository {
    void save(User user);

    boolean existsByRfidId(String rfidId);

    Optional<User> findById(long userId);

    boolean existsById(long userId);

    void deleteById(long userId);
}
