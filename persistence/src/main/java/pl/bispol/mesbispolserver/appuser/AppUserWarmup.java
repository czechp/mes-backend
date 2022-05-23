package pl.bispol.mesbispolserver.appuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("development")
@Component()
class AppUserWarmup {
    private final AppUserRepository appUserRepository;
    private final Logger logger;
    private final PasswordEncoder passwordEncoder;

    @Autowired()
    AppUserWarmup(final AppUserRepository appUserRepository, final PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<-------> Warmup for APPUSER entity <------->");
        saveAppUsers();
    }

    private void saveAppUsers() {
        AppUserRole[] roles = AppUserRole.values();
        List<AppUser> users = Arrays.asList(
                new AppUser("user", passwordEncoder.encode("user123"), "user@gmail.com"),
                new AppUser("management", passwordEncoder.encode("management123"), "management@gmail.com"),
                new AppUser("superuser", passwordEncoder.encode("superuser123"), "superuser@gmail.com"),
                new AppUser("admin", passwordEncoder.encode("admin123"), "admin@gmail.com")
        );
        for (int i = 0; i < roles.length; i++) {
            users.get(i).setAppUserRole(roles[i]);
        }
        users.forEach(user -> user.setEnabled(true));
        users.forEach(appUserRepository::save);
    }
}
