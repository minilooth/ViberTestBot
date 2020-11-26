package by.testbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.bot.KeyboardSource;
import by.testbot.models.Sender;
import by.testbot.payload.requests.message.SendRichMediaMessageRequest;
import by.testbot.payload.requests.message.SendTextMessageRequest;

@Service
public class KeyboardService {
    @Autowired
    private ViberService viberService;

    // public void sendAdminMainMenuRichMediaKeyboard(String viberId) {
    //     SendRichMediaMessageRequest sendRichMediaMessageRequest = new SendRichMediaMessageRequest();
    //     Sender sender = new Sender();

    //     sender.setName("AutoCapitalBot");

    //     sendRichMediaMessageRequest.setRichMediaKeyboard(KeyboardSource.getAdminMainMenuRichMediaKeyboard());
    //     sendRichMediaMessageRequest.setViberId(viberId);
    //     sendRichMediaMessageRequest.setAltText("Admin menu");
    //     sendRichMediaMessageRequest.setSender(sender);

    //     viberService.sendRichMediaMessage(sendRichMediaMessageRequest);
    // }

    public void sendAdminMainMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Главное меню");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getAdminMainMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendPostponeMessageMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Отложенное сообщение");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getPostponeMessageMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendListOfManagersMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Список менеджеров");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getListOfManagersMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendListOfClientsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Список клиентов");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getListOfClientsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendReportMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Отчет");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getReportMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendIntegrationsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Интеграции");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getIntegrationsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendSettingsMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Настройки");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getSettingsMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotUsagePeriodMenuKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Настройка периода временного использования бота");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getSetBotUsagePeriodMenuKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendConfirmPostponeMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Подтвердите отправку отложенного сообщения");
        sendTextMessageRequest.setKeyboard(KeyboardSource.getConfirmPostponeMessageKeyboard());
        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setSender(sender);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendBotOffersServicesMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText(name + ", подскажите есть ли у Вас (или Ваших знакомых) льгота по 140-у указу для возмещения 50% таможенных пошлин?\nЯ спрашиваю это, для того чтобы более точно Вас сориентировать по окончательной сумме и условиям необходимым для приобретения такого автомобиля из США.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendEndDialogMessageKeyboard(String viberId) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Вы ответили нет. Если вы хотите начать диалог заново, то нажмите кнопку снизу.");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getEndDialogKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendApproximatePriceMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText("Спасибо. Ориентировочные цифры необходимые для приобретения интересующего Вас авто , с пробегом до , целой безопасностью, доставку его в РБ, и растоможку под 140-й указ, с учетом возможного косметического ремонта, от .\n" + name + ", Вам интересно узнать статестические данные о ценах, настоящих пробегах и состоянии, в которм продаются интересующие Вас авто на аукционах за последнее время?");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendDontWorryMessageKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText(name + ", не пугайтесь сразу, цен и состояния увиденных авто, посмотрите больше вариантов.\nПри подборе для клиентов, мы всегда находим авто с минимальными, косметическими повреждениями и выгодные по стоимости.\\nПроверьте, ссылка открывается?");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendResearchCarPricesKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText(name + ", изучайте по чем продаются такие авто в США, открывайте вкладки понравившихся по цене и состоянию авто, оценивайте их повреждения и пробеги, анализируйте по чем такие авто продают у нас."+ 
                "\nПроявленный интерес и несложный анализ информации, даст Вам возможность сделать осознанное и выгодное проибретение!" + 
                "\nПо мере возрастания Вашего интереса к авто из США, задавайте мне вопросы и я поделюсь с Вами полезной информацией, позволяющей выгодно приобрести авто не только для личного пользования, но и в качестве прибыльной инвестиции после его продажи.\n" + 
                name + ", а Вы планируете приобрести авто в ближайшее время, или после нового года?");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getWhenWillBuyCarKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendSpecificOptionsKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText(name + ", Вас интересуют ссылки на конкретные варианты находящиеся сейчас в продаже на площадках аукциона, с детальным расчетом всех затрат, выводом стоимости под ключ и обоснованием экономической выгоды от покупки?");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

    public void sendProposalKeyboard(String viberId, String name) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName("AutoCapitalBot");

        sendTextMessageRequest.setText(name + ", тогда предлагаю нам поступить следующим образом: чтобы предложить Вам конкретные авто и сделать расчеты, наиболее точно, я задам несколько вопросов касаемо Ваших важных критериев выбора авто, отвечу на Ваши вопросы, затем предложу лучшие авто имеющиеся в продаже в штатах, Вы рассмотрите, и если она вам понравятся, будем общаться дальше.\nВас устраивает?");
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setKeyboard(KeyboardSource.getYesNoKeyboard());
        sendTextMessageRequest.setUserId(viberId);
    
        viberService.sendTextMessage(sendTextMessageRequest); 
    }

}
