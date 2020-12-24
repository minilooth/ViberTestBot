package by.autocapital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfig {
    @Autowired
    private Environment environment;

    public Boolean getEnabled() {
        return Boolean.parseBoolean(environment.getProperty("bot.enabled"));
    }

    public String getCodeWord() {
        return environment.getProperty("bot.codeWord");
    }
}
