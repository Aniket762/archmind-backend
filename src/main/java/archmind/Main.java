package archmind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setBannerMode(Banner.Mode.OFF);
        ConfigurableApplicationContext context = app.run(args);
        Environment env = context.getEnvironment();
        String activeProfile = env.getActiveProfiles().length > 0
                ? env.getActiveProfiles()[0]
                : "default";
        String serverPort = env.getProperty("local.server.port",
                env.getProperty("server.port", "8080"));
        logger.info("Application started successfully on profile: {}", activeProfile);
        logger.info("Application ready to serve on port: {}", serverPort);
    }
}
