package by.autocapital.bot.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import by.autocapital.bot.KeyboardSource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class BotConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();

        reloadableResourceBundleMessageSource.setBasename("classpath:messages");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");

        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public KeyboardSource keyboardSource() {
        return new KeyboardSource();
    }
}
