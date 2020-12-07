package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.bot.KeyboardSource;
import by.testbot.models.Client;
import by.testbot.models.Manager;
import by.testbot.models.viber.Failed;
import by.testbot.models.viber.Sender;
import by.testbot.payload.requests.message.SendFileMessageRequest;
import by.testbot.payload.requests.message.SendPictureMessageRequest;
import by.testbot.payload.requests.message.SendTextMessageRequest;
import by.testbot.services.file.ExcelService;
import by.testbot.services.file.FileService;
import by.testbot.services.other.LocaleMessageService;
import by.testbot.services.viber.ViberService;

@Service
public class MessageService {
    @Autowired
    private ViberService viberService;

    @Autowired
    private CarService carService;

    @Autowired
    private KeyboardSource keyboardSource;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FileService fileService;

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

    public List<Failed> sendTextMessageToAll(List<String> broadcastList, String message) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(message);
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setBroadcastList(broadcastList);

        return viberService.broadcastTextMessage(sendTextMessageRequest);
    }

    public List<Failed> sendPictureMessageToAll(List<String> broadcastList, String message, String pictureUrl) {
        SendPictureMessageRequest sendPictureMessageRequest = new SendPictureMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendPictureMessageRequest.setText(message);
        sendPictureMessageRequest.setMediaUrl(pictureUrl);
        sendPictureMessageRequest.setSender(sender);
        sendPictureMessageRequest.setBroadcastList(broadcastList);

        return viberService.broadcastPictureMessage(sendPictureMessageRequest);
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
        SendFileMessageRequest sendFileMessageRequest = new SendFileMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        String filename = excelService.generateExcelAndWriteActualData();

        sendFileMessageRequest.setMediaUrl(viberService.getFileEndpoint() + filename);
        sendFileMessageRequest.setSender(sender);
        sendFileMessageRequest.setFileName(filename);
        sendFileMessageRequest.setSize(fileService.getFileSizeInBytes(filename));
        sendFileMessageRequest.setUserId(viberId);

        viberService.sendFileMessage(sendFileMessageRequest);
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

    public void sendSelectBotMessageToEdit(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.editTextsWhichBotSend.selectMessage"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getBackKeyboard());
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendListOfMessagesWhichBotSendMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().getPrettyFormatedAllBotMessages());
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotTextMessage(Manager manager) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Сообщение:\n" + manager.getBotMessage().getMessage());
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(manager.getUser().getViberId());

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendEnterBotMessageTextMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.editTextsWhichBotSend.enterText"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendConfirmEditBotMessageMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.editTextsWhichBotSend.confirmEdit"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotMessageEditSuccessConfirmationMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.editTextsWhichBotSend.successConfirmation"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotMessageDeclineConfirmationMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.editTextsWhichBotSend.declineConfirmation"));
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

    public void sendAskBrandMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.userDialog.askBrand") + carService.generateBrandString());
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendAskModelMessage(String viberId, String brand) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.userDialog.askModel") + carService.generateModelsString(brand));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendAdminMainMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.mainMenu"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getAdminMainMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setMinApiVersion(6);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendManagersMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.managers"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getManagersMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendClientsMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.clients"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getClientsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendReportMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.report"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getReportMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendIntegrationsMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.integrations"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getIntegrationsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSettingsMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.settings"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getSettingsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotUsagePeriodMenuMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.botUsagePeriod"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getBotUsagePeriodMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendConfirmPostponeMessageMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Подтвердите отправку отложенного сообщения");
        sendTextMessageRequest.setKeyboard(keyboardSource.getConfirmPostponeMessageKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendAddPhotoMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.postponeMessage.uploadPhoto"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getWithoutPhotoKeyboard());
        sendTextMessageRequest.setUserId(viberId);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    //endregion

    //region UserMessageKeyboards

    public void sendIsHaveAnyBenefitsMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(1, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(client.getUser().getViberId());
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendNegativeDialogEndMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.negativeDialogEnd"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendPositiveDialogEndMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.positiveDialogEnd"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendPositiveDialogEndAndPhoneEnteredMessageAndKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.positiveDialogEndAndPhoneEntered"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(2, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(client.getUser().getViberId());
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendDontWorryAboutPricesAndIsLinkOpensMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(3, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(client.getUser().getViberId());
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendWhenArePlanningToBuyCarMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(4, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getWhenWillBuyCarKeyboard());
        sendTextMessageRequest.setUserId(client.getUser().getViberId());
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendIsInterestedInSpecificCarVariantsMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(5, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(client.getUser().getViberId());
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendWillAskFewQuestionsRegardingYourCriteriaMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(6, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(client.getUser().getViberId());
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendAskAndEnterPhoneNumberMessageAndKeyboard(Client client) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(viberService.getBotMessageService().formatBotMessage(7, client));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getAskAndEnterPhoneNumberKeyboard());
        sendTextMessageRequest.setMinApiVersion(3);
        sendTextMessageRequest.setUserId(client.getUser().getViberId());

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendAskYearOfIssueMessage(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("message.userDialog.askYearOfIssue"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }
}
