package by.testbot.models;

import java.util.Objects;

import by.testbot.payload.callbacks.ConversationStartedCallback;
import by.testbot.payload.callbacks.DeliveredCallback;
import by.testbot.payload.callbacks.FailedCallback;
import by.testbot.payload.callbacks.MessageCallback;
import by.testbot.payload.callbacks.SeenCallback;
import by.testbot.payload.callbacks.SubscribedCallback;
import by.testbot.payload.callbacks.UnsubscribedCallback;
import by.testbot.payload.callbacks.WebhookCallback;
import lombok.Data;

@Data
public class ViberUpdate {
    private ConversationStartedCallback conversationStartedCallback;
    private DeliveredCallback deliveredCallback;
    private FailedCallback failedCallback;
    private MessageCallback messageCallback;
    private SeenCallback seenCallback;
    private SubscribedCallback subscribedCallback;
    private UnsubscribedCallback unsubscribedCallback;
    private WebhookCallback webhookCallback;

    public Boolean hasConversationStartedCallback() {
        return !Objects.isNull(this.conversationStartedCallback);
    }

    public Boolean hasDeliveredCallback() {
        return !Objects.isNull(this.deliveredCallback);
    }

    public Boolean hasFailedCallback() {
        return !Objects.isNull(this.failedCallback);
    }

    public Boolean hasMessageCallback() {
        return !Objects.isNull(this.messageCallback);
    }

    public Boolean hasSeenCallback() {
        return !Objects.isNull(this.seenCallback);
    }

    public Boolean hasSubscribedCallback() {
        return !Objects.isNull(this.subscribedCallback);
    }

    public Boolean hasUnsubscribedCallback() {
        return !Objects.isNull(this.unsubscribedCallback);
    }

    public Boolean hasWebhookCallback() {
        return !Objects.isNull(this.unsubscribedCallback);
    }
}
