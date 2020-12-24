package by.testbot.config;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.core.convert.Property;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import by.testbot.services.other.ConfigService;
import lombok.SneakyThrows;

public class ReloadablePropertySource extends PropertySource<Property> {
    PropertiesConfiguration propertiesConfiguration;

    @SneakyThrows
    public ReloadablePropertySource(String name, PropertiesConfiguration propertiesConfiguration) {
        super(name);

        this.createConfigDirectory();
        this.createBotConfig();
        
        this.propertiesConfiguration = propertiesConfiguration;
    }

    @SneakyThrows
    public ReloadablePropertySource(String name, String path) {
        super(StringUtils.isEmpty(name) ? path : name);

        this.createConfigDirectory();
        this.createBotConfig();

        this.propertiesConfiguration = new PropertiesConfiguration(path);
        this.propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    @Override
    public Object getProperty(String s) {
        return propertiesConfiguration.getProperty(s);
    }

    @SneakyThrows
    private void createConfigDirectory() {
        if (Files.notExists(Path.of(ConfigService.CONFIG_FOLDER_PATH), LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(Path.of(ConfigService.CONFIG_FOLDER_PATH));
            
            logger.info("Created config directory: " + ConfigService.CONFIG_FOLDER_PATH);
        }
    }

    @SneakyThrows
    private void createBotConfig() {
        if (Files.notExists(Path.of(ConfigService.BOT_CONFIG_PATH), LinkOption.NOFOLLOW_LINKS)) {
            Files.createFile(Path.of(ConfigService.BOT_CONFIG_PATH));

            FileWriter fileWriter = new FileWriter(ConfigService.BOT_CONFIG_PATH);

            fileWriter.append("bot.enabled=true\n");
            fileWriter.append("bot.codeWord=MANAGER\n");
            fileWriter.close();

            logger.info("Created config file " + ConfigService.BOT_CONFIG_PATH);
        }
    }
}
