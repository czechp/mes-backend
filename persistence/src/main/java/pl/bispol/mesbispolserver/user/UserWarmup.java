package pl.bispol.mesbispolserver.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("development")
@Component()
class UserWarmup {
    private final Logger logger;
    private final UserRepository userRepository;

    @Autowired()
    UserWarmup(final UserRepository userRepository) {
        this.userRepository = userRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<-------> Warmup for USER entity <------->");
        saveUsers();
    }

    private void saveUsers() {
        List<User> users = Arrays.asList(
                new User("SomeFirstName1", "SomeSecondName1", "25770492944", UserRole.PRODUCTION),
                new User("SomeFirstName2", "SomeSecondName2", "25770486079", UserRole.PRODUCTION),
                new User("SomeFirstName3", "SomeSecondName3", "25770498569", UserRole.QUALITY_CONTROL),
                new User("UR First name", "UR Second name", "25770492085", UserRole.MAINTENANCE)
        );
        users.forEach(userRepository::save);
    }

}
