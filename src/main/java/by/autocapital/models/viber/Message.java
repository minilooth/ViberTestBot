package by.autocapital.models.viber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import by.autocapital.models.enums.MessageType;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Message {
    @JsonProperty("type")
    private MessageType messageType;

    @JsonProperty("text")
    private String text;

    @JsonProperty("media")
    private String media;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("contact")
    private Contact contact;

    @JsonProperty("tracking_data")
    private String trackingData;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_size")
    private Long fileSize;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("sticker_id")
    private Integer stickerId;

    @JsonProperty("thumbnail")
    private String thumbnailUrl;

    @JsonProperty("size")
    private Long size;

    public Boolean hasContact() {
        return messageType == MessageType.CONTACT;
    }

    public Boolean hasText() {
        return messageType == MessageType.TEXT;
    }

    public Boolean hasLocation() {
        return messageType == MessageType.CONTACT;
    }

    public Boolean hasPicture() {
        return messageType == MessageType.PICTURE;
    }

    public Boolean hasVideo() {
        return messageType == MessageType.VIDEO;
    }

    public Boolean hasFile() {
        return messageType == MessageType.FILE;
    }

    public Boolean hasSticker() {
        return messageType == MessageType.STICKER;
    }

    public Boolean hasUrl() {
        return messageType == MessageType.URL;
    }
}
