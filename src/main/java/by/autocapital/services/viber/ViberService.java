package by.autocapital.services.viber;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import by.autocapital.bot.BotContext;
import by.autocapital.bot.BotState;
import by.autocapital.config.EnvironmentConfig;
import by.autocapital.config.ReloadablePropertySourceFactory;
import by.autocapital.models.Client;
import by.autocapital.models.Manager;
import by.autocapital.models.Statistics;
import by.autocapital.models.User;
import by.autocapital.models.enums.Role;
import by.autocapital.models.enums.Status;
import by.autocapital.models.viber.Failed;
import by.autocapital.models.viber.Message;
import by.autocapital.models.viber.ViberUpdate;
import by.autocapital.payload.requests.SetWebhookRequest;
import by.autocapital.payload.requests.message.*;
import by.autocapital.payload.responses.SendMessageResponse;
import by.autocapital.payload.responses.SetWebhookResponse;
import by.autocapital.proxy.ViberProxy;
import by.autocapital.services.MessageService;
import by.autocapital.services.StatisticsService;
import by.autocapital.services.UserService;
import by.autocapital.services.other.ConfigService;
import by.autocapital.utils.Utils;
import lombok.Getter;

@Service
@PropertySource(value = "file:" + ConfigService.BOT_CONFIG_PATH, factory = ReloadablePropertySourceFactory.class)
@RefreshScope
public class ViberService {
    private static final Logger logger = LoggerFactory.getLogger(ViberService.class);
    
    public static final String FILE_URL = "/file/";
    public static final String PICTURE_URL = "/picture/";
    public static final String KEYBOARD_ICON_URL = "/keyboard_icon/";
    
    @Autowired
    private ViberProxy viberProxy;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private StatisticsService statisticsService;

    @Value("${testbot.authenticationToken}")
    private String authenticationToken;

    @Value("${testbot.webhookUrl}")
    private String webhookUrl;

    @Getter
    @Value("${testbot.sender.name}")
    private String senderName;

    @Getter private String fileEndpoint;
    @Getter private String pictureEndpoint;
    @Getter private String iconEndpoint;

    @PostConstruct
    private void postConstruct() {
        this.fileEndpoint = webhookUrl + FILE_URL;
        this.pictureEndpoint = webhookUrl + PICTURE_URL;
        this.iconEndpoint = webhookUrl + KEYBOARD_ICON_URL;
    }

    public void setWeebhook() {
        if (authenticationToken == null || authenticationToken.isEmpty() || authenticationToken.isBlank()) {
            logger.error("Authentication token has invalid type.");
            return;
        }
        if (webhookUrl == null || webhookUrl.isEmpty() || webhookUrl.isBlank()) {
            logger.error("Webhook url has invalid type.");
        }

        SetWebhookRequest setWebhookRequest = new SetWebhookRequest();

        setWebhookRequest.setUrl(webhookUrl);
        setWebhookRequest.setEventTypes(Utils.getAllEventTypes());
        setWebhookRequest.setSendName(true);
        setWebhookRequest.setSendPhoto(true);

        SetWebhookResponse setWebhookResponse = viberProxy.setWebhook(authenticationToken, setWebhookRequest);

        if (setWebhookResponse.getStatus() == Status.OK) {
            logger.info("Webhook setted with response: " + setWebhookResponse.getStatusMessage() + ", events: " + setWebhookResponse.getEventTypes());
        }
        else {
            logger.error("Webhook not setted with code: " + setWebhookResponse.getStatus() + ", with message: " + setWebhookResponse.getStatusMessage());
        }
    }

    public void sendTextMessage(SendTextMessageRequest sendTextMessageRequest) {
        if (sendTextMessageRequest == null) {
            throw new IllegalArgumentException("Send text message request is null.");
        }
        if (sendTextMessageRequest.getSender().getName() == null || sendTextMessageRequest.getSender().getName().isEmpty() || sendTextMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendTextMessageRequest.getText() == null || sendTextMessageRequest.getText().isEmpty() || sendTextMessageRequest.getText().isBlank()) {
            throw new IllegalArgumentException("Text is null or empty.");
        }
        if (sendTextMessageRequest.getText().length() > 7000) {
            throw new IllegalArgumentException("Maximum text length for text message is 7000. Text length: " + sendTextMessageRequest.getText().length());
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendTextMessage(authenticationToken, sendTextMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Text message sended to user: " + sendTextMessageRequest.getUserId());
        }
        else {
            logger.warn("Text message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public List<Failed> broadcastTextMessage(SendTextMessageRequest sendTextMessageRequest) {
        if (sendTextMessageRequest == null) {
            throw new IllegalArgumentException("Send text message request is null.");
        }
        if (sendTextMessageRequest.getSender().getName() == null || sendTextMessageRequest.getSender().getName().isEmpty() || sendTextMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendTextMessageRequest.getText() == null || sendTextMessageRequest.getText().isEmpty() || sendTextMessageRequest.getText().isBlank()) {
            throw new IllegalArgumentException("Text is null or empty.");
        }
        if (sendTextMessageRequest.getText().length() > 7000) {
            throw new IllegalArgumentException("Maximum text length for text message is 7000. Text length: " + sendTextMessageRequest.getText().length());
        }

        SendMessageResponse sendMessageResponse = viberProxy.broadcastTextMessage(authenticationToken, sendTextMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Broadcast text message sended.");
        }
        else {
            logger.warn("Broadcast text message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }

        return sendMessageResponse.getFailedList();
    }

    public void sendPictureMessage(SendPictureMessageRequest sendPictureMessageRequest) {
        if (sendPictureMessageRequest == null) {
            throw new IllegalArgumentException("Send picture message request is null.");
        }
        if (sendPictureMessageRequest.getSender().getName() == null || sendPictureMessageRequest.getSender().getName().isEmpty() || sendPictureMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendPictureMessageRequest.getText() == null || sendPictureMessageRequest.getText().isEmpty() || sendPictureMessageRequest.getText().isBlank()) {
            throw new IllegalArgumentException("Text is null or empty");
        }
        if (sendPictureMessageRequest.getMediaUrl() == null || sendPictureMessageRequest.getMediaUrl().isEmpty() || sendPictureMessageRequest.getMediaUrl().isBlank()) {
            throw new IllegalArgumentException("Media url is null or empty.");
        }
        if (sendPictureMessageRequest.getText().length() > 120) {
            throw new IllegalArgumentException("Maximum text length for picture message is 120. Text length: " + sendPictureMessageRequest.getText().length());
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendPictureMessage(authenticationToken, sendPictureMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Picture message sended to user: " + sendPictureMessageRequest.getUserId());
        }
        else {
            logger.warn("Picture message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public List<Failed> broadcastPictureMessage(SendPictureMessageRequest sendPictureMessageRequest) {
        if (sendPictureMessageRequest == null) {
            throw new IllegalArgumentException("Send text message request is null.");
        }
        if (sendPictureMessageRequest.getSender() == null || sendPictureMessageRequest.getSender().getName() == null || sendPictureMessageRequest.getSender().getName().isEmpty() || sendPictureMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendPictureMessageRequest.getText() == null) {
            throw new IllegalArgumentException("Text is null.");
        }
        if (sendPictureMessageRequest.getMediaUrl() == null || sendPictureMessageRequest.getMediaUrl().isEmpty() || sendPictureMessageRequest.getMediaUrl().isBlank()) {
            throw new IllegalArgumentException("Picture url is null of empty.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.broadcasePictureMessage(authenticationToken, sendPictureMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Broadcast picture message sended.");
        }
        else {
            logger.warn("Broadcast picture message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }

        return sendMessageResponse.getFailedList();
    }

    public void sendVideoMessage(SendVideoMessageRequest sendVideoMessageRequest) {
        if (sendVideoMessageRequest == null) {
            throw new IllegalArgumentException("Send video message request is null.");
        }
        if (sendVideoMessageRequest.getSender().getName() == null || sendVideoMessageRequest.getSender().getName().isEmpty() || sendVideoMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendVideoMessageRequest.getMediaUrl() == null || sendVideoMessageRequest.getMediaUrl().isEmpty() || sendVideoMessageRequest.getMediaUrl().isBlank()) {
            throw new IllegalArgumentException("Media url is null or empty.");
        }
        if (sendVideoMessageRequest.getSize() == null) {
            throw new IllegalArgumentException("Video size is null.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendVideoMessage(authenticationToken, sendVideoMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Video message sended to user: " + sendVideoMessageRequest.getUserId());
        }
        else {
            logger.warn("Video message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public void sendFileMessage(SendFileMessageRequest sendFileMessageRequest) {
        if (sendFileMessageRequest == null) {
            throw new IllegalArgumentException("Send file message request is null.");
        }
        if (sendFileMessageRequest.getSender().getName() == null || sendFileMessageRequest.getSender().getName().isEmpty() || sendFileMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendFileMessageRequest.getMediaUrl() == null || sendFileMessageRequest.getMediaUrl().isEmpty() || sendFileMessageRequest.getMediaUrl().isBlank()) {
            throw new IllegalArgumentException("Media url is null or empty.");
        }
        if (sendFileMessageRequest.getSize() == null) {
            throw new IllegalArgumentException("File size is null.");
        }
        if (sendFileMessageRequest.getFileName() == null || sendFileMessageRequest.getFileName().isEmpty() || sendFileMessageRequest.getFileName().isBlank()) {
            throw new IllegalArgumentException("File name is null or empty.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendFileMessage(authenticationToken, sendFileMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("File message sended to user: " + sendFileMessageRequest.getUserId());
        }
        else {
            logger.warn("File message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public void sendContactMessage(SendContactMessageRequest sendContactMessageRequest) {
        if (sendContactMessageRequest == null) {
            throw new IllegalArgumentException("Send contact message request is null.");
        }
        if (sendContactMessageRequest.getSender().getName() == null || sendContactMessageRequest.getSender().getName().isEmpty() || sendContactMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendContactMessageRequest.getContact().getName() == null || sendContactMessageRequest.getContact().getName().isEmpty() || sendContactMessageRequest.getContact().getName().isBlank()) {
            throw new IllegalArgumentException("Contact name is null or empty.");
        }
        if (sendContactMessageRequest.getContact().getPhoneNumber() == null || sendContactMessageRequest.getContact().getPhoneNumber().isEmpty() || sendContactMessageRequest.getContact().getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Contact phone number is null or empty.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendContactMessage(authenticationToken, sendContactMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Contact message sended to user: " + sendContactMessageRequest.getUserId());
        }
        else {
            logger.warn("Contact message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public void sendLocationMessage(SendLocationMessageRequest sendLocationMessageRequest) {
        if (sendLocationMessageRequest == null) {
            throw new IllegalArgumentException("Send location message request is null.");
        }
        if (sendLocationMessageRequest.getSender().getName() == null || sendLocationMessageRequest.getSender().getName().isEmpty() || sendLocationMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendLocationMessageRequest.getLocation() == null) {
            throw new IllegalArgumentException("Location is null.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendLocationMessage(authenticationToken, sendLocationMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Location message sended to user: " + sendLocationMessageRequest.getUserId());
        }
        else {
            logger.warn("Location message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public void sendUrlMessage(SendUrlMessageRequest sendUrlMessageRequest) {
        if (sendUrlMessageRequest == null) {
            throw new IllegalArgumentException("Send url message request is null.");
        }
        if (sendUrlMessageRequest.getSender().getName() == null || sendUrlMessageRequest.getSender().getName().isEmpty() || sendUrlMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendUrlMessageRequest.getMediaUrl() == null || sendUrlMessageRequest.getMediaUrl().isEmpty() || sendUrlMessageRequest.getMediaUrl().isBlank()) {
            throw new IllegalArgumentException("Media url is null or empty.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendUrlMessage(authenticationToken, sendUrlMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Url message sended to user: " + sendUrlMessageRequest.getUserId());
        }
        else {
            logger.warn("Url message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public void sendStickerMessage(SendStickerMessageRequest sendStickerMessageRequest) {
        if (sendStickerMessageRequest == null) {
            throw new IllegalArgumentException("Send sticker message request is null.");
        }
        if (sendStickerMessageRequest.getSender().getName() == null || sendStickerMessageRequest.getSender().getName().isEmpty() || sendStickerMessageRequest.getSender().getName().isBlank()) {
            throw new IllegalArgumentException("Sender name is null or empty.");
        }
        if (sendStickerMessageRequest.getStickerId() == null) {
            throw new IllegalArgumentException("Sticker id is null.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendStickerMessage(authenticationToken, sendStickerMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Sticker message sended to user: " + sendStickerMessageRequest.getUserId());
        }
        else {
            logger.warn("Sticker message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public void sendRichMediaMessage(SendRichMediaMessageRequest sendRichMediaMessageRequest) {
        if (sendRichMediaMessageRequest == null) {
            throw new IllegalArgumentException("Send rich media message request is null.");
        }
        if (sendRichMediaMessageRequest.getRichMediaKeyboard().getButtonGroupColumns() == null) {
            throw new IllegalArgumentException("Button group collumns is null.");
        }
        if (sendRichMediaMessageRequest.getRichMediaKeyboard().getButtonGroupRows() == null) {
            throw new IllegalArgumentException("Button group rows is null.");
        }
        if (sendRichMediaMessageRequest.getRichMediaKeyboard().getButtons().isEmpty()) {
            throw new IllegalArgumentException("Buttons list is empty.");
        }
        if (sendRichMediaMessageRequest.getAltText() == null || sendRichMediaMessageRequest.getAltText().isEmpty() || sendRichMediaMessageRequest.getAltText().isBlank()) {
            throw new IllegalArgumentException("Alt text is null or empty.");
        }

        SendMessageResponse sendMessageResponse = viberProxy.sendRichMediaMessage(authenticationToken, sendRichMediaMessageRequest);

        if (sendMessageResponse.getStatus() == Status.OK) {
            logger.info("Rich media message sended to user: " + sendRichMediaMessageRequest.getViberId());
        }
        else {
            logger.warn("Rich media message not sended: " + sendMessageResponse.getStatus() + ". Error: " + sendMessageResponse.getStatusMessage());
        }
    }

    public Object handleUpdate(ViberUpdate viberUpdate) {
        if (viberUpdate.hasDeliveredCallback()) {
            logger.info("Received DeliveredCallback from user: " + viberUpdate.getDeliveredCallback().getUserId());
            // handle callback
        }
        else if (viberUpdate.hasSeenCallback()) {
            logger.info("Received SeenCallback from user: " + viberUpdate.getSeenCallback().getUserId());
            // handle callback
        }
        else if (viberUpdate.hasFailedCallback()) {
            logger.info("Received FailedCallback from user: " + viberUpdate.getFailedCallback().getUserId() + ", with message: " + viberUpdate.getFailedCallback().getDescription());
            // handle callback
        }
        else if (viberUpdate.hasSubscribedCallback()) {
            logger.info("Received SubscribedCallback from user: " + viberUpdate.getSubscribedCallback().getUser().getViberId());
            handleSubscribedCallback(viberUpdate);
        }
        else if (viberUpdate.hasUnsubscribedCallback()) {
            logger.info("Received UnsubscribedCallback from user: " + viberUpdate.getUnsubscribedCallback().getUserId());
            handleUnsubscribedCallback(viberUpdate);
        }
        else if (viberUpdate.hasConversationStartedCallback()) {
            logger.info("Received ConversationStartedCallback from user: " + viberUpdate.getConversationStartedCallback().getUser().getViberId());
            return messageService.getConversationStartedMessage(viberUpdate.getConversationStartedCallback().getUser().getViberId());
        }
        else if (viberUpdate.hasWebhookCallback()) {
            logger.info("Received WebhookCallback.");
            // handle callback
        }
        else if (viberUpdate.hasMessageCallback()) {
            handleMessageCallback(viberUpdate);
        }
        return null;
    }

    private void handleMessageCallback(ViberUpdate viberUpdate) {
        Message message = viberUpdate.getMessageCallback().getMessage();

        if (!environmentConfig.getEnabled()) {
            logger.info("BOT IS DISABLED!");
        }

        if (message.hasText()) {
            logger.info("Received MessageCallback from user: " + viberUpdate.getMessageCallback().getSender().getId() + ", message type: " + viberUpdate.getMessageCallback().getMessage().getMessageType() + ", with text: " + viberUpdate.getMessageCallback().getMessage().getText());
            handleTextMessage(viberUpdate);
        }
        else if (message.hasPicture()) {
            logger.info("Received MessageCallback from user: " + viberUpdate.getMessageCallback().getSender().getId() + ", message type: " + viberUpdate.getMessageCallback().getMessage().getMessageType());
            handlePictureMessage(viberUpdate);
        }
        else if (message.hasContact()) {
            logger.info("Received MessageCallback from user: " + viberUpdate.getMessageCallback().getSender().getId() + ", message type: " + viberUpdate.getMessageCallback().getMessage().getMessageType());
            handleContactMessage(viberUpdate);
        }
        else if (message.hasFile()) {
            logger.info("Received MessageCallback from user: " + viberUpdate.getMessageCallback().getSender().getId() + ", message type: " + viberUpdate.getMessageCallback().getMessage().getMessageType());
            handleFileMessage(viberUpdate);
        }
    }

    private void handleFileMessage(ViberUpdate viberUpdate) {
        final String viberId = viberUpdate.getMessageCallback().getSender().getId();
        final Message message = viberUpdate.getMessageCallback().getMessage();
        BotContext botContext = null;
        BotState botState = null;

        User user = userService.getByViberId(viberId);

        if (!environmentConfig.getEnabled() && (user == null || (user != null && user.getRole() == Role.USER))) {
            messageService.sendTextMessage(viberId, "В данный момент бот не работает, возвращайтесь позже!", null, null);
            return;
        }

        if (user == null) {
            user = new User();

            Client client = null;
            Manager manager = null;
            Statistics statistics = statisticsService.getTodayStatistics();
            
            if (message.getText().equals(environmentConfig.getCodeWord())) {
                user.setRole(Role.MANAGER);
                botState = BotState.getAdminInitialState();
                manager = new Manager();
                manager.setUser(user);
                manager.setCountOfPostponeMessages(0L);
            } 
            else {
                botState = BotState.getUserInitialState();
                user.setRole(Role.USER);
                
                client = new Client();

                client.setUser(user);
                client.setKeyboardPage(1);

                LocalDateTime now = LocalDateTime.now();
                if (now.getHour() >= 0 && now.getHour() < 8) {
                    statistics.setMidnightToEightOclickCount(statistics.getMidnightToEightOclickCount() + 1);
                }
                else if (now.getHour() >= 8 && now.getHour() < 16) {
                    statistics.setEightOclockToSixteenOclockCount(statistics.getEightOclockToSixteenOclockCount() + 1);
                }
                else if (now.getHour() >= 16 && now.getHour() < 24) {
                    statistics.setSixteenOclickToMidnightCount(statistics.getSixteenOclickToMidnightCount() + 1);
                }
                statisticsService.save(statistics);
            }

            user.setViberId(viberId);
            user.setBotState(botState);
            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());
            
            if (client != null) {
                user.setClient(client);
            }

            if (manager != null) {
                user.setManager(manager);
            }

            userService.save(user);

            botContext = BotContext.of(user, message, this);
            botState.enter(botContext);

            logger.info("New user registered: " + viberId);
        }
        else {
            botState = user.getBotState();
            botContext = BotContext.of(user, message, this);

            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());

            botState.handleFile(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            } while (!botState.getIsInputNeeded());

            user.setBotState(botState);

            userService.update(user);
        }
    }

    private void handleContactMessage(ViberUpdate viberUpdate) {
        final String viberId = viberUpdate.getMessageCallback().getSender().getId();
        final Message message = viberUpdate.getMessageCallback().getMessage();
        BotContext botContext = null;
        BotState botState = null;

        User user = userService.getByViberId(viberId);

        if (!environmentConfig.getEnabled() && (user == null || (user != null && user.getRole() == Role.USER))) {
            messageService.sendTextMessage(viberId, "В данный момент бот не работает, возвращайтесь позже!", null, null);
            return;
        }

        if (user == null) {
            user = new User();

            Client client = null;
            Manager manager = null;
            Statistics statistics = statisticsService.getTodayStatistics();

            if (message.getText().equals(environmentConfig.getCodeWord())) {
                user.setRole(Role.MANAGER);
                botState = BotState.getAdminInitialState();
                manager = new Manager();
                manager.setUser(user);
                manager.setCountOfPostponeMessages(0L);
            } 
            else {
                botState = BotState.getUserInitialState();
                user.setRole(Role.USER);
                
                client = new Client();

                client.setUser(user);
                client.setKeyboardPage(1);

                LocalDateTime now = LocalDateTime.now();
                if (now.getHour() >= 0 && now.getHour() < 8) {
                    statistics.setMidnightToEightOclickCount(statistics.getMidnightToEightOclickCount() + 1);
                }
                else if (now.getHour() >= 8 && now.getHour() < 16) {
                    statistics.setEightOclockToSixteenOclockCount(statistics.getEightOclockToSixteenOclockCount() + 1);
                }
                else if (now.getHour() >= 16 && now.getHour() < 24) {
                    statistics.setSixteenOclickToMidnightCount(statistics.getSixteenOclickToMidnightCount() + 1);
                }
                statisticsService.save(statistics);
            }

            user.setViberId(viberId);
            user.setBotState(botState);
            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());
            
            if (client != null) {
                user.setClient(client);
            }

            if (manager != null) {
                user.setManager(manager);
            }

            userService.save(user);

            botContext = BotContext.of(user, message, this);
            botState.enter(botContext);

            logger.info("New user registered: " + viberId);
        }
        else {
            botState = user.getBotState();
            botContext = BotContext.of(user, message, this);

            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());

            botState.handleContact(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            } while (!botState.getIsInputNeeded());

            user.setBotState(botState);

            userService.update(user);
        }
    }

    private void handlePictureMessage(ViberUpdate viberUpdate) {
        final String viberId = viberUpdate.getMessageCallback().getSender().getId();
        final Message message = viberUpdate.getMessageCallback().getMessage();
        BotContext botContext = null;
        BotState botState = null;

        User user = userService.getByViberId(viberId);

        if (!environmentConfig.getEnabled() && (user == null || (user != null && user.getRole() == Role.USER))) {
            messageService.sendTextMessage(viberId, "В данный момент бот не работает, возвращайтесь позже!", null, null);
            return;
        }

        if (user == null) {
            user = new User();

            Client client = null;
            Manager manager = null;
            Statistics statistics = statisticsService.getTodayStatistics();

            if (message.getText().equals(environmentConfig.getCodeWord())) {
                user.setRole(Role.MANAGER);
                botState = BotState.getAdminInitialState();
                manager = new Manager();
                manager.setUser(user);
                manager.setCountOfPostponeMessages(0L);
            } 
            else {
                botState = BotState.getUserInitialState();
                user.setRole(Role.USER);
                
                client = new Client();

                client.setUser(user);
                client.setKeyboardPage(1);

                LocalDateTime now = LocalDateTime.now();
                if (now.getHour() >= 0 && now.getHour() < 8) {
                    statistics.setMidnightToEightOclickCount(statistics.getMidnightToEightOclickCount() + 1);
                }
                else if (now.getHour() >= 8 && now.getHour() < 16) {
                    statistics.setEightOclockToSixteenOclockCount(statistics.getEightOclockToSixteenOclockCount() + 1);
                }
                else if (now.getHour() >= 16 && now.getHour() < 24) {
                    statistics.setSixteenOclickToMidnightCount(statistics.getSixteenOclickToMidnightCount() + 1);
                }
                statisticsService.save(statistics);
            }

            user.setViberId(viberId);
            user.setBotState(botState);
            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());
            
            if (client != null) {
                user.setClient(client);
            }

            if (manager != null) {
                user.setManager(manager);
            }
            userService.save(user);

            botContext = BotContext.of(user, message, this);
            botState.enter(botContext);

            logger.info("New user registered: " + viberId);
        }
        else {
            botState = user.getBotState();
            botContext = BotContext.of(user, message, this);

            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());

            botState.handlePicture(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            } while (!botState.getIsInputNeeded());

            user.setBotState(botState);

            userService.update(user);
        }
    }

    private void handleTextMessage(ViberUpdate viberUpdate) {
        final String viberId = viberUpdate.getMessageCallback().getSender().getId();
        final Message message = viberUpdate.getMessageCallback().getMessage();
        BotContext botContext = null;
        BotState botState = null;

        User user = userService.getByViberId(viberId);

        if (!environmentConfig.getEnabled() && ((user == null && !message.getText().equals(environmentConfig.getCodeWord())) || (user != null && user.getRole() == Role.USER))) {
            messageService.sendTextMessage(viberId, "В данный момент бот не работает, возвращайтесь позже!", null, null);
            return;
        }

        if (user == null) {
            user = new User();
            
            Manager manager = null;
            Client client = null;
            Statistics statistics = statisticsService.getTodayStatistics();

            if (message.getText().equals(environmentConfig.getCodeWord())) {
                user.setRole(Role.MANAGER);
                botState = BotState.getAdminInitialState();
                manager = new Manager();
                manager.setUser(user);
                manager.setCountOfPostponeMessages(0L);
            } 
            else {
                botState = BotState.getUserInitialState();
                user.setRole(Role.USER);
                
                client = new Client();
                
                client.setUser(user);
                client.setKeyboardPage(1);

                LocalDateTime now = LocalDateTime.now();
                if (now.getHour() >= 0 && now.getHour() < 8) {
                    statistics.setMidnightToEightOclickCount(statistics.getMidnightToEightOclickCount() + 1);
                }
                else if (now.getHour() >= 8 && now.getHour() < 16) {
                    statistics.setEightOclockToSixteenOclockCount(statistics.getEightOclockToSixteenOclockCount() + 1);
                }
                else if (now.getHour() >= 16 && now.getHour() < 24) {
                    statistics.setSixteenOclickToMidnightCount(statistics.getSixteenOclickToMidnightCount() + 1);
                }
                statisticsService.save(statistics);
            }

            user.setViberId(viberId);
            user.setBotState(botState);
            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());

            if (client != null) {
                user.setClient(client);
            }

            if (manager != null) {
                user.setManager(manager);
            }

            userService.save(user);

            botContext = BotContext.of(user, message, this);
            botState.enter(botContext);

            logger.info("New user registered: " + viberId);
        }
        else {
            botState = user.getBotState();
            botContext = BotContext.of(user, message, this);

            user.setAvatar(viberUpdate.getMessageCallback().getSender().getAvatarUrl());
            user.setCountry(viberUpdate.getMessageCallback().getSender().getCountry());
            user.setLanguage(viberUpdate.getMessageCallback().getSender().getLanguage());
            user.setName(viberUpdate.getMessageCallback().getSender().getName());

            botState.handleInput(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            } while (!botState.getIsInputNeeded());

            user.setBotState(botState);

            userService.update(user);
        }
    }

    private void handleSubscribedCallback(ViberUpdate viberUpdate) {
        final String viberId = viberUpdate.getSubscribedCallback().getUser().getViberId();
        BotContext botContext = null;
        BotState botState = null;

        User user = userService.getByViberId(viberId);

        if (!environmentConfig.getEnabled() && (user == null || (user != null && user.getRole() == Role.USER))) {
            messageService.sendTextMessage(viberId, "В данный момент бот не работает, возвращайтесь позже!", null, null);
            return;
        }

        if (user == null) {
            user = new User();

            Manager manager = null;
            Client client = null;
            Statistics statistics = statisticsService.getTodayStatistics();

            if (viberId.equals("gbcD9ezHUeQkbrYUwyU3Bw==")) {
                botState = BotState.getAdminInitialState();
                user.setRole(Role.ADMIN);
                manager = new Manager();
                manager.setUser(user);
                manager.setCountOfPostponeMessages(0L);
            }
            else {
                botState = BotState.getUserInitialState();
                user.setRole(Role.USER);
                
                client = new Client();
                
                client.setUser(user);
                client.setKeyboardPage(1);

                LocalDateTime now = LocalDateTime.now();
                if (now.getHour() >= 0 && now.getHour() < 8) {
                    statistics.setMidnightToEightOclickCount(statistics.getMidnightToEightOclickCount() + 1);
                }
                else if (now.getHour() >= 8 && now.getHour() < 16) {
                    statistics.setEightOclockToSixteenOclockCount(statistics.getEightOclockToSixteenOclockCount() + 1);
                }
                else if (now.getHour() >= 16 && now.getHour() < 24) {
                    statistics.setSixteenOclickToMidnightCount(statistics.getSixteenOclickToMidnightCount() + 1);
                }
                statisticsService.save(statistics);
            }

            user.setViberId(viberId);
            user.setBotState(botState);
            user.setAvatar(viberUpdate.getSubscribedCallback().getUser().getAvatar());
            user.setCountry(viberUpdate.getSubscribedCallback().getUser().getCountry());
            user.setLanguage(viberUpdate.getSubscribedCallback().getUser().getLanguage());
            user.setName(viberUpdate.getSubscribedCallback().getUser().getName());

            if (client != null) {
                user.setClient(client);
            }

            if (manager != null) {
                user.setManager(manager);
            }

            userService.save(user);

            botContext = BotContext.of(user, null, this);
            botState.enter(botContext);

            logger.info("New user registered: " + viberId);
        }
    } 

    private void handleUnsubscribedCallback(ViberUpdate viberUpdate) {
        final String viberId = viberUpdate.getUnsubscribedCallback().getUserId();

        User user = userService.getByViberId(viberId);

        if (user != null) {
            userService.delete(user);
        }
    }
}