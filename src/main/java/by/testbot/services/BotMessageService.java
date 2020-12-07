package by.testbot.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.BotMessage;
import by.testbot.models.Client;
import by.testbot.models.Dialogue;
import by.testbot.repositories.BotMessageRepository;

@Service
public class BotMessageService {
    @Autowired
    private BotMessageRepository botMessageRepository;

    @Autowired
    private CarService carService;

    final static String NAME_PLACEHOLDER = "&{name}";
    final static String LINK_PLACEHOLDER = "&{link}";

    @Transactional
    public void save(BotMessage botMessage) {
        botMessageRepository.save(botMessage);
    }

    @Transactional
    public void delete(BotMessage botMessage) {
        botMessageRepository.delete(botMessage);
    }

    @Transactional
    public List<BotMessage> getAll() {
        return botMessageRepository.findAll();
    }

    @Transactional
    public BotMessage getById(Integer id) {
        return botMessageRepository.findById(id).orElse(null);
    }

    public String getPrettyFormatedAllBotMessages() {
        List<BotMessage> botMessages = getAll();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Список сообщений, которые бот отправляет:\n");

        for (BotMessage botMessage : botMessages) {
            String message = botMessage.getMessage().length() > 50 ? botMessage.getMessage().substring(0, 50) + "..." : botMessage.getMessage();
            
            stringBuilder.append(String.format("%d - %s\n", botMessage.getId(), message));
        }

        return stringBuilder.toString();
    }

    public String formatBotMessage(Integer id, Client client) {
        BotMessage botMessage = getById(id);

        if (botMessage == null) {
            return StringUtils.EMPTY;
        }

        String name = StringUtils.EMPTY;
        String link = StringUtils.EMPTY;

        if (hasName(botMessage.getMessage())) {
            name = client.getName();
        }
        if (hasLink(botMessage.getMessage())) {
            Dialogue currentDialogue = client.getDialogues().stream().filter(d -> !d.getDialogIsOver()).findAny().orElse(null);
            
            if (currentDialogue != null) {
                link = carService.generateLink(currentDialogue.getBrand(), currentDialogue.getModel(), currentDialogue.getYearFrom(), currentDialogue.getYearTo());
            }
        }

        return botMessage.getMessage().replace(NAME_PLACEHOLDER, name).replace(LINK_PLACEHOLDER, link);
    }

    private Boolean hasName(String message) {
        return message.contains(NAME_PLACEHOLDER);
    }

    private Boolean hasLink(String message) {
        return message.contains(LINK_PLACEHOLDER);
    }
}
