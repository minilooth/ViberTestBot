package by.testbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.Sender;
import by.testbot.models.User;
import by.testbot.payload.requests.message.SendTextMessageRequest;

@Service
public class MessageService {
    @Autowired
    private ViberService viberService;

    public SendTextMessageRequest getConversationStartedMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Привет, это бот AutoCapitalBot. Чтобы начать отправьте любое сообщение.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        return sendTextMessageRequest;
    }

    public void sendAskClientNameMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Как к Вам можно обращаться?");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotOfferServicesMessage(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText(name + ", подскажите есть ли у Вас (или Ваших знакомых) льгота по 140-у указу для возмещения 50% таможенных пошлин?\nЯ спрашиваю это, для того чтобы более точно Вас сориентировать по окончательной сумме и условиям необходимым для приобретения такого автомобиля из США.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest);    
    }

    public void sendAddTextMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Введите текст, который будет отправлен");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendAddPhotoMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Загрузите фото, которое будет отправлено");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSelectDateAndTimeMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Выберите день и время когда сообщение будет отправлено");
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

        sender.setName("AutoCapitalBot");

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
        //TODO: get and send list of messages which bot send
    }

    public void sendInChatBotWorkTimePeriodSettingsMessage(String viberId) {
        //TODO: send in chat bot work time period settings details
    }

    public void sendAtNightBotWorkTimePeriodSettingsMessage(String viberId) {
        //TODO: send at night bot work time period settings details
    }

    public void sendAskForPhoneNumberMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Отлично, напишите свой номер телефона, я Вас наберу, и мы быстро осбудим все вопросы.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }
}
