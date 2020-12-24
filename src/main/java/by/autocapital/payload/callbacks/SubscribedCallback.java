package by.autocapital.payload.callbacks;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.User;
import by.autocapital.models.enums.EventType;
import lombok.Data;

@Data
public class SubscribedCallback {
    @JsonProperty("event")
    private EventType eventType;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("user")
    private User user;

    @JsonProperty("message_token")
    private Long messageToken;

    @JsonProperty("chat_hostname")
    private String chatHostname;
}
