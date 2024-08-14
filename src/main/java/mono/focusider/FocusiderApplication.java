package mono.focusider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FocusiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FocusiderApplication.class, args);
    }

}
