package by.autocapital.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import by.autocapital.config.EnvironmentConfig;
import by.autocapital.models.Answer;
import by.autocapital.models.BotMessage;
import by.autocapital.models.Button;
import by.autocapital.models.Car;
import by.autocapital.models.Client;
import by.autocapital.models.Dialogue;
import by.autocapital.models.Manager;
import by.autocapital.models.PostponeMessage;
import by.autocapital.models.User;
import by.autocapital.models.enums.AnswerType;
import by.autocapital.models.enums.Role;
import by.autocapital.services.AnswerService;
import by.autocapital.services.BotMessageService;
import by.autocapital.services.ButtonService;
import by.autocapital.services.CarService;
import by.autocapital.services.ClientService;
import by.autocapital.services.DialogueService;
import by.autocapital.services.ManagerService;
import by.autocapital.services.MessageService;
import by.autocapital.services.PostponeMessageService;
import by.autocapital.services.UserService;
import by.autocapital.services.file.ExcelService;
import by.autocapital.services.file.FileService;
import by.autocapital.services.other.ConfigService;
import by.autocapital.utils.Utils;
import by.autocapital.validation.ValidationResult;
import by.autocapital.validation.Validator;
import lombok.Setter;

public enum BotState {

    //#region Manager Fill Profile

    AskManagerFirstname(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendYouAreBecomeManagerMessage(botContext.getUser().getManager());
            messageService.sendAskManagerFirstnameMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            botContext.getUser().getManager().setFirstname(botContext.getMessage().getText());
            nextState = AskManagerSurname;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AskManagerSurname(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAskManagerSurnameMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            botContext.getUser().getManager().setSurname(botContext.getMessage().getText());

            nextState = botContext.getUser().getManager().getPhoneNumber() == null ? AskManagerPhoneNumber : MainMenu; 
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AskManagerPhoneNumber(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAskManagerPhoneNumberMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            ValidationResult validationResult = Validator.validatePhoneNumber(text);
            
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AskManagerPhoneNumber;
                return;
            }

            if (clientService.checkPhoneNumber(text) || managerService.checkPhoneNumber(text)) {
                messageService.sendManagerWithThisPhoneNumberAlreadyExistsMessage(botContext.getUser().getManager());
                nextState = AskManagerPhoneNumber;
                return;
            }

            botContext.getUser().getManager().setPhoneNumber(text);
            messageService.sendSuccessfullyFilledManagerProfileMessage(botContext.getUser().getManager());
            nextState = MainMenu;
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            botContext.getUser().getManager().setPhoneNumber(botContext.getMessage().getContact().getPhoneNumber());
            messageService.sendSuccessfullyFilledManagerProfileMessage(botContext.getUser().getManager());
            nextState = MainMenu;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    //#endregion

    //#region Main Menu

    MainMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAdminMainMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.adminMainMenu.postponeMessage":
                    nextState = AddPostponeMessageText;
                    break;
                case "callback.adminMainMenu.managers":
                    nextState = ManagersMenu;
                    break;
                case "callback.adminMainMenu.clients":
                    nextState = ClientsMenu;
                    break;
                case "callback.adminMainMenu.report":
                    nextState = Report;
                    break;
                case "callback.adminMainMenu.integrations":
                    nextState = Integrations;
                    break;
                case "callback.adminMainMenu.settings":
                    nextState = Settings;
                    break;
                default:
                    nextState = MainMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ManagersMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendManagersMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.managersMenu.list":
                    nextState = ListOfManagers;
                    break;
                case "callback.managersMenu.add":
                    nextState = AddManager;
                    break;
                case "callback.managersMenu.delete":
                    nextState = DeleteManager;
                    break;
                case "callback.managersMenu.changePrivilegies":
                    nextState = ChangeManagerPrivilegies;
                    break;
                case "callback.managersMenu.back":
                    nextState = MainMenu;
                    break;
                default:
                    nextState = ManagersMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ClientsMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendClientsMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.clientsMenu.list":
                    nextState = GetListOfClients;
                    break;
                case "callback.clientsMenu.additionalActions":
                    nextState = AdditionalOperationsWithClients;
                    break;
                case "callback.clientsMenu.back":
                    nextState = MainMenu;
                    break;
                default:
                    nextState = ClientsMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    Report(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendReportMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.reportMenu.aboutManagersWork":
                    nextState = ReportAboutManagersWork;
                    break;
                case "callback.reportMenu.aboutBotWork":
                    nextState = ReportAboutBotWork;
                    break;
                case "callback.reportMenu.back":
                    nextState = MainMenu;
                    break;
                default:
                    nextState = Report;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    Integrations(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendIntegrationsMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.integrationsMenu.addOrDelete":
                    nextState = AddOrDeleteIntegration;
                    break;
                // case "Удаление интергации":
                // botState = DeleteIntegration;
                // break;
                case "callback.integrationsMenu.new":
                    nextState = NewIntegrations;
                    break;
                case "callback.integrationsMenu.back":
                    nextState = MainMenu;
                    break;
                default:
                    nextState = Integrations;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    Settings(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendSettingsMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.settings.editTextsWhichBotSend":
                    nextState = BotMessageMenu;
                    break;
                case "callback.settings.botWork":
                    nextState = BotWork;
                    break;
                case "callback.settings.updateCars":
                    nextState = UpdateCarPrices;
                    break;
                case "callback.settings.back":
                    nextState = MainMenu;
                    break;
                default:
                    nextState = Settings;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    //#endregion

    //#region Settings

    //#region BotMessages

    BotMessageMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendBotMessageMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.botMessageMenu.list":
                    nextState = ListOfBotMessages;
                    break;
                case "callback.botMessageMenu.edit":
                    nextState = SelectBotMessageToEdit;
                    break;
                case "callback.botMessageMenu.add": 
                    nextState = AddNewBotMessage;
                    break;
                case "callback.botMessageMenu.delete":
                    nextState = SelectBotMessageToDelete;
                    break;
                case "callback.botMessageMenu.editOrder":
                    nextState = ChangeBotMessagesOrder;
                    break;
                case "callback.botMessageMenu.back":
                    nextState = Settings;
                    break;
                default:
                    nextState = BotMessageMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ListOfBotMessages(false) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendListOfBotMessages(botContext.getUser().getManager());
            nextState = BotMessageMenu;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AddNewBotMessage(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAddNewBotMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            BotMessage newBotMessage = BotMessage.builder()
                                                 .message(botContext.getMessage().getText())
                                                 .isAdded(true)
                                                 .lastUpdate(new Date().getTime())
                                                 .build();

            botMessageService.save(newBotMessage);
            botContext.getUser().getManager().setBotMessage(newBotMessage);

            nextState = ConfirmAddNewBotMessage;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmAddNewBotMessage(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendConfirmAddNewBotMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch (botContext.getMessage().getText()) {
                case "callback.confirm":
                    botMessageService.addNewMessage(botContext.getUser().getManager().getBotMessage());
                    
                    botContext.getUser().getManager().setBotMessage(null);
                    messageService.sendConfirmedAddNewBotMessageMessage(botContext.getUser().getManager());
                    nextState = BotMessageMenu;
                    break;
                case "callback.reject":
                    botMessageService.delete(botContext.getUser().getManager().getBotMessage());
                    messageService.sendRejectedAddNewBotMessageMessage(botContext.getUser().getManager());
                    nextState = BotMessageMenu;
                    break;
                default:
                    nextState = ConfirmAddNewBotMessage;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    SelectBotMessageToDelete(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendListOfBotMessages(botContext.getUser().getManager());
            messageService.sendSelectBotMessageToDeleteMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();
            ValidationResult validationResult = null;
            List<BotMessage> botMessages = null;
            Integer messagePosition = 0;

            if (text.equals("callback.back")) {
                nextState = BotMessageMenu;
                return;
            }

            validationResult = Validator.validateNumberOfMessage(text);
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = SelectBotMessageToDelete;
                return;
            }

            botMessages = botMessageService.getAllAdded();
            messagePosition = Integer.parseInt(text);

            if (messagePosition < 1 || messagePosition > botMessages.size()) {
                messageService.sendBotMessageNotFoundMessage(botContext.getUser().getManager());
                nextState = SelectBotMessageToDelete;
            }
            else {
                BotMessage botMessage = botMessages.get(messagePosition - 1);
                if (botMessage != null) {
                    botContext.getUser().getManager().setBotMessage(botMessage);

                    nextState = ConfirmDeleteBotMessage;
                }
                else {
                    nextState = SelectBotMessageToDelete;
                }
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmDeleteBotMessage(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendConfirmDeleteBotMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch (botContext.getMessage().getText()) {
                case "callback.confirm":
                    botMessageService.deleteBotMessage(botContext.getUser().getManager().getBotMessage());
                    messageService.sendConfirmedDeleteBotMessageMessage(botContext.getUser().getManager());
                    nextState = BotMessageMenu;
                    break;
                case "callback.reject":
                    botContext.getUser().getManager().setBotMessage(null);
                    messageService.sendRejectedDeleteBotMessageMessage(botContext.getUser().getManager());
                    nextState = BotMessageMenu;
                    break;
                default:
                    nextState = ConfirmDeleteBotMessage;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    EditBotMessageText(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendCurrentBotMessageTextMessage(botContext.getUser().getManager());
            messageService.sendEnterBotMessageTextMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {
                nextState = EditBotMessageMenu;
            }
            else {
                botContext.getUser().getManager().setBotMessageText(text);
                nextState = ConfirmChangeBotMessageText;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmChangeBotMessageText(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendConfirmChangeBotMessageTextMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch(botContext.getMessage().getText()) {
                case "callback.confirm":
                    BotMessage botMessage = botContext.getUser().getManager().getBotMessage();

                    if (botMessage != null) {
                        botMessage.setMessage(botContext.getUser().getManager().getBotMessageText());

                        botContext.getUser().getManager().setBotMessageText(null);

                        botMessageService.save(botMessage);
                        messageService.sendConfirmedEditBotMessageTextMessage(botContext.getUser().getManager());
                    }
                    else {
                        botContext.getUser().getManager().setBotMessageText(null);

                        messageService.sendUnableToEditBotMessageMessage(botContext.getUser().getManager());
                    }

                    nextState = EditBotMessageMenu;
                    break;
                case "callback.reject":
                    botContext.getUser().getManager().setBotMessageText(null);

                    messageService.sendRejectedEditBotMessageTextMessage(botContext.getUser().getManager());
                    nextState = EditBotMessageMenu;
                    break;
                default:
                    nextState = ConfirmChangeBotMessageText;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ChangeBotMessagesOrder(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "User is null");

            messageService.sendEnterNewBotMessageSequenceMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {
                nextState = BotMessageMenu;
                return;
            }

            List<Integer> order = botMessageService.parseBotMessageSequence(text);
            List<BotMessage> oldBotMessageSequence = botMessageService.getAllAdded();
            ValidationResult validationResult = null;

            validationResult = Validator.validateBotMessageSequence(text);
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                
                nextState = ChangeBotMessagesOrder;
                return;
            }
            validationResult = Validator.validateBotMessageSequence(order, oldBotMessageSequence);
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                
                nextState = ChangeBotMessagesOrder;
                return;
            }

            botMessageService.changeBotMessagesOrder(order, oldBotMessageSequence);
            messageService.sendSuccessfullyChangedBotMessageSequenceMessage(botContext.getUser().getManager());
            nextState = BotMessageMenu;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    SelectBotMessageToEdit(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendListOfBotMessages(botContext.getUser().getManager());
            messageService.sendSelectBotMessageToEdit(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {
                nextState = BotMessageMenu;
                return;
            }

            ValidationResult validationResult = Validator.validateNumberOfMessage(text);
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = SelectBotMessageToEdit;
                return;
            }

            List<BotMessage> botMessages = botMessageService.getAllAdded();
            Integer messageIndex = Integer.parseInt(text);

            if (messageIndex < 1 || messageIndex > botMessages.size()) {
                messageService.sendBotMessageNotFoundMessage(botContext.getUser().getManager());
                nextState = SelectBotMessageToEdit;
            }
            else {
                BotMessage botMessage = botMessages.get(messageIndex - 1);

                if (botMessage != null) {
                    botContext.getUser().getManager().setBotMessage(botMessage);
                    nextState = EditBotMessageMenu;
                }
                else {
                    nextState = SelectBotMessageToEdit;
                }
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    EditBotMessageMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "User is null");

            messageService.sendBotMessageButtonsMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            if (botContext.getUser().getManager().getBotMessage() == null) {
                messageService.sendUnableToEditBotMessageMessage(botContext.getUser().getManager());
                nextState = BotMessageMenu;
                return;
            }

            switch(botContext.getMessage().getText()) {
                case "callback.botMessageButtonsMenu.showMessageButton":
                    messageService.sendBotMessageText(botContext.getUser().getManager());
                    nextState=EditBotMessageMenu;
                    break;
                case "callback.botMessageButtonsMenu.editTextButton":
                    nextState = EditBotMessageText;
                    break;
                case "callback.botMessageButtonsMenu.listButton": 
                    nextState = ListOfBotMessageButtons;
                    break;
                case "callback.botMessageButtonsMenu.deleteButton":
                    if (botContext.getUser().getManager().getBotMessage().getButtons().size() < 1) {
                        messageService.sendButtonsListIsEmptyMessage(botContext.getUser().getManager());
                        nextState = EditBotMessageMenu;
                    }
                    else {
                        nextState = DeleteBotMessageButton;
                    }
                    break;
                case "callback.botMessageButtonsMenu.addButton": 
                    if (botContext.getUser().getManager().getBotMessage().getButtons().size() > ButtonService.MAX_BUTTONS_COUNT) {
                        messageService.sendMaxButtonsCountReachedMessage(botContext.getUser().getManager());
                        nextState = EditBotMessageMenu;
                    }
                    else {
                        nextState = AddBotMessageButton;
                    }
                    break;
                case "callback.botMessageButtonsMenu.back":
                    botContext.getUser().getManager().setBotMessage(null);
                    nextState = BotMessageMenu;
                    break;
                default: 
                    nextState = EditBotMessageMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ListOfBotMessageButtons(false) {
        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getUser().getManager().getBotMessage(), "Bot message is null");

            messageService.sendListOfBotMessageButtons(botContext.getUser().getManager());
        }

        @Override
        public BotState nextState() {
            return EditBotMessageMenu;
        }
    },

    DeleteBotMessageButton(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getUser().getManager().getBotMessage(), "Bot message is null");

            messageService.sendListOfBotMessageButtons(botContext.getUser().getManager());
            messageService.sendAskNumberOfButtonToDeleteMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();
            ValidationResult validationResult = null;
            List<Button> buttons = null;
            Integer buttonPosition = 0;

            if (text.equals("callback.back")) {
                nextState = EditBotMessageMenu;
                return;
            }

            validationResult = Validator.validateNumberOfButton(text);
            if (!validationResult.getIsValid()) {
                if (botContext.getUser() == null) {
                    throw new NullPointerException("User is null");
                }

                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = DeleteBotMessageButton;
                return;
            }

            buttons = buttonService.getAllByBotMessage(botContext.getUser().getManager().getBotMessage());
            buttonPosition = Integer.parseInt(text);

            if (buttonPosition < 1 || buttonPosition > buttons.size()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), "Такой кнопки не существует", null, null);
                nextState = DeleteBotMessageButton;
            }
            else {
                botContext.getUser().getManager().setButton(buttons.get(buttonPosition - 1));
                nextState = ConfirmDeleteBotMessageButton;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmDeleteBotMessageButton(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendConfirmDeleteBotMessageButtonMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch(botContext.getMessage().getText()) {
                case "callback.confirm":
                    Button button = botContext.getUser().getManager().getButton();

                    if (button != null) {
                        botContext.getUser().getManager().setButton(null);

                        buttonService.delete(button);
                        messageService.sendConfirmedDeleteButtonMessage(botContext.getUser().getManager());
                    }
                    else {
                        messageService.sendUnableToDeleteButtonMessage(botContext.getUser().getManager());
                    }
                    
                    nextState = EditBotMessageMenu;
                    break;
                case "callback.reject":
                    botContext.getUser().getManager().setButton(null);

                    messageService.sendRejectedDeleteButtonMessage(botContext.getUser().getManager());
                    nextState = EditBotMessageMenu;
                    break;
                default:
                    nextState = ConfirmDeleteBotMessageButton;
                    break;
            }   
            
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AddBotMessageButton(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAddBotMessageButtonMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("callback.back")) {
                nextState = EditBotMessageMenu;
                return;
            }

            Button newButton = Button.builder()
                                     .text(botContext.getMessage().getText())
                                     .callbackData(buttonService.generateCallbackData())
                                     .build();
            
            buttonService.save(newButton);
            botContext.getUser().getManager().setButton(newButton);

            nextState = SetAnswerTypeToNewButton;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    SetAnswerTypeToNewButton(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendSelectAnswerTypeMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            Button button = botContext.getUser().getManager().getButton();

            switch(botContext.getMessage().getText()) {
                case "callback.botMessageMenu.selectAnswerType.negative": 
                    button.setAnswerType(AnswerType.NEGATIVE);

                    buttonService.save(button);
                    nextState = SelectIsButtonEndsDialogue;
                    break;
                case "callback.botMessageMenu.selectAnswerType.positive":
                    button.setAnswerType(AnswerType.POSITIVE);

                    buttonService.save(button);
                    nextState = SelectIsButtonEndsDialogue;
                    break;
                default: 
                    nextState = SetAnswerTypeToNewButton;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    SelectIsButtonEndsDialogue(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendIsButtonEndsDialogueMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            Button button = botContext.getUser().getManager().getButton();

            switch (botContext.getMessage().getText()) {
                case "Да": 
                    button.setDialogueEnds(false);

                    buttonService.save(button);
                    nextState = ConfirmAddNewBotMessageButton;
                    break;
                case "Нет": 
                    button.setDialogueEnds(true);

                    buttonService.save(button);
                    nextState = ConfirmAddNewBotMessageButton;
                    break;
                default:
                    nextState = SelectIsButtonEndsDialogue;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmAddNewBotMessageButton(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendConfirmAddNewBotMessageButtonMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            Button button = botContext.getUser().getManager().getButton();

            switch (botContext.getMessage().getText()) {
                case "callback.confirm":
                    BotMessage botMessage = botContext.getUser().getManager().getBotMessage();

                    botContext.getUser().getManager().setButton(null);

                    if (botMessage != null) {
                        button.setBotMessage(botMessage);

                        botMessageService.save(botMessage);
                        messageService.sendConfirmedAddButtonMessage(botContext.getUser().getManager());
                    }
                    else {
                        buttonService.delete(button);
                        messageService.sendUnableToAddNewButtonMessage(botContext.getUser().getManager());
                    }

                    nextState = EditBotMessageMenu;
                    break;
                case "callback.reject":
                    botContext.getUser().getManager().setButton(null);

                    buttonService.delete(button);
                    messageService.sendRejectedAddNewButtonMessage(botContext.getUser().getManager());
                    nextState = EditBotMessageMenu;
                    break;
                default:
                    nextState = ConfirmAddNewBotMessageButton;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    //#endregion

    //#region BotWork

    BotWork(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendBotWorkKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "Включить/выключить бота":
                    Boolean enabled = environmentConfig.getEnabled();

                    if (enabled) {
                        configService.changeConfigProperty("bot.enabled", Boolean.toString(false));
                        messageService.sendBotWillBeTurnedOffMessage(botContext.getUser().getManager());
                    }
                    else {
                        configService.changeConfigProperty("bot.enabled", Boolean.toString(true));
                        messageService.sendBotWillBeTurnedOnMessage(botContext.getUser().getManager());
                    }
                    nextState = BotWork;
                    break;
                case "Назад":
                    nextState = Settings;
                    break;
                default:
                    nextState = BotWork;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    UpdateCarPrices(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendSendExcelFileWithPricesMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("callback.back")) {
                nextState = Settings;
            }
            else {
                nextState = UpdateCarPrices;
            }
        }

        @Override
        public void handleFile(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String url = botContext.getMessage().getMedia();
            String viberFilename = botContext.getMessage().getFileName();
            List<Car> cars = carService.getAll();
            
            String filename = fileService.downloadFile(url, viberFilename);
            
            Integer countOfCarsUpdated = excelService.updateCarInformation(filename);

            messageService.sendCountOfCarsUpdatedMessage(botContext.getUser().getManager(), countOfCarsUpdated, cars.size());
            
            nextState = Settings;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    //#endregion

    //#endregion

    //#region PostponeMessage

    AddPostponeMessageText(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAddTextMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("callback.back")) {
                nextState = MainMenu;
                return;
            }

            PostponeMessage postponeMessage = PostponeMessage.builder()
                                                             .text(botContext.getMessage().getText())
                                                             .viberId(botContext.getUser().getViberId())
                                                             .isLast(true)
                                                             .build();

            postponeMessageService.save(postponeMessage);

            nextState = AddPostponeMessagePhoto;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AddPostponeMessagePhoto(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendAddPhotoMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "Без фото":
                    nextState = AddPostponeMessageDate;
                    break;
                default:
                    nextState = AddPostponeMessagePhoto;
                    break;
            }
        }

        @Override
        public void handlePicture(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String filename = fileService.downloadPicture(botContext.getMessage().getMedia());
            PostponeMessage postponeMessage = postponeMessageService.getLastByViberId(botContext.getUser().getViberId());

            postponeMessage.setPictureUrl(botContext.getViberService().getPictureEndpoint() + filename);

            postponeMessageService.save(postponeMessage);

            nextState = AddPostponeMessageDate;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AddPostponeMessageDate(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendSelectDateAndTimeMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();
            PostponeMessage postponedMessage = postponeMessageService.getLastByViberId(botContext.getUser().getViberId());

            ValidationResult validationResult = null;
            validationResult = Validator.validateDate(text);
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AddPostponeMessageDate;
            }
            else {
                try {
                    postponedMessage.setDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(text));
    
                    postponeMessageService.save(postponedMessage);
                    nextState = ConfirmationPostponeMessage;
                } catch (ParseException e) {
                    nextState = AddPostponeMessageDate;
                }
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmationPostponeMessage(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendConfirmPostponeMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            PostponeMessage postponeMessage = postponeMessageService.getLastByViberId(botContext.getUser().getViberId());

            switch (botContext.getMessage().getText()) {
                case "callback.confirm":
                    postponeMessage.setIsLast(false);
                    botContext.getUser().getManager().setCountOfPostponeMessages(botContext.getUser().getManager().getCountOfPostponeMessages() + 1);
                    messageService.sendConfirmedPostponeMessageMessage(botContext.getUser().getManager());
                    nextState = MainMenu;
                    break;
                case "callback.reject":
                    postponeMessageService.delete(postponeMessage);
                    messageService.sendRejectedPostponeMessageMessage(botContext.getUser().getManager());
                    nextState = MainMenu;
                    break;
                default:
                    nextState = ConfirmationPostponeMessage;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    //#endregion

    //#region Managers

    ListOfManagers(false) {
        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            List<Manager> managers = managerService.getAll();

            if (!managers.isEmpty()) {
                messageService.sendListOfManagersMessage(botContext.getUser().getManager());
                messageService.sendListOfManagers(botContext.getUser().getManager(), managers);
            }
            else {
                messageService.sendListOfManagersIsEmptyMessage(botContext.getUser().getManager());
            }
        }

        @Override
        public BotState nextState() {
            return ManagersMenu;
        }
    },
    
    AddManager(true) {
        BotState nextState;
        
        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            
            messageService.sendShareManagerContactMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber().substring(1);
            
            Client client = clientService.getByPhoneNumber(phoneNumber);

            if (client != null) {
                botContext.getUser().getManager().setSearchPhoneNumber(phoneNumber);
                nextState = ConfirmAddNewManager;
            }
            else {
                messageService.sendClientNotFoundMessage(botContext.getUser().getManager());
                nextState = AddManager;
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("callback.back")) {
                nextState = ManagersMenu;
            }
            else {
                nextState = AddManager;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    }, 

    ConfirmAddNewManager(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            Client client = clientService.getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
            String avatarUrl = client.getUser().getAvatar() != null ? client.getUser().getAvatar() : FileService.PICTURE_PLACEHOLDER;

            messageService.sendClientInformationMessage(botContext.getUser().getManager(), client, avatarUrl);
            messageService.sendConfrimAddNewManagerMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch(botContext.getMessage().getText()) {
                case "callback.confirm":
                    Client client = clientService.getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
                    User user = client.getUser();
                    Manager newManager = new Manager();

                    client.setUser(null);

                    newManager.setUser(user);
                    newManager.setPhoneNumber(client.getPhoneNumber());

                    user.setRole(Role.ADMIN);
                    user.setClient(null);
                    user.setManager(newManager);
                    user.setBotState(BotState.getAdminInitialState());

                    clientService.delete(client);
                    managerService.save(newManager);

                    botContext.getUser().getManager().setSearchPhoneNumber(null);
                    messageService.sendConfirmedAddNewManagerMessage(botContext.getUser().getManager());
                    nextState = ManagersMenu;
                    break;
                case "callback.reject":
                    botContext.getUser().getManager().setSearchPhoneNumber(null);
                    messageService.sendRejectedAddNewManagerMessage(botContext.getUser().getManager());
                    nextState = ManagersMenu;
                    break;
                default:
                    nextState = ConfirmAddNewManager;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    DeleteManager(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            List<Manager> managers = managerService.getAll();

            messageService.sendListOfManagers(botContext.getUser().getManager(), managers);
            messageService.sendSelectManagerToDelete(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("callback.back")) {
                nextState = ManagersMenu;
            }
            else {
                nextState = DeleteManager;
            }
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber().substring(1);
            
            Manager manager = managerService.getByPhoneNumber(phoneNumber);

            if (manager != null) {
                if (manager.equals(botContext.getUser().getManager())) {
                    messageService.sendUnableToDeleteSelfMessage(botContext.getUser().getManager());
                    nextState = DeleteManager;
                }
                else {
                    botContext.getUser().getManager().setSearchPhoneNumber(phoneNumber);
                    nextState = ConfirmDeleteManager;
                }
            }
            else {
                messageService.sendClientNotFoundMessage(botContext.getUser().getManager());
                nextState = DeleteManager;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    }, 

    ConfirmDeleteManager(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            Manager manager = managerService.getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
            String avatarUrl = manager.getUser().getAvatar() != null ? manager.getUser().getAvatar() : FileService.PICTURE_PLACEHOLDER;

            messageService.sendManagerInformationMessage(botContext.getUser().getManager(), manager, avatarUrl);
            messageService.sendConfrimDeleteManagerMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.confirm": 
                    Manager manager = managerService.getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
                    
                    if (manager != null) {
                        Client client = new Client();
                        User user = manager.getUser();

                        user.setBotState(getUserInitialState());
                        user.setRole(Role.USER);
                        client.setUser(manager.getUser());
                        client.setKeyboardPage(1);
    
                        manager.setBotMessage(null);
                        manager.setUser(null);
    
                        if (manager.getButton() != null) {
                            buttonService.delete(manager.getButton());
                        }
    
                        managerService.delete(manager);
    
                        user.setManager(null);
                        user.setClient(client);

                        managerService.delete(manager);
                        clientService.save(client);
                        userService.save(user);

                        messageService.sendConfirmedDeleteManagerMessage(botContext.getUser().getManager());
                    }
                    else {
                        messageService.sendUnableToDeleteManagerMessage(botContext.getUser().getManager());
                    }

                    botContext.getUser().getManager().setSearchPhoneNumber(null);

                    nextState = ManagersMenu;
                    break;
                case "callback.reject":
                    botContext.getUser().getManager().setSearchPhoneNumber(null);
                    messageService.sendRejectedDeleteManagerMessage(botContext.getUser().getManager());
                    nextState = ManagersMenu;
                    break;
                default:
                    nextState = ConfirmDeleteManager;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    ChangeManagerPrivilegies(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {
            nextState = ManagersMenu;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },


    //#endregion
    
    //#region Clients
    
    GetListOfClients(false) {
        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            messageService.sendListOfClientsMessage(botContext.getUser().getManager());
        }

        @Override
        public BotState nextState() {
            return ClientsMenu;
        }
    },
    
    AdditionalOperationsWithClients(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return ClientsMenu;
        }
    }, 

    //#endregion

    //#region Report

    ReportAboutManagersWork(false) {
        @Override
        public void enter(BotContext botContext) {
            messageService.sendReportAbountManagersWork(botContext.getUser().getManager());
        }

        @Override
        public BotState nextState() {
            return Report;
        }
    },

    ReportAboutBotWork(false) {
        @Override
        public void enter(BotContext botContext) {
            messageService.sendReportAboutBotWork(botContext.getUser().getManager());
        }

        @Override
        public BotState nextState() {
            return Report;
        }
    },

    //#endregion

    //#region Integrations

    AddOrDeleteIntegration(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Integrations;
        }
    },

    // DeleteIntegration(false) {
    //     @Override
    //     public void enter(BotContext botContext) {

    //     }

    //     @Override
    //     public BotState nextState() {
    //         return Integrations;
    //     }
    // },

    NewIntegrations(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Integrations;
        }
    }, 

    //#endregion
    
    //#region UserDialog

    AskClientName(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendAskClientNameMessage(botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            Client client = botContext.getUser().getClient();

            client.setName(botContext.getMessage().getText());

            Dialogue currentDialogue = Dialogue.builder()
                                    .client(botContext.getUser().getClient())
                                    .dialogueIsOver(false)
                                    .botMessagesLastUpdate(botMessageService.getLastUpdate())
                                    .lastUpdate(new Date().getTime())
                                    .mustBeAnswers(botMessageService.getAddedBotMessagesCount())
                                    .build();

            dialogueService.save(currentDialogue);

            nextState = AskBrand;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AskBrand(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendAskBrandMessage(botContext.getUser().getClient(), 
                                                                                 carService.getBrands(),
                                                                                 botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)carService.getBrands().size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskBrand;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < carService.getBrands().size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskBrand;
            }
            else if (carService.getBrands().contains(text)) {
                Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());;

                if (currentDialogue == null && botContext.getUser().getClient() != null && botContext.getUser().getClient().getName() != null) {
                    currentDialogue = Dialogue.builder()
                                        .client(botContext.getUser().getClient())
                                        .dialogueIsOver(false)
                                        .botMessagesLastUpdate(botMessageService.getLastUpdate())
                                        .lastUpdate(new Date().getTime())
                                        .mustBeAnswers(botMessageService.getAddedBotMessagesCount())
                                        .build();
                }

                currentDialogue.setBrand(text);

                botContext.getUser().getClient().setKeyboardPage(1);
                dialogueService.save(currentDialogue);

                nextState = AskModel;
            }
            else {
                nextState = AskBrand;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    AskModel(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());

            messageService.sendAskModelMessage(botContext.getUser().getClient(),
                                                                                 carService.getModelsByBrand(currentDialogue.getBrand()),
                                                                                 botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)carService.getBrands().size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskModel;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < carService.getModelsByBrand(currentDialogue.getBrand()).size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskModel;
            }
            else if (carService.getModelsByBrand(currentDialogue.getBrand()).contains(text)) {
                currentDialogue.setModel(text);

                botContext.getUser().getClient().setKeyboardPage(1);
                dialogueService.save(currentDialogue);

                nextState = AskYearOfIssueFrom;
            }
            else {
                nextState = AskModel;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AskYearOfIssueFrom(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendAskYearOfIssueFromMessage(botContext.getUser().getClient(),
                                                                                           Utils.generateYears(CarService.START_YEAR),
                                                                                           botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)Utils.generateYears(CarService.START_YEAR).size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueFrom;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < Utils.generateYears(CarService.START_YEAR).size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueFrom;
            }
            else if (Utils.generateYears(CarService.START_YEAR).contains(text)) {
                Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());;

                currentDialogue.setYearFrom(Integer.parseInt(text));

                botContext.getUser().getClient().setKeyboardPage(1);

                dialogueService.save(currentDialogue);

                nextState = AskYearOfIssueTo;
            }
            else {
                nextState = AskYearOfIssueFrom;
            }

        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AskYearOfIssueTo(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendAskYearOfIssueToMessage(botContext.getUser().getClient(),
                                                                                           Utils.generateYears(CarService.START_YEAR),
                                                                                           botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)Utils.generateYears(CarService.START_YEAR).size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueTo;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < Utils.generateYears(CarService.START_YEAR).size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueTo;
            }
            else if (Utils.generateYears(CarService.START_YEAR).contains(text)) {
                Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());
                
                currentDialogue.setBotMessage(botMessageService.getFirstMessage());
                currentDialogue.setYearTo(Integer.parseInt(text));

                botContext.getUser().getClient().setKeyboardPage(1);
                dialogueService.save(currentDialogue);

                if (currentDialogue.getBotMessage() == null) {
                    if (currentDialogue.getClient().getPhoneNumber() == null) {
                        nextState = AskPhoneNumber;
                    }
                    else {
                        nextState = PositiveDialogEnd;
                    }
                    return;
                }

                nextState = UserDialog;
            }
            else {
                nextState = AskYearOfIssueTo;
            }

        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    NegativeDialogEnd(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendNegativeDialogEndMessage(botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("Начать новый диалог")) {
                if (botContext.getUser().getClient() != null && botContext.getUser().getClient().getName() != null) {
                    nextState = AskBrand;
                }
                else {
                    nextState = AskClientName;
                }
            }
            else {
                nextState = NegativeDialogEnd;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    AskPhoneNumber(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendAskAndEnterPhoneNumberMessage(botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            ValidationResult validationResult = Validator.validatePhoneNumber(text);
            
            if (!validationResult.getIsValid()) {
                messageService.sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AskPhoneNumber;
                return;
            }

            if (clientService.checkPhoneNumber(text) || managerService.checkPhoneNumber(text)) {
                messageService.sendClientWithThisPhoneNumberAlreadyExistsMessage(botContext.getUser().getClient());
                nextState = AskPhoneNumber;
                return;
            }

            Client client = botContext.getUser().getClient();
            client.setPhoneNumber(text);

            Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());

            currentDialogue.setDialogueIsOver(true);

            dialogueService.save(currentDialogue);
            messageService.sendPhoneNumberSuccessfullyEnteredMessage(botContext.getUser().getClient());
            nextState = PositiveDialogEnd;
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber();

            botContext.getUser().getClient().setPhoneNumber(phoneNumber);
            
            Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());

            currentDialogue.setDialogueIsOver(true);

            nextState = PositiveDialogEnd;

            dialogueService.save(currentDialogue);
            messageService.sendPhoneNumberSuccessfullyEnteredMessage(botContext.getUser().getClient());
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    PositiveDialogEnd(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            messageService.sendPositiveDialogEndMessage(botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            if (botContext.getMessage().getText().equals("Начать новый диалог")) {
                if (botContext.getUser().getClient().getName() != null) {
                    nextState = AskBrand;
                }
                else {
                    nextState = AskClientName;
                }
            }
            else {
                nextState = PositiveDialogEnd;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    UserDialog(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");

            Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());
            
            if (currentDialogue == null) {
                nextState = AskBrand;
                return;
            }

            messageService.sendBotMessageMessage(botContext.getUser().getClient(), currentDialogue.getBotMessage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = dialogueService.getCurrentDialogueByClient(botContext.getUser().getClient());

            if (currentDialogue == null) {
                nextState = AskBrand;
                return;
            }

            Set<Button> messageButtons = currentDialogue.getBotMessage().getButtons();

            for (Button button : messageButtons) {
                if (text.equals(button.getCallbackData())) {
                    Answer newAnswer = new Answer();
                    Answer previousAnswer = answerService.getLastByDialogue(currentDialogue);
                    Long timestamp = new Date().getTime();

                    newAnswer.setAnswer(button.getText());
                    newAnswer.setAnswerType(button.getAnswerType());
                    newAnswer.setDialogue(currentDialogue);

                    if (currentDialogue.getBotMessage().getPreviousMessage() == null) {
                        newAnswer.setStep(1);
                    }
                    else {
                        newAnswer.setStep(previousAnswer.getStep() + 1);
                    }

                    newAnswer.setIsLast(true);
                    newAnswer.setTimestamp(timestamp);

                    currentDialogue.setLastUpdate(timestamp);

                    if (currentDialogue.getBotMessage().getNextMessage() != null) {
                        if (button.getDialogueEnds()) {
                            newAnswer.setIsLast(false);

                            currentDialogue.setBotMessage(null);

                            nextState = NegativeDialogEnd;
                        }
                        else {
                            currentDialogue.setBotMessage(currentDialogue.getBotMessage().getNextMessage());
                            nextState = UserDialog;
                        }
                    }
                    else {
                        currentDialogue.setBotMessage(null);

                        if (currentDialogue.getClient().getPhoneNumber() == null) {
                            newAnswer.setIsLast(false);

                            nextState = AskPhoneNumber;
                        }
                        else {
                            newAnswer.setIsLast(false);

                            currentDialogue.setDialogueIsOver(true);
                            // messageService.sendPositiveDialogEndAndPhoneEnteredMessage(botContext.getUser().getClient());   
                            nextState = PositiveDialogEnd;
                        }
                    }

                    if (previousAnswer != null) {
                        previousAnswer.setIsLast(false);
                        answerService.save(previousAnswer);
                    }
    
                    answerService.save(newAnswer);
                    dialogueService.save(currentDialogue);
    
                    break;
                }
                else {
                    nextState = UserDialog;
                }
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    };

    //#endregion

    private final Boolean isInputNeeded;

    @Setter 
    private static MessageService messageService;
    
    @Setter 
    private static BotMessageService botMessageService;
    
    @Setter 
    private static ClientService clientService;
    
    @Setter 
    private static ManagerService managerService;
    
    @Setter 
    private static AnswerService answerService;
    
    @Setter 
    private static DialogueService dialogueService;

    @Setter
    private static PostponeMessageService postponeMessageService;

    @Setter
    private static ButtonService buttonService;

    @Setter
    private static CarService carService;

    @Setter
    private static FileService fileService;

    @Setter
    private static UserService userService;

    @Setter
    private static EnvironmentConfig environmentConfig;

    @Setter
    private static ConfigService configService;

    @Setter
    private static ExcelService excelService;

    BotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public static BotState getAdminInitialState() {
        return AskManagerFirstname;
    }

    public static BotState getUserInitialState() {
        return AskClientName;
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}
    public void handlePicture(BotContext botContext) {}
    public void handleFile(BotContext botContext) {}
    public void handleContact(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
