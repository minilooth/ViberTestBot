package by.autocapital.payload.callbacks;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.enums.EventType;
import lombok.Data;

@Data
public class UnsubscribedCallback {
    @JsonProperty("event")
    private EventType eventType;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("message_token")
    private Long messageToken;

    @JsonProperty("chat_hostname")
    private String chatHostname;
}
