package by.testbot.payload.requests.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import by.testbot.models.RichMediaKeyboard;
import by.testbot.models.Sender;
import by.testbot.models.enums.MessageType;
import lombok.Data;

@Data
public class SendRichMediaMessageRequest {
    @JsonProperty("receiver")
    private String viberId;

    @JsonProperty("min_api_version")
    private final Integer minApiVersion = 7;

    @JsonProperty("type")
    private final MessageType messageType = MessageType.RICH_MEDIA;

    @JsonProperty("alt_text")
    private String altText;

    @JsonProperty("sender")
    private Sender sender;

    @JsonProperty("tracking_data")
    private String trackingData;

    @JsonProperty("rich_media")
    private RichMediaKeyboard richMediaKeyboard;
}
