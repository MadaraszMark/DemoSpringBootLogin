package app.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoLoginSbApplication {

    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext context) {
        DemoLoginSbApplication.context = context;
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
