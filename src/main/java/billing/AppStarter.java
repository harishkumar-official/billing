package billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"billing"})
@EnableScheduling
public class AppStarter extends SpringBootServletInitializer {

  public static void main(String... args) {
    SpringApplication.run(AppStarter.class, args);
  }
}
