package by.testbot.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {
    private String message;
    private Boolean isValid;
}
