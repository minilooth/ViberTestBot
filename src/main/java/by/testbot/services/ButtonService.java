package by.testbot.services;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.BotMessage;
import by.testbot.models.Button;
import by.testbot.models.enums.AnswerType;
import by.testbot.repositories.ButtonRepository;

@Service
public class ButtonService {
    @Autowired
    private ButtonRepository buttonRepository;

    public final static Integer MAX_BUTTONS_COUNT = 24;
    public final static Integer CALLBACK_LENGTH = 6;
    public final static String CALLBACK_PREFIX = "callback_";

    @Transactional
    public void save(Button button) {
        buttonRepository.save(button);
    }

    @Transactional
    public void saveAll(Iterable<Button> buttons) {
        buttonRepository.saveAll(buttons);
    }

    @Transactional
    public void delete(Button button) {
        buttonRepository.delete(button);
    }

    @Transactional
    public Button getByCallbackData(String callbackData) {
        return buttonRepository.findFirstByCallbackData(callbackData);
    }

    @Transactional
    public List<Button> getAllByBotMessage(BotMessage botMessage) {
        return buttonRepository.findAllByBotMessage(botMessage);
    }

    @Transactional
    public void deleteAll(Iterable<Button> buttons) {
        buttonRepository.deleteAll(buttons);
    }

    @Transactional
    public List<Button> getAll() {
        return buttonRepository.findAll();
    }

    public String generateCallbackData() {
        String callbackData;

        do {
            callbackData = CALLBACK_PREFIX + RandomStringUtils.randomAlphanumeric(CALLBACK_LENGTH); 
        }
        while(getByCallbackData(callbackData) != null);

        return callbackData;
    }

    public String formatButtons(List<Button> buttons) {
        if (buttons.isEmpty()) {
            return "Список кнопок пуст";
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (Button button : buttons) {
            if (buttons.indexOf(button) != buttons.size() - 1) {
                stringBuilder.append(String.format("%d - %s(%s, %s)\n", buttons.indexOf(button) + 1, button.getText(), "Тип ответа: " + (button.getAnswerType() == AnswerType.NEGATIVE ? "Отрицательный" : "Положительный"), "Заканчивает диалог: " + (button.getDialogueEnds() ? "Да" : "Нет" )));
            }
            else {
                stringBuilder.append(String.format("%d - %s(%s, %s)", buttons.indexOf(button) + 1, button.getText(), "Тип ответа: " + (button.getAnswerType() == AnswerType.NEGATIVE ? "Отрицательный" : "Положительный"), "Заканчивает диалог: " + (button.getDialogueEnds() ? "Да" : "Нет" )));
            }
        }

        return stringBuilder.toString();
    }
}
