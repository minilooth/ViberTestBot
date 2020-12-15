package by.testbot.utils;

import java.time.LocalDate;
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

    public static List<String> generateYears(Integer startYear) {
        List<String> years = new ArrayList<>();
        Integer currentYear = LocalDate.now().getYear();

        for (Integer i = startYear; i <= currentYear; i++) {
            years.add(i.toString());
        }

        return years;
    }
}
