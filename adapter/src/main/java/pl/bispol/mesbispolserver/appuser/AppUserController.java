package pl.bispol.mesbispolserver.appuser;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.appuser.dto.AppUserLoginDto;
import pl.bispol.mesbispolserver.appuser.dto.AppUserQueryDto;
import pl.bispol.mesbispolserver.appuser.dto.AppUserRegisterDto;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Set;

@RestController()
@CrossOrigin("*")
@RequestMapping("/api/appusers")
@Validated()
class AppUserController {
    private final AppUserQueryService appUserQueryService;
    private final AppUserCommandService appUserCommandService;
    private final AuthenticationManager authenticationManager;

    @Autowired()
    AppUserController(final AppUserQueryService appUserQueryService,
                      final AppUserCommandService appUserCommandService, final AuthenticationManager authenticationManager) {
        this.appUserQueryService = appUserQueryService;
        this.appUserCommandService = appUserCommandService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    Set<AppUserQueryDto> getAll() {
        return appUserQueryService.getAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    AppUserQueryDto getById(
            @PathVariable(name = "userId") final long id
    ) {
        return appUserQueryService.findById(id)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    AppUserQueryDto login(
            @RequestBody() AppUserLoginDto appUserLoginDto
    ) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDto.getUsername(),
                appUserLoginDto.getPassword()));
        return appUserQueryService.findByUsername(appUserLoginDto.getUsername())
                .orElseThrow(() -> new BadRequestException(Statements.INVALID_USERNAME_OR_PASSWORD));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void register(
            @RequestBody() @Valid() final AppUserRegisterDto appUserRegisterDto
    ) {
        appUserCommandService.register(appUserRegisterDto);
    }


    @PatchMapping("/enable/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    void enableUser(
            @PathVariable(name = "userId") @Min(1L) final long userId,
            @RequestParam(name = "enable") boolean enable
    ) {
        appUserCommandService.enableUser(userId, enable);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changePassword(@RequestParam(name = "password") @Length(min = 5) final String newPassword,
                        @RequestParam(name = "email") final String email,
                        Principal user) {
        appUserCommandService.changePassword(user.getName(), email, newPassword);
    }

    @PatchMapping("/role/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    void changerRole(
            @PathVariable(name = "userId") final long userId,
            @RequestParam(name = "role") final AppUserRole appUserRole
    ) {
        appUserCommandService.changeRole(userId, appUserRole);
    }


    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    void deleteUser(@PathVariable(name = "userId") final long userId) {
        appUserCommandService.deleteUser(userId);
    }


}
