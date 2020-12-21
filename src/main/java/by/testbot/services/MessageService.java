package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.bot.KeyboardSource;
import by.testbot.models.BotMessage;
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

    // Other

    public SendTextMessageRequest getConversationStartedMessage(String viberId) {
        return this.getConversationStartedMessage(viberId, localeMessageService.getMessage("message.welcome"), null, null);
    }

    public List<Failed> sendTextMessageToAll(List<String> broadcastList, String message) {
        return this.sendTextMessageToAll(broadcastList, message, null, null);
    }

    public List<Failed> sendPictureMessageToAll(List<String> broadcastList, String message, String pictureUrl) {
        return this.sendPictureMessageToAll(broadcastList, message, pictureUrl, null, null);
    }

    public void sendClientInformationMessage(Manager manager, Client client, String clientAvatar) {
        this.sendPictureMessage(manager.getUser().getViberId(), clientService.getClientInformation(client), clientAvatar, null ,null);
    }

    public void sendManagerInformationMessage(Manager manager, Manager managerForInformation, String managerAvatar) {
        this.sendPictureMessage(manager.getUser().getViberId(), managerService.getManagerInformation(managerForInformation), managerAvatar, null, null);
    }

    // Admin messages

    // Manager start messages

    public void sendYouAreBecomeManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.youAreBecomeManager"), null, null);
    } 

    public void sendAskManagerFirstnameMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.askManagerFirstname"), null, null);
    }

    public void sendAskManagerSurnameMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.askManagerSurname"), null, null);
    } 

    public void sendAskManagerPhoneNumberMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.askManagerPhoneNumber"), keyboardSource.getAskManagerPhoneNumberKeyboard(), 3);
    }

    public void sendManagerWithThisPhoneNumberAlreadyExistsMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managerWithThisPhoneNumberAlreadyExists"), null, null);
    }

    public void sendSuccessfullyFilledManagerProfileMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.successfullyFilledManagerProfile"), null, null);
    }

    // Postpone message messages

    public void sendAddTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.enterText"), null, null);
    }

    public void sendSelectDateAndTimeMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.setDateAndTime"), null, null);
    }

    public void sendSuccessPostponeMessageConfirmationMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.successPostponeMessageConfirmation"), null, null);
    }

    public void sendDeclinePostponeMessageConfirmationMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.declinePostponeMessageConfirmation"), null, null);
    }

    // List of managers

    public void sendListOfManagersIsEmptyMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.listOfManagersIsEmpty"), null, null);
    }

    public void sendListOfManagersMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.listOfManagers"), null, null);
    }

    public void sendListOfManagers(Manager manager, List<Manager> managers) {
        this.sendTextMessage(manager.getUser().getViberId(), managerService.formatManagers(managers), null, null);
    }

    // Add manager messages

    public void sendShareManagerContactMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.shareManagerContact"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendClientNotFoundMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.clientNotFound"), null, null);
    }

    public void sendConfrimAddNewManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.confrimAddNewManager"), keyboardSource.getConfirmManagerKeyboard(), null);
    }

    public void sendSuccessfullyAddedNewManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.successfullyAddedNewManager"), null, null);
    }

    public void sendCancellerationAddManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.cancellerationAddManager"), null, null);
    }

    // Delete manager messages

    public void sendSelectManagerToDelete(Manager manager) {
        // TODO: Rename method
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.shareManagerContact"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendUnableToDeleteSelfMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.unableToDeleteSelf"), null, null);
    }

    public void sendConfrimDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.confrimDeleteManager"), keyboardSource.getConfirmManagerKeyboard(), null);
    }

    public void sendSuccessfullyDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.successfullyDeleteManager"), null, null);
    }

    public void sendUnableToDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.unableToDeleteManager"), null, null);
    }

    public void sendCancellerationDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.cancellerationDeleteManager"), null, null);
    }

    // Change managers privilegies

    public void changeManagerPrivilegiesMessage(String viberId) {
        //TODO: send change manager privilegies details message
    }

    // Clients messages

    public void sendListOfClientsMessage(Manager manager) {
        String filename = excelService.generateExcelAndWriteActualData();
        String mediaUrl = viberService.getFileEndpoint() + filename;
        Long fileSize = fileService.getFileSizeInBytes(filename);

        this.sendFileMessage(manager.getUser().getViberId(), mediaUrl, filename, fileSize);
    }

    public void sendAdditionalOperationsWithClientsMessage(String viberId) {
        //TODO: send additional operations with clients details messages
    }

    // Report messages

    public void sendReportAbountManagersWork(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), "Отчет по работе менеджеров:\n" + managerService.getManagersStatistics(), null, null);
    }

    public void sendGetReportAboutBotWork(String viberId) {
        //TODO: create and send report about bot work
    }

    // Integration Messages

    public void sendAddIntegrationMessage(String viberId) {
        this.sendTextMessage(viberId, "Введите токен для добавления интеграции", null, null);
    }

    public void sendDeleteIntegrationMessage(String viberId) {
        //TODO: send delete integration details message
    }

    public void sendNewIntegrationsMessage(String viberId) {
        //TODO: get and send list of integrations ?????
    }

    // Bot Message Menu 

    public void sendBotMessageButtonsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.botMessageButtonsMenu"), keyboardSource.getBotMessageButtonsMenuKeyabord(), null);
    }

    public void sendBotMessageMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessageMenu"), keyboardSource.getBotMessageMenuKeyabord(), null);
    }

    // Bot Work Time

    public void sendBotWillBeTurnedOffMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botWorkTime.botWillBeTurnedOn"), null, null);
    }

    public void sendBotWillBeTurnedOnMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botWorkTime.botWillBeTurnedOn"), null, null);
    }

    // Update car prices
    
    public void sendSendExcelFileWithPricesMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.updateCarPrices.sendExcelFileWithPrices"), keyboardSource.getBackKeyboard(), null);
    }
    
    public void sendCountOfCarsUpdatedMessage(Manager manager, Integer countOfCarsUpdated, Integer countOfCars) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.updateCarPrices.countOfCarsUpdated", countOfCarsUpdated, countOfCars), null, null);
    }

    // Select Bot Message to Edit message

    public void sendSelectBotMessageToEdit(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.selectBotMessageToEdit"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendBotMessageText(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.showBotMessage") + manager.getBotMessage().getMessage(), null, null);
    }

    public void sendBotMessageNotFoundMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.botMessageNotFound"), null, null);
    }

    public void sendUnableToEditBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.unableToEditBotMessage"), null, null);
    }

    public void sendEnterBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.enterBotMessageText"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendCancellerationChangeBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.cancellerationChangeBotMessageText"), null, null);
    }

    public void sendConfirmChangeBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.confirmChangeBotEditMessage"), keyboardSource.getConfirmBotMessageKeyboard(), null);
    }

    public void sendSuccessEditBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.successEditBotMessageText"), null, null);
    }

    // Button edit

    public void sendAskNumberOfButtonToDeleteMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.askNumberOfButtonToDelete"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendListOfBotMessageButtons(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.listOfBotMessageButtons") + buttonService.formatButtons(buttonService.getAllByBotMessage(manager.getBotMessage())), null, null);
    }

    public void sendAddBotMessageButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.addBotMessageButton"), null, null);
    }

    public void sendSuccessfullyAddedButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.successfullyAddedButton"), null, null);
    }

    public void sendConfirmAddNewBotMessageButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.confirmAddNewBotMessageButton"), keyboardSource.getConfirmBotMessageButtonKeyboard(), null);
    }
    
    public void sendUnableToAddNewButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.unableToAddNewButton"), null, null);
    }

    public void sendConfirmDeleteBotMessageButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.confirmDeleteBotMessageButton"), keyboardSource.getConfirmBotMessageButtonKeyboard(), null);
    }

    public void sendUnableToDeleteButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.unableToDeleteButton"), null, null);
    }

    public void sendSuccessfullyDeletedButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.successfullyDeletedButton"), null, null);
    }

    public void sendCancellerationDeleteButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.cancellerationDeleteButton"), null, null);
    }

    public void sendCancellerationAddNewButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.cancellerationAddNewButton"), null, null);
    }

    public void sendSelectAnswerTypeMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.selectAnswerType"), keyboardSource.getAnswerTypeKeyboard(), null);
    }

    public void sendIsButtonEndsDialogueMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.isButtonEndsDialogue"), keyboardSource.getYesNoKeyboard(), null);
    }

    public void sendListOfBotMessages(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.listOfBotMessages") + viberService.getBotMessageService().getPrettyFormatedAllBotMessages(), null, null);
    }

    public void sendListOfBotMessagesIsEmptyMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.listOfBotMessagesIsEmpty"), null, null);
    }

    public void sendCurrentBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.currentBotMessageText") + manager.getBotMessage().getMessage(), null, null);
    }

    public void sendMaxButtonsCountReachedMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.maxButtonsCountReached"), null, null);
    }

    public void sendButtonsListIsEmptyMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.buttonsListIsEmpty"), null, null);
    }

    public void sendAddNewBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessageMenu.addNewBotMessage"), null, null);
    }

    public void sendConfirmAddNewBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.confirmAddNewBotMessage"), keyboardSource.getConfirmBotMessageKeyboard(), null);
    }

    public void sendSuccessfullyAddedNewBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.successfullyAddedNewBotMessage"), null, null);
    }

    public void sendCancellerationAddNewBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.cancellerationAddNewBotMessage"), null, null);
    }

    public void sendSelectBotMessageToDeleteMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.selectBotMessageToDelete"), null, null);
    }

    public void sendConfirmDeleteBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.confirmDeleteBotMessage"), keyboardSource.getConfirmBotMessageKeyboard(), null);
    }

    public void sendSuccessfullyDeleteBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.successfullyDeleteBotMessage"), null, null);
    }

    public void sendCancellerationDeleteBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.cancellerationDeleteBotMessage"), null, null);
    }

    public void sendEnterNewBotMessageSequenceMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.enterNewBotMessageSequence"), keyboardSource.getBackKeyboard(), null);
    }

    public void sendSuccessfullyChangedBotMessageSequenceMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.successfullyChangedBotMessageSequence"), null, null);
    }

    

    public void sendBotMessageMessage(Client client, BotMessage botMessage) {
        this.sendTextMessage(client.getUser().getViberId(), botMessageService.formateBotMessage(botMessage, client), keyboardSource.generateKeyboard(botMessage), 3);
    }

    public void sendInChatBotUsagePeriodSettingsMessage(Manager manager) {
        //TODO: send in chat bot work time period settings details

        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("button.botUsagePeriodMenu.inChat"), null, null);
    }

    public void sendAtNightBotUsagePeriodSettingsMessage(Manager manager) {
        //TODO: send at night bot work time period settings details

        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("button.botUsagePeriodMenu.atNight"), null, null);
    }

    // User messages

    public void sendAskClientNameMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.askName"), null, null);
    }

    public void sendAskBrandMessage(Client client, List<String> brands, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), botMessageService.formateBotMessage(localeMessageService.getMessage("message.userDialog.askBrand"), client), keyboardSource.generatePagableKeyboard(brands, page), null);
    }

    public void sendAskModelMessage(Client client, List<String> models, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.askModel"), keyboardSource.generatePagableKeyboard(models, page), null);
    }

    public void sendAskYearOfIssueFromMessage(Client client, List<String> years, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.askYearOfIssueFrom"), keyboardSource.generatePagableKeyboard(years, page), null);
    }

    public void sendAskYearOfIssueToMessage(Client client, List<String> years, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.askYearOfIssueTo"), keyboardSource.generatePagableKeyboard(years, page), null);
    }

    public void sendAdminMainMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.mainMenu"), keyboardSource.getAdminMainMenuKeyboard(), 6);
    }

    public void sendManagersMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.managers"), keyboardSource.getManagersMenuKeyboard(), null);
    }

    public void sendClientsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.clients"), keyboardSource.getClientsMenuKeyboard(), null);
    }

    public void sendReportMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.report"), keyboardSource.getReportMenuKeyboard(), null);
    }

    public void sendIntegrationsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.integrations"), keyboardSource.getIntegrationsMenuKeyboard(), null);
    }

    public void sendSettingsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.settings"), keyboardSource.getSettingsMenuKeyboard(), null);
    }

    public void sendBotWorkKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.botWork"), keyboardSource.getBotUsagePeriodMenuKeyboard(), null);
    }

    public void sendConfirmPostponeMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.confirmPostponeMessage"), keyboardSource.getConfirmPostponeMessageKeyboard(), null);
    }

    public void sendAddPhotoMessageAndKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.uploadPhoto"), keyboardSource.getWithoutPhotoKeyboard(), null);
    }

    //endregion

    //region UserMessageKeyboards

    public void sendPhoneNumberSuccessfullyEnteredMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.phoneEntered"), null, null);
    }

    public void sendNegativeDialogEndMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.negativeDialogEnd"), keyboardSource.getEndDialogKeyboard(), null);
    }

    public void sendPositiveDialogEndMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.positiveDialogEnd"), keyboardSource.getEndDialogKeyboard(), null);
    }

    public void sendPositiveDialogEndAndPhoneEnteredMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.positiveDialogEndAndPhoneEntered"), keyboardSource.getEndDialogKeyboard(), null);
    }

    public void sendAskAndEnterPhoneNumberMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.askAndEnterPhoneNumber"), keyboardSource.getAskAndEnterPhoneNumberKeyboard(), 3);
    }

    public void sendClientWithThisPhoneNumberAlreadyExistsMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.clientWithThisPhoneNumberAlreadyExists"), null, null);
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
