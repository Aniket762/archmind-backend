package archmind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
        logger.info("Application started successfully on profile: {}",
                System.getProperty("spring.profiles.active", "default"));
        logger.info("Application ready to serve on port: {}",
                System.getProperty("server.port","8080"));
    }
}
