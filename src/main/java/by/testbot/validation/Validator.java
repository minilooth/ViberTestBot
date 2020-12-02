package by.testbot.validation;

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
}
