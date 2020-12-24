package by.autocapital.models.viber;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.enums.Status;
import lombok.Data;

@Data
public class Failed {
    @JsonProperty("receiver")
    private String userId;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("status_message")
    private String statusMessage;
}
