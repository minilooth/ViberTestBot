package by.autocapital.payload.requests.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import by.autocapital.models.enums.MessageType;
import by.autocapital.models.viber.Keyboard;
import by.autocapital.models.viber.Location;
import by.autocapital.models.viber.Sender;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SendLocationMessageRequest {
    @JsonProperty("receiver")
    private String userId;

    @JsonProperty("min_api_version")
    private Integer minApiVersion;

    @JsonProperty("sender")
    private Sender sender;

    @JsonProperty("tracking_data")
    private String trackingData;

    @JsonProperty(value = "type", required = true)
    private final MessageType messageType = MessageType.LOCATION;

    @JsonProperty(value = "location", required = true)
    private Location location;

    @JsonProperty("keyboard")
    private Keyboard keyboard;

    @JsonProperty("broadcast_list")
    private List<String> broadcastList;
}