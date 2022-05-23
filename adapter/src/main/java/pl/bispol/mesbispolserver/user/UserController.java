package pl.bispol.mesbispolserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.user.dto.UserCommandDto;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import javax.validation.Valid;
import java.util.Set;

@RestController()
@RequestMapping("/api/users")
@CrossOrigin("*")
class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Autowired()
    UserController(final UserQueryService userQueryService, final UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    Set<UserQueryDto> findAll() {
        return userQueryService.findAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_SUPERUSER", "ROLE_ADMIN"})
    UserQueryDto findById(
            @PathVariable(name = "userId") final long userId
    ) {
        return userQueryService.findById(userId)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));

    }


    @GetMapping("/rfid/{rfidId}")
    @ResponseStatus(HttpStatus.OK)
    UserQueryDto findByRfid(
            @PathVariable(name = "rfidId") final String rfidId
    ) {
        return userQueryService.findByRfidId(rfidId)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));

    }

    @GetMapping("/line/{lineId}")
    UserQueryDto findByLineId(@PathVariable(name = "lineId") final long lineId) {
        return userQueryService.findByLineId(lineId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    void addUser(
            @RequestBody() @Valid() UserCommandDto userCommandDto
    ) {
        userCommandService.save(userCommandDto);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    void updateUser(
            @PathVariable(name = "userId") final long userId,
            @RequestBody() @Valid() final UserCommandDto userCommandDto
    ) {
        userCommandService.update(userId, userCommandDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    void deleteUser(
            @PathVariable(name = "userId") final long userId
    ) {
        userCommandService.delete(userId);
    }


}
