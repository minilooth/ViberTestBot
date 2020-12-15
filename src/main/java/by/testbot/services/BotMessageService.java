package by.testbot.services;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private DialogueService dialogueService;

    final static String NAME_PLACEHOLDER = "&{name}";
    final static String LINK_PLACEHOLDER = "&{link}";

    @Transactional
    public void save(BotMessage botMessage) {
        botMessageRepository.save(botMessage);
    }

    @Transactional
    public void saveAll(Iterable<BotMessage> botMessages) {
        botMessageRepository.saveAll(botMessages);
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
    public List<BotMessage> getAllAdded() {
        BotMessage botMessage = getFirstMessage();
        List<BotMessage> botMessages = new ArrayList<>();

        while(botMessage != null) {
            botMessages.add(botMessage);
            botMessage = botMessage.getNextMessage();
        }

        return botMessages;
    }

    @Transactional
    public BotMessage getFirstMessage() {
        return botMessageRepository.findFirstByPreviousMessageAndIsAdded(null, false);
    }

    @Transactional
    public BotMessage getLastMessage() {
        return botMessageRepository.findFirstByNextMessageAndIsAdded(null, false);
    }

    @Transactional
    public BotMessage getById(Integer id) {
        return botMessageRepository.findById(id).orElse(null);
    }

    public Long getLastUpdate() {
        BotMessage firstMessage = getFirstMessage();

        if (firstMessage == null) {
            return null;
        }
        else {
            return firstMessage.getLastUpdate();
        }
    }

    public List<Integer> parseBotMessageSequence(String botMessageSequence) {
        List<Integer> sequence = new ArrayList<>();

        String[] tokens = botMessageSequence.split(",");

        for (String token : tokens) {
            sequence.add(Integer.parseInt(token));
        }

        return sequence;
    }

    public void changeBotMessagesOrder(List<Integer> order, List<BotMessage> oldBotMessageSequence) {
        List<BotMessage> newBotMessageOrder = new ArrayList<>();
        Long timestamp = new Date().getTime();

        order.forEach(p -> newBotMessageOrder.add(oldBotMessageSequence.get(p - 1)));

        for (BotMessage botMessage : newBotMessageOrder) {
            if (newBotMessageOrder.indexOf(botMessage) == 0) {
                botMessage.setPreviousMessage(null);
            }
            else {
                botMessage.setPreviousMessage(newBotMessageOrder.get(newBotMessageOrder.indexOf(botMessage) - 1));
            }

            if (newBotMessageOrder.indexOf(botMessage) < newBotMessageOrder.size() - 1) {
                botMessage.setNextMessage(newBotMessageOrder.get(newBotMessageOrder.indexOf(botMessage) + 1));
            }
            else {
                botMessage.setNextMessage(null);
            }
            botMessage.setLastUpdate(timestamp);
        }

        List<Dialogue> notEndedDialogues = dialogueService.getAllNotEndedAndBotMessageNotNullDialogues();

        dialogueService.deleteAll(notEndedDialogues);

        saveAll(newBotMessageOrder);
    }

    public String getPrettyFormatedAllBotMessages() {
        BotMessage botMessage = getFirstMessage();
        Integer counter = 1;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Список сообщений, которые бот отправляет:\n");

        while(botMessage != null) {
            String message = botMessage.getMessage().length() > 50 ? botMessage.getMessage().substring(0, 50) + "..." : botMessage.getMessage();
            
            if (botMessage.getNextMessage() != null) {
                stringBuilder.append(String.format("%d - %s\n", counter, message));
            }
            else {
                stringBuilder.append(String.format("%d - %s", counter, message));
            }

            botMessage = botMessage.getNextMessage();
            counter++;
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

    public String formateBotMessage(BotMessage botMessage, Client client) {
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