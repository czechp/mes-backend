package pl.bispol.mesbispolserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.util.Optional;
import java.util.Set;

@Service()
public
class UserQueryService {
    private final UserQueryRepository userQueryRepository;
    private final LineQueryService lineQueryService;

    @Autowired()
    UserQueryService(final UserQueryRepository userQueryRepository, LineQueryService lineQueryService) {
        this.userQueryRepository = userQueryRepository;
        this.lineQueryService = lineQueryService;
    }


    public Set<UserQueryDto> findAll() {
        return userQueryRepository.findBy();
    }

    public Optional<UserQueryDto> findByRfidId(final String rfidId) {
        return userQueryRepository.findByRfidId(rfidId);
    }

    public Optional<UserQueryDto> findById(final long userId) {
        return userQueryRepository.findById(userId);
    }

    UserQueryDto findByLineId(long lineId) {
        Line line = lineQueryService.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        return userQueryRepository.findByRfidId(line.getOperatorRfid())
                .orElseThrow(() -> new NotFoundException(Statements.USER_NOT_EXISTS));
    }
}
