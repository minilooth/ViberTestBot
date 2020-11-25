package by.testbot.utils;

import java.util.ArrayList;
import java.util.List;

import by.testbot.models.enums.EventType;

public class Utils {
    public static List<EventType> getAllEventTypes() {
        List<EventType> eventTypes = new ArrayList<>();

        // eventTypes.add(EventType.DELIVERED);
        // eventTypes.add(EventType.SEEN);
        eventTypes.add(EventType.FAILED);
        eventTypes.add(EventType.SUBSCRIBED);
        eventTypes.add(EventType.UNSUBSCRIBED);
        eventTypes.add(EventType.CONVERSATION_STARTED);

        return eventTypes;
    }
}
