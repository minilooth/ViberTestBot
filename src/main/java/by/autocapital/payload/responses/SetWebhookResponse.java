package by.autocapital.payload.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.enums.EventType;
import by.autocapital.models.enums.Status;
import lombok.Data;

@Data
public class SetWebhookResponse {
    @JsonProperty("status")
    private Status status;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("event_types")
    private List<EventType> eventTypes;

    @JsonProperty("chat_hostname")
    private String chatHostname;
}
