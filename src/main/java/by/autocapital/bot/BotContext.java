package by.autocapital.bot;

import by.autocapital.models.User;
import by.autocapital.models.viber.Message;
import by.autocapital.services.viber.ViberService;
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
