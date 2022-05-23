package pl.bispol.mesbispolserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.util.Optional;
import java.util.Set;

@Service()
class UserQueryRepositoryImpl implements UserQueryRepository {
    private final UserRepository userRepository;

    @Autowired()
    UserQueryRepositoryImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<UserQueryDto> findBy() {
        return userRepository.findBy();
    }

    @Override
    public Optional<UserQueryDto> findByRfidId(final String rfidId) {
        return userRepository.findByRfidId(rfidId);
    }


    @Override
    public Optional<UserQueryDto> findById(final long userId) {
        return userRepository.findQueryById(userId);
    }
}
