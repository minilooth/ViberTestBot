package by.testbot.services.other;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class ConfigService {
    public final static Logger logger = LoggerFactory.getLogger(ConfigService.class);
    public final static String CONFIG_FOLDER_PATH = "./config/";
    public final static String BOT_CONFIG_PATH = CONFIG_FOLDER_PATH + "bot.properties";

    @SneakyThrows
    public void checkConfig() {
        Map<String, String> properties = new HashMap<>();
        Scanner scanner = new Scanner(new File(BOT_CONFIG_PATH));

        while(scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("=");

            if (tokens.length < 2) {
                throw new IllegalArgumentException("Config file " + BOT_CONFIG_PATH + " have invalid format");
            }

            properties.put(tokens[0], tokens[1] == null ? StringUtils.EMPTY : tokens[1]);
        }

        if (!properties.containsKey("bot.enabled")) {
            throw new IllegalArgumentException("Missing bot property bot.enabled from config: " + BOT_CONFIG_PATH);
        }
        if (!properties.containsKey("bot.codeWord")) {
            throw new IllegalArgumentException("Missing bot property bot.codeWord from config: " + BOT_CONFIG_PATH);
        }
    }

    @SneakyThrows
    public void changeConfigProperty(String property, String value) {
        Map<String, String> properties = new HashMap<>();
        Scanner scanner = new Scanner(new File(BOT_CONFIG_PATH));

        while(scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("=");

            if (tokens.length < 2) {
                throw new IllegalArgumentException("Config file " + BOT_CONFIG_PATH + " have invalid format");
            }

            properties.put(tokens[0], tokens[1] == null ? StringUtils.EMPTY : tokens[1]);
        }

        if (!properties.containsKey(property)) {
            throw new IllegalArgumentException("Property " + property + " not found in config file: " + BOT_CONFIG_PATH);
        }

        properties.replace(property, value);

        FileWriter fileWriter = new FileWriter(BOT_CONFIG_PATH);

        for (String newProperty : properties.keySet()) {
            fileWriter.append(newProperty + "=" + properties.get(newProperty) + "\n");
        }

        fileWriter.close();

        logger.info("Property " + property + " value changed to " + value);
    }
}
