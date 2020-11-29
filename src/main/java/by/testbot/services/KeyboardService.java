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

    //region AdminMessageKeyboards

    public void sendAdminMainMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Главное меню");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getAdminMainMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendPostponeMessageMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Отложенное сообщение");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getPostponeMessageMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendListOfManagersMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Список менеджеров");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getListOfManagersMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendListOfClientsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Список клиентов");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getListOfClientsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendReportMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Отчет");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getReportMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendIntegrationsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Интеграции");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getIntegrationsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSettingsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Настройки");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getSettingsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotUsagePeriodMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Настройка периода временного использования бота");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getSetBotUsagePeriodMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendConfirmPostponeMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Подтвердите отправку отложенного сообщения");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getConfirmPostponeMessageKeyboard());
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
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendNegativeDialogEndMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Вы ответили нет. Если вы хотите начать диалог заново, то нажмите кнопку снизу.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendPositiveDialogEndMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText("Ваш номер телефона успешно записан. В ближайшее время с вами свяжется менеджер.\nЕсли вы хотите начать диалог заново, то нажмите кнопку снизу.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.areInterestedToKnowAdditionalDataAboutCarsAtAuctions", "", "", "", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendDontWorryAboutPricesAndIsLinkOpensMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.dontWorryAboutPricesAndIsLinkOpens", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendWhenArePlanningToBuyCarMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.whenArePlanningToBuyCar", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getWhenWillBuyCarKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendIsInterestedInSpecificCarVariantsMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.isInterestedInSpecificCarVariants", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendWillAskFewQuestionsRegardingYourCriteriaMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(localeMessageService.getMessage("reply.willAskFewQuestionsRegardingYourCriteria", name));
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    //endregion

}
