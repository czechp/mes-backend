package pl.bispol.mesbispolserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.user.dto.UserCommandDto;

@Service()
class UserCommandService {
    private final UserCommandRepository userCommandRepository;

    @Autowired()
    UserCommandService(final UserCommandRepository userCommandRepository) {
        this.userCommandRepository = userCommandRepository;
    }

    void save(final UserCommandDto userCommandDto) {
        if (!userCommandRepository.existsByRfidId(userCommandDto.getRfidId()))
            userCommandRepository.save(UserFactory.toEntity(userCommandDto));
        else
            throw new BadRequestException(Statements.RFID_ID_ALREADY_ASSIGNED);
    }

    void update(final long userId, final UserCommandDto userCommandDto) {
        User user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));

        boolean rfidIdIsUnique = false;

        if (userCommandDto.getRfidId().equals(user.getRfidId()))
            rfidIdIsUnique = true;
        else
            rfidIdIsUnique = !userCommandRepository.existsByRfidId(userCommandDto.getRfidId());

        if (rfidIdIsUnique) {
            User updatedUser = UserFactory.toEntity(userCommandDto);
            updatedUser.setId(userId);
            userCommandRepository.save(updatedUser);
        } else
            throw new BadRequestException(Statements.RFID_ID_ALREADY_ASSIGNED);
    }

    void delete(final long userId) {
        if (userCommandRepository.existsById(userId))
            userCommandRepository.deleteById(userId);
        else
            throw new NotFoundException(Statements.USER_NOT_EXISTS);
    }
}
