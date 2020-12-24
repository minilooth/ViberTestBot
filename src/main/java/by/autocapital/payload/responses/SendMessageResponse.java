package by.autocapital.payload.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.autocapital.models.enums.Status;
import by.autocapital.models.viber.Failed;
import lombok.Data;

@Data
public class SendMessageResponse {
    @JsonProperty("status")
    private Status status;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("message_token")
    private Long messageToken;

    @JsonProperty("chat_hostname")
    private String chatHostname;

    @JsonProperty("failed_list")
    private List<Failed> failedList;
}
