package by.autocapital.models.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventType {
    @JsonProperty("delivered")
    DELIVERED("delivered"),

    @JsonProperty("seen")
    SEEN("seen"),

    @JsonProperty("failed")
    FAILED("failed"),

    @JsonProperty("subscribed")
    SUBSCRIBED("subscribed"),

    @JsonProperty("unsubscribed")
    UNSUBSCRIBED("unsubscribed"),

    @JsonProperty("conversation_started")
    CONVERSATION_STARTED("conversation_started"),

    @JsonProperty("webhook")
    WEBHOOK("webhook"),

    @JsonProperty("message")
    MESSAGE("message");

    private String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EventType fromString(String eventType) {
        return Arrays.stream(EventType.values())
            .filter(type -> type.name.equals(eventType))
            .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
