package by.autocapital.payload.callbacks;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.User;
import by.autocapital.models.enums.EventType;
import lombok.Data;

@Data
public class ConversationStartedCallback {
    @JsonProperty("event")
    private EventType eventType;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("message_token")
    private Long messageToken;

    @JsonProperty("type")
    private String type;

    @JsonProperty("context")
    private String context;

    @JsonProperty("user")
    private User user;

    @JsonProperty("subscribed")
    private Boolean subscribed;

    @JsonProperty("chat_hostname")
    private String chatHostname;
}
