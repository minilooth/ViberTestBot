package by.testbot.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.Button;
import by.testbot.models.Manager;
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
    public void saveAll(Set<Button> buttons) {
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

    public void deleteAll(Set<Button> buttons) {
        for (Button button : buttons) {
            delete(button);
        }
    }

    @Transactional
    public List<Button> getAll() {
        return buttonRepository.findAll();
    }

    public String generateCallbackData() {
        return CALLBACK_PREFIX + RandomStringUtils.randomAlphanumeric(CALLBACK_LENGTH);
    }

    public Set<Button> copyButtons(Set<Button> buttons) {
        Set<Button> buttonsCopy = new HashSet<>();

        for (Button button : buttons) {
            Button buttonCopy = new Button();
            buttonCopy.setText(button.getText());
            buttonCopy.setAnswerType(button.getAnswerType());
            buttonCopy.setDialogueEnds(button.getDialogueEnds());
            buttonCopy.setIsLast(button.getIsLast());
            buttonCopy.setCallbackData(button.getCallbackData());

            buttonsCopy.add(buttonCopy);
        }

        return buttonsCopy;
    }

    public Set<Button> copyButtons(Set<Button> buttons, Manager manager) {
        Set<Button> buttonsCopy = new HashSet<>();

        for (Button button : buttons) {
            Button buttonCopy = new Button();
            buttonCopy.setText(button.getText());
            buttonCopy.setAnswerType(button.getAnswerType());
            buttonCopy.setDialogueEnds(button.getDialogueEnds());
            buttonCopy.setIsLast(button.getIsLast());
            buttonCopy.setCallbackData(button.getCallbackData());

            if (buttonCopy.getManagers() == null) {
                Set<Manager> managers = new HashSet<>();
                managers.add(manager);

                buttonCopy.setManagers(managers);
            }
            else {
                buttonCopy.getManagers().add(manager);
            }

            save(buttonCopy);

            buttonsCopy.add(buttonCopy);
        }

        return buttonsCopy;
    }

    public String formatButtons(Set<Button> buttons) {
        if (buttons.isEmpty()) {
            return "Список кнопок пуст";
        }

        List<Button> buttonsCopy = List.copyOf(buttons);
        StringBuilder stringBuilder = new StringBuilder();

        for (Button button : buttonsCopy) {
            if (buttonsCopy.indexOf(button) != buttonsCopy.size() - 1) {
                stringBuilder.append(String.format("%d - %s(%s, %s)\n", buttonsCopy.indexOf(button) + 1, button.getText(), "Тип ответа: " + (button.getAnswerType() == AnswerType.NEGATIVE ? "Отрицательный" : "Положительный"), "Заканчивает диалог: " + (button.getDialogueEnds() ? "Да" : "Нет" )));
            }
            else {
                stringBuilder.append(String.format("%d - %s(%s, %s)", buttonsCopy.indexOf(button) + 1, button.getText(), "Тип ответа: " + (button.getAnswerType() == AnswerType.NEGATIVE ? "Отрицательный" : "Положительный"), "Заканчивает диалог: " + (button.getDialogueEnds() ? "Да" : "Нет" )));
            }
        }

        return stringBuilder.toString();
    }
}
