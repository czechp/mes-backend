package pl.bispol.mesbispolserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MesBackendApplication.class, args);
    }

}
