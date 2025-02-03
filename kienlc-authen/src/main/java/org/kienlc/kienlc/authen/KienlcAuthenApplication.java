package org.kienlc.kienlc.authen;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class KienlcAuthenApplication {

    public static void main(String[] args) {
        SpringApplication.run(KienlcAuthenApplication.class, args);
    }

}
