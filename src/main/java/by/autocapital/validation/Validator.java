package by.autocapital.validation;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import by.autocapital.models.BotMessage;

public class Validator {
    public static ValidationResult validateDate(String date) {
        if (date == null || date.isEmpty() || date.isBlank()) {
            return new ValidationResult("Дата не может быть пуста", false);
        }
        if (!date.matches("^(0?[0][1-9]|[12][0-9]|3[01]).(0?[0][1-9]|1[0-2]).([0-9]{4}) ([0-5][0-9]):([0-5][0-9])+$")) {
            return new ValidationResult("Дата введена в не верном формате", false);
        }
        return new ValidationResult(null, true);
    }

    public static ValidationResult validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.isBlank()) {
            return new ValidationResult("Номер телефона не может быть пустым", false);
        }
        if (!phoneNumber.matches("^[3]{1}[7]{1}[5]{1}[0-9]{2}[0-9]{7}+$")) {
            return new ValidationResult("Мобильный телефон введен в неверном формате", false);
        }
        return new ValidationResult(null, true);
    }

    public static ValidationResult validateNumberOfMessage(String number) {
        if (number == null || number.isEmpty() || number.isBlank()) {
            return new ValidationResult("Номер сообщения не может быть пустым", false);
        }
        if (!number.matches("^[0-9]+$")) {
            return new ValidationResult("Номер сообщения может состоять только из цифр", false);
        }
        return new ValidationResult(null, true);
    }

    public static ValidationResult validateNumberOfButton(String number) {
        if (number == null || number.isEmpty() || number.isBlank()) {
            return new ValidationResult("Номер кнопки не может быть пустым", false);
        }
        if (!number.matches("^[0-9]+$")) {
            return new ValidationResult("Номер кнопки может состоять только из цифр", false);
        }
        return new ValidationResult(null, true);
    }

    public static ValidationResult validateYears(String years) {
        if (years == null || years.isEmpty() || years.isBlank()) {
            return new ValidationResult("Дата не может быть пуста", false);
        }
        if (!years.matches("^[0-9]{4}-[0-9]{4}+$")) {
            return new ValidationResult("Дата введена в неверном формате", false);
        }

        String[] splittedYears = years.split("-");

        Integer yearFrom = Integer.parseInt(splittedYears[0]);
        Integer yearTo = Integer.parseInt(splittedYears[1]);
        Integer currentYear = LocalDate.now().getYear();

        if (yearFrom > currentYear || yearTo > currentYear) {
            return new ValidationResult("Год не может быть больше " + currentYear, false);
        }
        if (yearTo < yearFrom) {
            return new ValidationResult("Первый год не может быть больше второго", false);
        }
        if (yearFrom < 1975) {
            return new ValidationResult("Год не может быть меньше 1975", false);
        }

        return new ValidationResult(null, true);
    }

    public static ValidationResult validateBotMessageSequence(String botMessageSequence) {
        if (botMessageSequence == null || botMessageSequence.isEmpty() || botMessageSequence.isBlank()) {
            return new ValidationResult("Последовательность сообщений бота не может быть пуста", false);
        }
        if (!botMessageSequence.matches("^([0-9]{0,},){0,}[0-9]{0,}$")) {
            return new ValidationResult("Последовательность сообщений бота введена в неверном формате", false);
        }
        return new ValidationResult(null, true);
    }

    public static ValidationResult validateBotMessageSequence(List<Integer> order, List<BotMessage> oldBotMessageOrder) {
        if (order.size() != oldBotMessageOrder.size() || order.stream().distinct().count() != oldBotMessageOrder.size()) {
            return new ValidationResult("Количество номеров в последовательности не равно количеству сообщений!", false);
        }
        if (Collections.max(order) != oldBotMessageOrder.size() || Collections.min(order) != 1) {
            return new ValidationResult("Последовательность введена в неверном формате!", false);
        }
        return new ValidationResult(null, true);
    }
}
