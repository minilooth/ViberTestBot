package by.testbot.bot;

import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.testbot.services.MessageService;

@Component
public class BotStateInjector {
    @Autowired
    private MessageService messageService;

    @PostConstruct
    public void postConstruct() {
        for (BotState botState : EnumSet.allOf(BotState.class))
            botState.setMessageService(messageService);
    }
}
