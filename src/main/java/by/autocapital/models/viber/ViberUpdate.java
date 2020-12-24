package by.autocapital.models.viber;

import java.util.Objects;

import by.autocapital.payload.callbacks.ConversationStartedCallback;
import by.autocapital.payload.callbacks.DeliveredCallback;
import by.autocapital.payload.callbacks.FailedCallback;
import by.autocapital.payload.callbacks.MessageCallback;
import by.autocapital.payload.callbacks.SeenCallback;
import by.autocapital.payload.callbacks.SubscribedCallback;
import by.autocapital.payload.callbacks.UnsubscribedCallback;
import by.autocapital.payload.callbacks.WebhookCallback;
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
