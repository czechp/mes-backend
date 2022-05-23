package pl.bispol.mesbispolserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
class UserCommandRepositoryImpl implements UserCommandRepository {
    private final UserRepository userRepository;

    @Autowired()
    UserCommandRepositoryImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(final User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByRfidId(final String rfidId) {
        return userRepository.existsByRfidId(rfidId);
    }

    @Override
    public Optional<User> findById(final long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean existsById(final long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteById(final long userId) {
        userRepository.deleteById(userId);
    }
}
