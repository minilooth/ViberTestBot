package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.bot.KeyboardSource;
import by.testbot.models.BotMessage;
import by.testbot.models.Button;
import by.testbot.models.Client;
import by.testbot.models.Manager;
import by.testbot.models.viber.Failed;
import by.testbot.models.viber.Keyboard;
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
    private KeyboardSource keyboardSource;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ButtonService buttonService;

    @Autowired
    private BotMessageService botMessageService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LocaleMessageService localeMessageService;

    public SendTextMessageRequest getConversationStartedMessage(String viberId) {
        return this.getConversationStartedMessage(viberId, localeMessageService.getMessage("message.welcome"), null, null);
    }

    public List<Failed> sendTextMessageToAll(List<String> broadcastList, String message) {
        return this.sendTextMessageToAll(broadcastList, message, null, null);
    }

    public List<Failed> sendPictureMessageToAll(List<String> broadcastList, String message, String pictureUrl) {
        return this.sendPictureMessageToAll(broadcastList, message, pictureUrl, null, null);
    }

    // Admin messages

    public void sendAskManagerFirstnameMessage(String viberId) {
        this.sendTextMessage(viberId, "Поздравляем, вы стали менеджером!\nЧтобы продолжить нужно заполнить информацию", null, null);
        this.sendTextMessage(viberId, "Введите имя", null, null);
    }

    public void sendAskManagerSurnameMessage(String viberId) {
        this.sendTextMessage(viberId, "Введите фамилию", null, null);
    } 

    public void sendAskManagerPhoneNumberMessage(String viberId) {
        this.sendTextMessage(viberId, "Введите номер телефона(в формате 375291234567) или нажмите кнопку снизу", keyboardSource.getAskManagerPhoneNumberKeyboard(), 3);
    }

    public void sendSuccessfullyFilledManagerProfileMessage(String viberId) {
        this.sendTextMessage(viberId, "Профиль менеджера успешно заполнен", null, null);
    }

    public void sendAddTextMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.postponeMessage.enterText"), null, null);
    }

    public void sendSelectDateAndTimeMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.postponeMessage.setDateAndTime"), null, null);
    }

    public void sendSuccessPostponeMessageConfirmationMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.postponeMessage.successPostponeMessageConfirmation"), null, null);
    }

    public void sendDeclinePostponeMessageConfirmationMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.postponeMessage.declinePostponeMessageConfirmation"), null, null);
    }

    public void sendListOfManagersIsEmptyMessage(String viberId) {
        this.sendTextMessage(viberId, "Список менеджеров пуст", null, null);
    }

    public void sendListOfManagersMessage(String viberId, List<Manager> managers) {
        this.sendTextMessage(viberId, managerService.formatManagers(managers), null, null);
    }

    public void sendShareManagerContactMessage(String viberId) {
        this.sendTextMessage(viberId, "Отправьте контакт клиента, которого хотите сделать менеджером\n(Клиент должен пройти диалог до конца и поделиться номером телефона)", keyboardSource.getBackKeyboard(), null);
    }

    public void sendClientNotFoundMessage(String viberId) {
        this.sendTextMessage(viberId, "Клиент не найден или не поделился номером телефона", null, null);
    }

    public void sendClientInformationMessage(String viberId, Client client, String clientAvatar) {
        this.sendPictureMessage(viberId, clientService.getClientInformation(client), clientAvatar, null ,null);
    }

    public void sendManagerInformationMessage(String viberId, Manager manager, String managerAvatar) {
        this.sendPictureMessage(viberId, managerService.getManagerInformation(manager), managerAvatar, null, null);
    }

    public void sendConfrimAddNewManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите добавление менеджера", keyboardSource.getConfirmManagerKeyboard(), null);
    }

    public void sendSuccessfullyAddedNewManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Менеджер успешно добавлен", null, null);
    }

    public void sendCancellerationAddManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Вы отменили добавление менеджера", null, null);
    }

    public void sendSelectManagerToDelete(String viberId) {
        this.sendTextMessage(viberId, "Отправьте контакт менеджера для удаления", keyboardSource.getBackKeyboard(), null);
    }

    public void sendUnableToDeleteSelfMessage(String viberId) {
        this.sendTextMessage(viberId, "Невозможно удалить самого себя", null, null);
    }

    public void sendConfrimDeleteManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите удаление менеджера", keyboardSource.getConfirmManagerKeyboard(), null);
    }

    public void sendSuccessfullyDeleteManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Менеджер успешно удален", null, null);
    }

    public void sendUnableToDeleteManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Не удалось удалить менеджера так как он не найден или уже удален", null, null);
    }

    public void sendCancellerationDeleteManagerMessage(String viberId) {
        this.sendTextMessage(viberId, "Удаление менеджера успешно отменено", null, null);
    }

    public void changeManagerPrivilegiesMessage(String viberId) {
        //TODO: send change manager privilegies details message
    }

    public void sendListOfClientsMessage(String viberId) {
        String filename = excelService.generateExcelAndWriteActualData();
        String mediaUrl = viberService.getFileEndpoint() + filename;
        Long fileSize = fileService.getFileSizeInBytes(filename);

        this.sendFileMessage(viberId, mediaUrl, filename, fileSize);
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
        this.sendTextMessage(viberId, "Введите токен для добавления интеграции", null, null);
    }

    public void sendDeleteIntegrationMessage(String viberId) {
        //TODO: send delete integration details message
    }

    public void sendNewIntegrationsMessage(String viberId) {
        //TODO: get and send list of integrations ?????
    }

    public void sendSelectBotMessageToEdit(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.editTextsWhichBotSend.selectMessage"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendBotMessageButtonsMenuKeyboard(String viberId) {
        this.sendTextMessage(viberId, "Меню редактирования сообщения", keyboardSource.getBotMessageButtonsMenuKeyabord(), null);
    }

    public void sendBotMessageMenuKeyboard(String viberId) {
        this.sendTextMessage(viberId, "Меню сообщений бота", keyboardSource.getBotMessageMenuKeyabord(), null);
    }

    public void sendUnableToEditBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Не удалось изменить сообщение бота, так как оно не найдено или удалено", null, null);
    }

    public void sendDeleteBotMessageButtonMessage(String viberId, List<Button> buttons) {
        this.sendTextMessage(viberId, "Список кнопок:\n" + buttonService.formatButtons(buttons), null, null);
        this.sendTextMessage(viberId, "Введите номер кнопки для удаления", keyboardSource.getBackKeyboard(), null);
    }

    public void sendListOfBotMessageButtons(String viberId, List<Button> buttons) {
        this.sendTextMessage(viberId, "Список кнопок:\n" + buttonService.formatButtons(buttons), null, null);
    }

    public void sendAddBotMessageButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Введите текст кнопки", null, null);
    }

    public void sendSuccessfullyAddedButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Кнопка успешно добавлена", null, null);
    }

    public void sendConfirmAddNewBotMessageButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите добавление кнопки", keyboardSource.getConfirmBotMessageButtonKeyboard(), null);
    }
    
    public void sendUnableToAddNewButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Не удалось добавить новую кнопку, так как сообщение бота не найдено или удалено", null, null);
    }

    public void sendConfirmDeleteBotMessageButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите удаление кнопки", keyboardSource.getConfirmBotMessageButtonKeyboard(), null);
    }

    public void sendUnableToDeleteButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Не удалось удалить кнопку, так как она не найдена удалена", null, null);
    }

    public void sendSuccessfullyDeletedButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Кнопка успешно удалена", null, null);
    }

    public void sendCancellerationDeleteButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Вы отменили удаление кнопки", null, null);
    }

    public void sendCancellerationAddNewButtonMessage(String viberId) {
        this.sendTextMessage(viberId, "Вы отменили добавление новой кнопки", null, null);
    }

    public void sendChangesSuccessfullySavedMessage(String viberId) {
        this.sendTextMessage(viberId, "Изменения успешно сохранены", null, null);
    }

    public void sendChangesIsCancelledMessage(String viberId) {
        this.sendTextMessage(viberId, "Изменения отменены", null, null);
    }

    public void sendSelectAnswerTypeMessage(String viberId) {
        this.sendTextMessage(viberId, "Выберите какой тип ответа будет сохраняться при нажатии на кнопку", keyboardSource.getAnswerTypeKeyboard(), null);
    }

    public void sendIsButtonEndsDialogueMessage(String viberId) {
        this.sendTextMessage(viberId, "При нажатии на кнопку, диалог будет продолжаться?", keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendSelectBotMessageButtonActionTypeMessage(String viberId) {
        this.sendTextMessage(viberId, "Какого типа будет кнопка?", keyboardSource.getButtonTypeKeyboard(), null);
    }

    public void sendListOfBotMessagesMessage(String viberId) {
        this.sendTextMessage(viberId, viberService.getBotMessageService().getPrettyFormatedAllBotMessages(), null, null);
    }

    public void sendBotTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), "Текущий текст сообщения:\n" + manager.getBotMessage().getMessage(), null, null);
    }

    public void sendEnterBotMessageTextMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.editTextsWhichBotSend.enterText"), null, null);
    }

    public void sendUnableToChangeBotMessageTextMessage(String viberId) {
        this.sendTextMessage(viberId, "Не удалось изменить текст сообщения бота, так как оно не найдено или удалено", null, null);
    }

    public void sendCancellerationChangeBotMessageTextMessage(String viberId) {
        this.sendTextMessage(viberId, "Вы отменили изменение текста сообщения бота", null, null);
    }

    public void sendConfirmChangeBotMessageTextMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите изменение текста сообщения бота", keyboardSource.getConfirmBotMessageKeyboard(), null);
    }

    public void sendSuccessEditBotMessageTextMessage(String viberId) {
        this.sendTextMessage(viberId, "Текст успешно изменен", null, null);
    }

    public void sendMaxButtonsCountReachedMessage(String viberId) {
        this.sendTextMessage(viberId, "Достигнуто максимальное количество кнопок", null, null);
    }

    public void sendMinButtonsCountReachedMessage(String viberId) {
        this.sendTextMessage(viberId, "Список кнопок пуст", null, null);
    }

    public void sendBotMessageNotFoundMessage(String viberId) {
        this.sendTextMessage(viberId, "Такое сообщение не найдено", null, null);
    }

    public void sendUnableSaveBotMessage(String viberId) {
        this.sendTextMessage(viberId, "Не удалось сохранить сообщение, так как это сообщение не найдено или было удалено", null, null);
    }

    public void sendAddNewBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Введите текст сообщения.\nЧтобы выводить имя клиента впишите &{name}, чтобы выводить ссылку на автомобиль впишите &{link}", null, null);
    }

    public void sendConfirmAddNewBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите добавление нового сообщения", keyboardSource.getConfirmBotMessageKeyboard(), null);
    }

    public void sendSuccessfullyAddedNewBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Сообщение успешно добавлено", null, null);
    }

    public void sendCancellerationAddNewBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Вы отменили добавление сообщения.\nСообщение не добавлено", null, null);
    }

    public void sendSelectBotMessageToDelete(String viberId) {
        this.sendTextMessage(viberId, "Введите номер сообщения для удаления", null, null);
    }

    public void sendConfirmDeleteBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите удаление сообщения", keyboardSource.getConfirmBotMessageKeyboard(), null);
    }

    public void sendSuccessfullyDeleteBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Сообщение успешно удалено", null, null);
    }

    public void sendCancellerationDeleteBotMessageMessage(String viberId) {
        this.sendTextMessage(viberId, "Вы отменили удаление сообщения.\nСообщение не удалено", null, null);
    }

    public void sendEnterNewBotMessageSequence(String viberId) {
        this.sendTextMessage(viberId, "Введите новую последовательность сообщений бота.\n(Через запятую в формате 1,2,3)", keyboardSource.getBackKeyboard(), null);
    }

    public void sendSuccessfullyChangedBotMessageSequenceMessage(String viberId) {
        this.sendTextMessage(viberId, "Последовательность сообщений бота успешно изменена", null, null);
    }

    public void sendBotMessageMessage(String viberId, BotMessage botMessage, Client client) {
        this.sendTextMessage(viberId, botMessageService.formateBotMessage(botMessage, client), keyboardSource.generateKeyboard(botMessage), 3);
    }

    public void sendBotMessageDeclineConfirmationMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.editTextsWhichBotSend.declineConfirmation"), null, null);
    }

    public void sendInChatBotUsagePeriodSettingsMessage(String viberId) {
        //TODO: send in chat bot work time period settings details

        this.sendTextMessage(viberId, localeMessageService.getMessage("button.botUsagePeriodMenu.inChat"), null, null);
    }

    public void sendAtNightBotUsagePeriodSettingsMessage(String viberId) {
        //TODO: send at night bot work time period settings details

        this.sendTextMessage(viberId, localeMessageService.getMessage("button.botUsagePeriodMenu.atNight"), null, null);
    }

    // User messages

    public void sendAskClientNameMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("reply.askName"), null, null);
    }

    public void sendAskBrandMessage(String viberId, List<String> brands, Integer page) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.userDialog.askBrand"), keyboardSource.generatePagableKeyboard(brands, page), null);
    }

    public void sendAskModelMessage(String viberId, List<String> models, Integer page) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.userDialog.askModel"), keyboardSource.generatePagableKeyboard(models, page), null);
    }

    public void sendAskYearOfIssueFromMessage(String viberId, List<String> years, Integer page) {
        this.sendTextMessage(viberId, "Выберите минимальный год выпуска", keyboardSource.generatePagableKeyboard(years, page), null);
    }

    public void sendAskYearOfIssueToMessage(String viberId, List<String> years, Integer page) {
        this.sendTextMessage(viberId, "Выберите максимальный год выпуска", keyboardSource.generatePagableKeyboard(years, page), null);
    }

    public void sendAdminMainMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.mainMenu"), keyboardSource.getAdminMainMenuKeyboard(), 6);
    }

    public void sendManagersMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.managers"), keyboardSource.getManagersMenuKeyboard(), null);
    }

    public void sendClientsMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.clients"), keyboardSource.getClientsMenuKeyboard(), null);
    }

    public void sendReportMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.report"), keyboardSource.getReportMenuKeyboard(), null);
    }

    public void sendIntegrationsMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.integrations"), keyboardSource.getIntegrationsMenuKeyboard(), null);
    }

    public void sendSettingsMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.settings"), keyboardSource.getSettingsMenuKeyboard(), null);
    }

    public void sendBotUsagePeriodMenuMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("keyboardMessage.botUsagePeriod"), keyboardSource.getBotUsagePeriodMenuKeyboard(), null);
    }

    public void sendConfirmPostponeMessageMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, "Подтвердите отправку отложенного сообщения", keyboardSource.getConfirmPostponeMessageKeyboard(), null);
    }

    public void sendAddPhotoMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.postponeMessage.uploadPhoto"), keyboardSource.getWithoutPhotoKeyboard(), null);
    }

    //endregion

    //region UserMessageKeyboards

    public void sendIsHaveAnyBenefitsMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), viberService.getBotMessageService().formatBotMessage(1, client), keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendNegativeDialogEndMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("reply.negativeDialogEnd"), keyboardSource.getEndDialogKeyboard(), null);
    }

    public void sendPositiveDialogEndMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("reply.positiveDialogEnd"), keyboardSource.getEndDialogKeyboard(), null);
    }

    public void sendPositiveDialogEndAndPhoneEnteredMessageAndKeyboard(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("reply.positiveDialogEndAndPhoneEntered"), keyboardSource.getEndDialogKeyboard(), null);
    }

    public void sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), viberService.getBotMessageService().formatBotMessage(2, client), keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendDontWorryAboutPricesAndIsLinkOpensMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), viberService.getBotMessageService().formatBotMessage(3, client), keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendWhenArePlanningToBuyCarMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), viberService.getBotMessageService().formatBotMessage(4, client), keyboardSource.getWhenWillBuyCarKeyboard(), null);
    }

    public void sendIsInterestedInSpecificCarVariantsMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), viberService.getBotMessageService().formatBotMessage(5, client), keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendWillAskFewQuestionsRegardingYourCriteriaMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), viberService.getBotMessageService().formatBotMessage(6, client), keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendAskAndEnterPhoneNumberMessageAndKeyboard(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.askAndEnterPhoneNumber"), keyboardSource.getAskAndEnterPhoneNumberKeyboard(), 3);
    }

    public void sendAskYearOfIssueMessage(String viberId) {
        this.sendTextMessage(viberId, localeMessageService.getMessage("message.userDialog.askYearOfIssue"), null, null);
    }

    private SendTextMessageRequest getConversationStartedMessage(String viberId, String message, Keyboard keyboard, Integer minApiVersion) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(message);
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setUserId(viberId);

        return sendTextMessageRequest;
    }

    public void sendFileMessage(String viberId, String mediaUrl, String filename, Long fileSize) {
        SendFileMessageRequest sendFileMessageRequest = new SendFileMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendFileMessageRequest.setMediaUrl(mediaUrl);
        sendFileMessageRequest.setSender(sender);
        sendFileMessageRequest.setFileName(filename);
        sendFileMessageRequest.setSize(fileSize);
        sendFileMessageRequest.setUserId(viberId);

        viberService.sendFileMessage(sendFileMessageRequest);
    }

    public void sendTextMessage(String viberId, String text, Keyboard keyboard, Integer minApiVersion) {
        if (viberId == null || viberId.isEmpty() || viberId.isBlank()) {
            throw new IllegalArgumentException("Viber Id is null");
        }

        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setUserId(viberId);
        sendTextMessageRequest.setText(text);
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setMinApiVersion(minApiVersion);
        sendTextMessageRequest.setKeyboard(keyboard);

        viberService.sendTextMessage(sendTextMessageRequest);
    }

    public void sendPictureMessage(String viberId, String text, String pictureUrl, Keyboard keyboard, Integer minApiVersion) {
        SendPictureMessageRequest sendPictureMessageRequest = new SendPictureMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendPictureMessageRequest.setUserId(viberId);
        sendPictureMessageRequest.setText(text);
        sendPictureMessageRequest.setMediaUrl(pictureUrl);
        sendPictureMessageRequest.setSender(sender);
        sendPictureMessageRequest.setKeyboard(keyboard);
        sendPictureMessageRequest.setMinApiVersion(minApiVersion);

        viberService.sendPictureMessage(sendPictureMessageRequest);
    }
    
    public List<Failed> sendTextMessageToAll(List<String> broadcastList, String text, Keyboard keyboard, Integer minApiVersion) {
        SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendTextMessageRequest.setText(text);
        sendTextMessageRequest.setSender(sender);
        sendTextMessageRequest.setMinApiVersion(minApiVersion);
        sendTextMessageRequest.setBroadcastList(broadcastList);
        sendTextMessageRequest.setKeyboard(keyboard);

        return viberService.broadcastTextMessage(sendTextMessageRequest);
    }

    public List<Failed> sendPictureMessageToAll(List<String> broadcastList, String text, String pictureUrl, Keyboard keyboard, Integer minApiVersion) {
        SendPictureMessageRequest sendPictureMessageRequest = new SendPictureMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendPictureMessageRequest.setText(text);
        sendPictureMessageRequest.setMediaUrl(pictureUrl);
        sendPictureMessageRequest.setSender(sender);
        sendPictureMessageRequest.setBroadcastList(broadcastList);
        sendPictureMessageRequest.setKeyboard(keyboard);
        sendPictureMessageRequest.setMinApiVersion(minApiVersion);

        return viberService.broadcastPictureMessage(sendPictureMessageRequest);
    }

	
}
