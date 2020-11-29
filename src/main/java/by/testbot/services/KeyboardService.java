package by.testbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.bot.KeyboardSource;
import by.testbot.models.Sender;
import by.testbot.payload.requests.message.SendTextMessageRequest;

@Service
public class KeyboardService {
    @Autowired
    private ViberService viberService;

    @Autowired
    private LocaleMessageService localeMessageService;

    @Autowired
    private KeyboardSource keyboardSource;

    //region AdminMessageKeyboards

    public void sendAdminMainMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.mainMenu"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getAdminMainMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendPostponeMessageMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.postponedMessage"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getPostponeMessageMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendManagersMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.managers"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getManagersMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendClientsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.clients"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getClientsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendReportMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.report"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getReportMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendIntegrationsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.integrations"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getIntegrationsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSettingsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.settings"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getSettingsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotUsagePeriodMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("keyboardMessage.botUsagePeriod"));
        sendTextMessageRequest.setKeyboard(keyboardSource.getBotUsagePeriodMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendConfirmPostponeMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Подтвердите отправку отложенного сообщения");
        sendTextMessageRequest.setKeyboard(keyboardSource.getConfirmPostponeMessageKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    //endregion

    //region UserMessageKeyboards

    public void sendIsHaveAnyBenefitsMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.isHaveAnyBenefits", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendNegativeDialogEndMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.negativeDialogEnd"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendPositiveDialogEndMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.positiveDialogEnd"));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.areInterestedToKnowAdditionalDataAboutCarsAtAuctions", "", "", "", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendDontWorryAboutPricesAndIsLinkOpensMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.dontWorryAboutPricesAndIsLinkOpens", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendWhenArePlanningToBuyCarMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.whenArePlanningToBuyCar", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getWhenWillBuyCarKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendIsInterestedInSpecificCarVariantsMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.isInterestedInSpecificCarVariants", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendWillAskFewQuestionsRegardingYourCriteriaMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.willAskFewQuestionsRegardingYourCriteria", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(keyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    //endregion

}
