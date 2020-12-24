package by.autocapital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.autocapital.bot.KeyboardSource;
import by.autocapital.models.BotMessage;
import by.autocapital.models.Client;
import by.autocapital.models.Manager;
import by.autocapital.models.Statistics;
import by.autocapital.models.viber.Failed;
import by.autocapital.models.viber.Keyboard;
import by.autocapital.models.viber.Sender;
import by.autocapital.payload.requests.message.SendFileMessageRequest;
import by.autocapital.payload.requests.message.SendPictureMessageRequest;
import by.autocapital.payload.requests.message.SendTextMessageRequest;
import by.autocapital.services.file.ExcelService;
import by.autocapital.services.file.FileService;
import by.autocapital.services.other.LocaleMessageService;
import by.autocapital.services.viber.ViberService;

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
    private StatisticsService statisticsService;

    @Autowired
    private LocaleMessageService localeMessageService;

    final static Integer MIN_API_VERSION = 6;

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
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.askManagerPhoneNumber"), keyboardSource.getSharePhoneKeyboard(), MIN_API_VERSION);
    }

    public void sendManagerWithThisPhoneNumberAlreadyExistsMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managerWithThisPhoneNumberAlreadyExists"), null, null);
    }

    public void sendSuccessfullyFilledManagerProfileMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.successfullyFilledManagerProfile"), null, null);
    }

    // Postpone message messages

    public void sendAddTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.enterText"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendSelectDateAndTimeMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.setDateAndTime"), null, null);
    }

    public void sendConfirmedPostponeMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.successPostponeMessageConfirmation"), null, null);
    }

    public void sendRejectedPostponeMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.declinePostponeMessageConfirmation"), null, null);
    }

    public void sendConfirmPostponeMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.confirmPostponeMessage"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendAddPhotoMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.postponeMessage.uploadPhoto"), keyboardSource.getWithoutPhotoKeyboard(), MIN_API_VERSION);
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
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.shareManagerContact"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendClientNotFoundMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.clientNotFound"), null, null);
    }

    public void sendConfrimAddNewManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.confrimAddNewManager"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendConfirmedAddNewManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.successfullyAddedNewManager"), null, null);
    }

    public void sendRejectedAddNewManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.addManager.cancellerationAddManager"), null, null);
    }

    // Delete manager messages

    public void sendSelectManagerToDelete(Manager manager) {
        // TODO: Rename method
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.shareManagerContact"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendUnableToDeleteSelfMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.unableToDeleteSelf"), null, null);
    }

    public void sendConfrimDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.confrimDeleteManager"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendConfirmedDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.successfullyDeleteManager"), null, null);
    }

    public void sendUnableToDeleteManagerMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.managers.deleteManager.unableToDeleteManager"), null, null);
    }

    public void sendRejectedDeleteManagerMessage(Manager manager) {
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

        this.sendFileMessage(manager.getUser().getViberId(), mediaUrl, filename, fileSize, null, null);
    }

    public void sendAdditionalOperationsWithClientsMessage(String viberId) {
        //TODO: send additional operations with clients details messages
    }

    // Report messages

    public void sendReportAbountManagersWork(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.report.reportAboutManagersWork", managerService.getManagersStatistics()), null, null);
    }

    public void sendReportAboutBotWork(Manager manager) {
        Statistics yesterdayStatistics = statisticsService.getYesterdayStatistics();

        if (yesterdayStatistics == null) {
            this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.report.reportAboutBotWorkNotFound"), null, null);
        }
        else {
            this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.report.reportAboutBotWork", yesterdayStatistics.getDate().toString(),
                                                                                                     yesterdayStatistics.getMidnightToEightOclickCount(), 
                                                                                                     yesterdayStatistics.getEightOclockToSixteenOclockCount(),
                                                                                                     yesterdayStatistics.getSixteenOclickToMidnightCount()), null, null);
        }
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
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.botMessageButtonsMenu"), keyboardSource.getBotMessageButtonsMenuKeyabord(), MIN_API_VERSION);
    }

    public void sendBotMessageMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessageMenu"), keyboardSource.getBotMessageMenuKeyabord(), MIN_API_VERSION);
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
        String filename = excelService.generateCarExcel();
        String mediaUrl = viberService.getFileEndpoint() + filename;
        Long fileSize = fileService.getFileSizeInBytes(filename);

        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.updateCarPrices.sendExcelFileWithPrices"), null, null);
        this.sendFileMessage(manager.getUser().getViberId(), mediaUrl, filename, fileSize, keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }
    
    public void sendCountOfCarsUpdatedMessage(Manager manager, Integer countOfCarsUpdated, Integer countOfCars) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.updateCarPrices.countOfCarsUpdated", countOfCarsUpdated, countOfCars), null, null);
    }

    // Select Bot Message to Edit message

    public void sendSelectBotMessageToEdit(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.selectBotMessageToEdit"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendBotMessageText(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.showBotMessage", manager.getBotMessage().getMessage()), null, null);
    }

    public void sendBotMessageNotFoundMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.botMessageNotFound"), null, null);
    }

    public void sendUnableToEditBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.unableToEditBotMessage"), null, null);
    }

    public void sendEnterBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.enterBotMessageText"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendRejectedEditBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.cancellerationChangeBotMessageText"), null, null);
    }

    public void sendConfirmChangeBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.confirmChangeBotEditMessage"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendConfirmedEditBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.successEditBotMessageText"), null, null);
    }

    // Button edit

    public void sendAskNumberOfButtonToDeleteMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.askNumberOfButtonToDelete"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendListOfBotMessageButtons(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.listOfBotMessageButtons", buttonService.formatButtons(buttonService.getAllByBotMessage(manager.getBotMessage()))), null, null);
    }

    public void sendAddBotMessageButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.addBotMessageButton"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendConfirmedAddButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.successfullyAddedButton"), null, null);
    }

    public void sendConfirmAddNewBotMessageButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.confirmAddNewBotMessageButton"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }
    
    public void sendUnableToAddNewButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.unableToAddNewButton"), null, null);
    }

    public void sendConfirmDeleteBotMessageButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.confirmDeleteBotMessageButton"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendUnableToDeleteButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.unableToDeleteButton"), null, null);
    }

    public void sendConfirmedDeleteButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.successfullyDeletedButton"), null, null);
    }

    public void sendRejectedDeleteButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.cancellerationDeleteButton"), null, null);
    }

    public void sendRejectedAddNewButtonMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.cancellerationAddNewButton"), null, null);
    }

    public void sendSelectAnswerTypeMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.selectAnswerType"), keyboardSource.getAnswerTypeKeyboard(), MIN_API_VERSION);
    }

    public void sendIsButtonEndsDialogueMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.isButtonEndsDialogue"), keyboardSource.getYesNoKeyboard(), MIN_API_VERSION);
    }

    public void sendListOfBotMessages(Manager manager) {
        if (!botMessageService.isListOfBotMessagesEmpty()) {
            this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.listOfBotMessages", botMessageService.getPrettyFormatedAllBotMessages()), null, null);
        }
        else {
            this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.listOfBotMessagesIsEmpty"), null, null);
        }
    }

    public void sendCurrentBotMessageTextMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.editBotMessage.currentBotMessageText", manager.getBotMessage().getMessage()), null, null);
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
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.confirmAddNewBotMessage"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendConfirmedAddNewBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.successfullyAddedNewBotMessage"), null, null);
    }

    public void sendRejectedAddNewBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.cancellerationAddNewBotMessage"), null, null);
    }

    public void sendSelectBotMessageToDeleteMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.selectBotMessageToDelete"), null, null);
    }

    public void sendConfirmDeleteBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.confirmDeleteBotMessage"), keyboardSource.getConfirmKeyboard(), MIN_API_VERSION);
    }

    public void sendConfirmedDeleteBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.successfullyDeleteBotMessage"), null, null);
    }

    public void sendRejectedDeleteBotMessageMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.cancellerationDeleteBotMessage"), null, null);
    }

    public void sendEnterNewBotMessageSequenceMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.enterNewBotMessageSequence"), keyboardSource.getBackKeyboard(), MIN_API_VERSION);
    }

    public void sendSuccessfullyChangedBotMessageSequenceMessage(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("message.botMessage.successfullyChangedBotMessageSequence"), null, null);
    }

    

    public void sendBotMessageMessage(Client client, BotMessage botMessage) {
        this.sendTextMessage(client.getUser().getViberId(), botMessageService.formateBotMessage(botMessage, client), keyboardSource.generateKeyboard(botMessage), MIN_API_VERSION);
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
        this.sendTextMessage(client.getUser().getViberId(), botMessageService.formateBotMessage(localeMessageService.getMessage("message.userDialog.askBrand"), client), keyboardSource.generatePagableKeyboard(brands, page), MIN_API_VERSION);
    }

    public void sendAskModelMessage(Client client, List<String> models, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.askModel"), keyboardSource.generatePagableKeyboard(models, page), MIN_API_VERSION);
    }

    public void sendAskYearOfIssueFromMessage(Client client, List<String> years, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.askYearOfIssueFrom"), keyboardSource.generatePagableKeyboard(years, page), MIN_API_VERSION);
    }

    public void sendAskYearOfIssueToMessage(Client client, List<String> years, Integer page) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("message.userDialog.askYearOfIssueTo"), keyboardSource.generatePagableKeyboard(years, page), MIN_API_VERSION);
    }

    public void sendAdminMainMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.mainMenu"), keyboardSource.getAdminMainMenuKeyboard(), MIN_API_VERSION);
    }

    public void sendManagersMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.managers"), keyboardSource.getManagersMenuKeyboard(), MIN_API_VERSION);
    }

    public void sendClientsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.clients"), keyboardSource.getClientsMenuKeyboard(), MIN_API_VERSION);
    }

    public void sendReportMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.report"), keyboardSource.getReportMenuKeyboard(), MIN_API_VERSION);
    }

    public void sendIntegrationsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.integrations"), keyboardSource.getIntegrationsMenuKeyboard(), MIN_API_VERSION);
    }

    public void sendSettingsMenuKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.settings"), keyboardSource.getSettingsMenuKeyboard(), MIN_API_VERSION);
    }

    public void sendBotWorkKeyboard(Manager manager) {
        this.sendTextMessage(manager.getUser().getViberId(), localeMessageService.getMessage("keyboardMessage.botWork"), keyboardSource.getBotUsagePeriodMenuKeyboard(), MIN_API_VERSION);
    }

    //endregion

    //region UserMessageKeyboards

    public void sendPhoneNumberSuccessfullyEnteredMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.phoneEntered"), null, null);
    }

    public void sendNegativeDialogEndMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.negativeDialogEnd"), keyboardSource.getEndDialogKeyboard(), MIN_API_VERSION);
    }

    public void sendPositiveDialogEndMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.positiveDialogEnd"), keyboardSource.getEndDialogKeyboard(), MIN_API_VERSION);
    }

    public void sendPositiveDialogEndAndPhoneEnteredMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.positiveDialogEndAndPhoneEntered"), keyboardSource.getEndDialogKeyboard(), MIN_API_VERSION);
    }

    public void sendAskAndEnterPhoneNumberMessage(Client client) {
        this.sendTextMessage(client.getUser().getViberId(), localeMessageService.getMessage("reply.askAndEnterPhoneNumber"), keyboardSource.getSharePhoneKeyboard(), MIN_API_VERSION);
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

    public void sendFileMessage(String viberId, String mediaUrl, String filename, Long fileSize, Keyboard keyboard, Integer minApiVersion) {
        SendFileMessageRequest sendFileMessageRequest = new SendFileMessageRequest();
        Sender sender = new Sender();

        sender.setName(viberService.getSenderName());

        sendFileMessageRequest.setMediaUrl(mediaUrl);
        sendFileMessageRequest.setSender(sender);
        sendFileMessageRequest.setFileName(filename);
        sendFileMessageRequest.setSize(fileSize);
        sendFileMessageRequest.setKeyboard(keyboard);
        sendFileMessageRequest.setMinApiVersion(minApiVersion);
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
