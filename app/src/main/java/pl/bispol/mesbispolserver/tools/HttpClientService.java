package pl.bispol.mesbispolserver.tools;

import pl.bispol.mesbispolserver.breakdown.Breakdown;
import pl.bispol.mesbispolserver.quality_control.dto.QualityControlEmailMsgDto;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpClientService {
    void qualityControlSendEmail(QualityControlEmailMsgDto qualityControlEmailMsgDto) throws URISyntaxException, IOException;

    void resetLineCounters() throws URISyntaxException;

    void breakdownSendEmails(Breakdown breakdown);

    void longBreakdownSendEmails(Breakdown breakdown);
}
