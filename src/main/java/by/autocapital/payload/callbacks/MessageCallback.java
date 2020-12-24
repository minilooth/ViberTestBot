package by.autocapital.payload.callbacks;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.enums.EventType;
import by.autocapital.models.viber.Message;
import by.autocapital.models.viber.Sender;
import lombok.Data;

@Data
public class MessageCallback {
    @JsonProperty("event")
    private EventType eventType;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("message_token")
    private Long messageToken;

    @JsonProperty("sender")
    private Sender sender;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("chat_hostname")
    private String chatHostname;

    @JsonProperty("silent")
    private Boolean silent;
}
