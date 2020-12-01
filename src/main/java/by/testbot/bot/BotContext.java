package by.testbot.bot;

import by.testbot.models.Message;
import by.testbot.models.User;
import by.testbot.services.MessageService;
import by.testbot.services.PostponeMessageService;
import by.testbot.services.UserService;
import by.testbot.services.ViberService;
import by.testbot.services.ClientChatMessageHistoryService;
import by.testbot.services.KeyboardService;
import lombok.Getter;

@Getter
public class BotContext {
    private final User user;
    private final ViberService viberService;
    private final MessageService messageService;
    private final KeyboardService keyboardService;
    private final UserService userService;
    private final ClientChatMessageHistoryService clientChatMessageHistoryService;
    private final PostponeMessageService postponeMessageService;
    private final Message message;

    private BotContext(User user, Message message, ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, ClientChatMessageHistoryService clientChatMessageHistoryService, PostponeMessageService postponeMessageService) {
        this.user = user;
        this.message = message;
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.clientChatMessageHistoryService = clientChatMessageHistoryService;
        this.postponeMessageService = postponeMessageService;
    }

    public static BotContext of(User user, Message message, ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, ClientChatMessageHistoryService clientChatMessageHistoryService, PostponeMessageService postponeMessageService) {
        return new BotContext(user, message, viberService, messageService, keyboardService, userService, clientChatMessageHistoryService, postponeMessageService);
    }
}
