package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.Sender;
import by.testbot.payload.requests.message.SendTextMessageRequest;

@Service
public class MessageService {
    @Autowired
    private ViberService viberService;

    @Autowired
    private LocaleMessageService localeMessageService;

    public SendTextMessageRequest getConversationStartedMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.welcome"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        return sendTextMessageRequest;
    }

    public void sendTextMessage(String viberId, String message) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(message);
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendTextMessageToAll(List<String> broadcastList, String message) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(message);
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setBroadcastList(broadcastList);

        viberService.broadcastTextMessage(sendTextMessageRequest);
    }

    // Admin messages

    public void sendAddTextMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.postponeMessage.enterText"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSelectDateAndTimeMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.postponeMessage.setDateAndTime"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSuccessPostponeMessageConfirmationMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.postponeMessage.successPostponeMessageConfirmation"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendDeclinePostponeMessageConfirmationMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.postponeMessage.declinePostponeMessageConfirmation"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendListOfManagersMessage(String viberId) {
        //TODO: get and send list of managers
    }

    public void sendAddManagerMessage(String viberId) {
        //TODO: send add manager details messages
    }

    public void sendDeleteManagerMessage(String viberId) {
        //TODO: send delete manager details messages
    }

    public void changeManagerPrivilegiesMessage(String viberId) {
        //TODO: send change manager privilegies details message
    }

    public void sendListOfClientsMessage(String viberId) {
        //TODO: get and send list of clients
    }

    public void sendAdditionalOperationsWithClientsMessage(String viberId) {
        //TODO: send additional operations with clients details messages
    }

    public void sendGetReportAboutManagersWork(String viberId) {
        //TODO: create and send report about managers work
    }

    public void sendGetReportAboutBotWork(String viberId) {
        //TODO: create and send report about bot work
    }

    public void sendAddIntegrationMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Введите токен для добавления интеграции");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendDeleteIntegrationMessage(String viberId) {
        //TODO: send delete integration details message
    }

    public void sendNewIntegrationsMessage(String viberId) {
        //TODO: get and send list of integrations ?????
    }

    public void sendListOfMessagesWhichBotSendMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        //TODO: get and send list of messages which bot send

        sendTextMessageRequest.setText(localeMessageService.getMessage("button.settings.editTextsWhichBotSend"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendInChatBotUsagePeriodSettingsMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        //TODO: send in chat bot work time period settings details

        sendTextMessageRequest.setText(localeMessageService.getMessage("button.botUsagePeriodMenu.inChat"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendAtNightBotUsagePeriodSettingsMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        //TODO: send at night bot work time period settings details

        sendTextMessageRequest.setText(localeMessageService.getMessage("button.botUsagePeriodMenu.atNight"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    // User messages

    public void sendAskClientNameMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.askName"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }
}
