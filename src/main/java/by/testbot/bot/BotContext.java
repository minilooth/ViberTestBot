package by.testbot.bot;

import by.testbot.payload.callbacks.ConversationStartedCallback;
import by.testbot.payload.callbacks.DeliveredCallback;
import by.testbot.payload.callbacks.FailedCallback;
import by.testbot.payload.callbacks.MessageCallback;
import by.testbot.payload.callbacks.SeenCallback;
import by.testbot.payload.callbacks.SubscribedCallback;
import by.testbot.payload.callbacks.UnsubscribedCallback;
import by.testbot.payload.callbacks.WebhookCallback;
import by.testbot.services.MessageService;
import by.testbot.services.UserService;
import by.testbot.services.ViberService;
import by.testbot.services.KeyboardService;
import lombok.Getter;

@Getter
public class BotContext {
    private final ViberService viberService;
    private final MessageService messageService;
    private final KeyboardService keyboardService;
    private final UserService userService;
    private final ConversationStartedCallback conversationStartedCallback;
    private final DeliveredCallback deliveredCallback;
    private final FailedCallback failedCallback;
    private final MessageCallback messageCallback;
    private final SeenCallback seenCallback;
    private final SubscribedCallback subscribedCallback;
    private final UnsubscribedCallback unsubscribedCallback;
    private final WebhookCallback webhookCallback;

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, ConversationStartedCallback conversationStartedCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = conversationStartedCallback;
        this.deliveredCallback = null;
        this.failedCallback = null;
        this.messageCallback = null;
        this.seenCallback = null;
        this.subscribedCallback = null;
        this.unsubscribedCallback = null;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, DeliveredCallback deliveredCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = deliveredCallback;
        this.failedCallback = null;
        this.messageCallback = null;
        this.seenCallback = null;
        this.subscribedCallback = null;
        this.unsubscribedCallback = null;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, FailedCallback failedCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = null;
        this.failedCallback = failedCallback;
        this.messageCallback = null;
        this.seenCallback = null;
        this.subscribedCallback = null;
        this.unsubscribedCallback = null;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, MessageCallback messageCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = null;
        this.failedCallback = null;
        this.messageCallback = messageCallback;
        this.seenCallback = null;
        this.subscribedCallback = null;
        this.unsubscribedCallback = null;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, SeenCallback seenCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = null;
        this.failedCallback = null;
        this.messageCallback = null;
        this.seenCallback = seenCallback;
        this.subscribedCallback = null;
        this.unsubscribedCallback = null;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, SubscribedCallback subscribedCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = null;
        this.failedCallback = null;
        this.messageCallback = null;
        this.seenCallback = null;
        this.subscribedCallback = subscribedCallback;
        this.unsubscribedCallback = null;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, UnsubscribedCallback unsubscribedCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = null;
        this.failedCallback = null;
        this.messageCallback = null;
        this.seenCallback = null;
        this.subscribedCallback = null;
        this.unsubscribedCallback = unsubscribedCallback;
        this.webhookCallback = null;
    }

    private BotContext(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, WebhookCallback webhookCallback) {
        this.viberService = viberService;
        this.messageService = messageService;
        this.keyboardService = keyboardService;
        this.userService = userService;
        this.conversationStartedCallback = null;
        this.deliveredCallback = null;
        this.failedCallback = null;
        this.messageCallback = null;
        this.seenCallback = null;
        this.subscribedCallback = null;
        this.unsubscribedCallback = null;
        this.webhookCallback = webhookCallback;
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, ConversationStartedCallback conversationStartedCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, conversationStartedCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, DeliveredCallback deliveredCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, deliveredCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, FailedCallback failedCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, failedCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, MessageCallback messageCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, messageCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, SeenCallback seenCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, seenCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, SubscribedCallback subscribedCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, subscribedCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, UnsubscribedCallback unsubscribedCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, unsubscribedCallback);
    }

    public static BotContext of(ViberService viberService, MessageService messageService, KeyboardService keyboardService, UserService userService, WebhookCallback webhookCallback) {
        return new BotContext(viberService, messageService, keyboardService, userService, webhookCallback);
    }
}
