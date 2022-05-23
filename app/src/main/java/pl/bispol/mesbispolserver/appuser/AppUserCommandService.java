package pl.bispol.mesbispolserver.appuser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.appuser.dto.AppUserRegisterDto;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;

import javax.transaction.Transactional;

@Service()
class AppUserCommandService {


    private final AppUserCommandRepository appUserCommandRepository;
    private final PasswordEncoder passwordEncoder;

    AppUserCommandService(final AppUserCommandRepository appUserCommandRepository, final PasswordEncoder passwordEncoder) {
        this.appUserCommandRepository = appUserCommandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    void register(final AppUserRegisterDto appUserRegisterDto) {
        boolean userNotExists = !appUserCommandRepository.existsByUsernameOrEmail(appUserRegisterDto.getUsername(), appUserRegisterDto.getEmail());
        boolean passwordsEqual = appUserRegisterDto.getPassword().equals(appUserRegisterDto.getPasswordConf());

        if (userNotExists && passwordsEqual) {
            AppUser appUser = AppUserFactory.toEntity(appUserRegisterDto);
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            appUserCommandRepository.save(appUser);
        } else if (!userNotExists)
            throw new BadRequestException(Statements.USER_ALREADY_EXISTS);
        else
            throw new BadRequestException(Statements.PASSWORD_ARE_NOT_EQUAL);
    }

    void enableUser(final long userId, final boolean enable) {
        AppUser appUser = appUserCommandRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));
        appUser.setEnabled(enable);
        appUserCommandRepository.save(appUser);
    }

    void deleteUser(final long userId) {
        if (appUserCommandRepository.existsById(userId))
            appUserCommandRepository.deleteUserById(userId);
        else
            throw new NotFoundException(Statements.USER_NOT_EXISTS);
    }

    @Transactional()
    public void changePassword(final String username, final String email, final String newPassword) {
        AppUser appUser = appUserCommandRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));
        if (appUser.getEmail().equals(email)) {
            appUser.setPassword(passwordEncoder.encode(newPassword));
        } else
            throw new BadRequestException(Statements.EMAIL_DO_NOT_MATCH_TO_USER);
    }

    @Transactional()
    public void changeRole(final long userId, final AppUserRole appUserRole) {
        AppUser appUser = appUserCommandRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));
        appUser.setAppUserRole(appUserRole);
    }
}
