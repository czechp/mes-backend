package pl.bispol.mesbispolserver.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.breakdown.Breakdown;
import pl.bispol.mesbispolserver.breakdown.BreakdownFactory;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownEmailMsgDto;
import pl.bispol.mesbispolserver.quality_control.dto.QualityControlEmailMsgDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HttpClientServiceImpl implements HttpClientService {
    @Value("${email.service.credentials}")
    private String emailServiceCredentials;

    @Value("${email.service.address}")
    private String emailServiceAddress;

    @Value("${plc.counter.synchronise.address}")
    private String plcCounterSynchroniseAddress;

    private HttpClient httpClient;

    private ObjectMapper objectMapper;

    private Logger logger;

    HttpClientServiceImpl() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.httpClient = HttpClient
                .newBuilder()
                .executor(executorService)
                .build();

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void qualityControlSendEmail(QualityControlEmailMsgDto qualityControlEmailMsgDto) throws URISyntaxException, IOException {
        String body = objectMapper.writeValueAsString(qualityControlEmailMsgDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(emailServiceAddress + "/api/quality-controls/send-emails"))
                .header("Authorization", emailServiceCredentials)
                .setHeader("User-Agent", "MES backend system")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(r -> logger.info("Http response - email service response status code: " + r.statusCode()));
    }

    @Override
    public void resetLineCounters() throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(plcCounterSynchroniseAddress + "/api/plcs/reset"))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(r -> logger.info("Http response - plc synchronise service response status code: " + r.statusCode()));
    }

    @Override
    public void breakdownSendEmails(Breakdown breakdown) {
        try {
            breakdownBasicSendEmails(breakdown, "/api/breakdowns/send-emails");
        } catch (Exception e) {
            logger.info("Error during sending email about breakdown");
        }
    }

    @Override
    public void longBreakdownSendEmails(Breakdown breakdown) {
        try {
            breakdownBasicSendEmails(breakdown, "/api/breakdowns/send-emails-management");
        } catch (Exception e) {
            logger.info("Error during sending email about breakdown");
        }
    }

    private void breakdownBasicSendEmails(Breakdown breakdown, String endpoint) throws IOException, URISyntaxException {
        BreakdownEmailMsgDto breakdownEmailMsgDto = BreakdownFactory.toEmailDto(breakdown);

        String body = objectMapper.writeValueAsString(breakdownEmailMsgDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(emailServiceAddress + endpoint))
                .header("Authorization", emailServiceCredentials)
                .setHeader("User-Agent", "MES backend system")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(r -> logger.info("Http response - email service response status code: " + r.statusCode()));

    }
}
