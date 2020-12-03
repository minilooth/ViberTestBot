package by.testbot.bot;

import by.testbot.models.viber.Message;
import by.testbot.models.User;
import by.testbot.services.ViberService;
import lombok.Getter;

@Getter
public class BotContext {
    private final User user;
    private final Message message;
    private final ViberService viberService;

    private BotContext(User user, Message message, ViberService viberService) {
        this.user = user;
        this.message = message;
        this.viberService = viberService;
    }

    public static BotContext of(User user, Message message, ViberService viberService) {
        return new BotContext(user, message, viberService);
    }
}
