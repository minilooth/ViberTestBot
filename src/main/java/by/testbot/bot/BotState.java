package by.testbot.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.testbot.models.Answer;
import by.testbot.models.BotMessage;
import by.testbot.models.Button;
import by.testbot.models.Car;
import by.testbot.models.Client;
import by.testbot.models.Dialogue;
import by.testbot.models.Manager;
import by.testbot.models.PostponeMessage;
import by.testbot.models.User;
import by.testbot.models.enums.AnswerType;
import by.testbot.models.enums.Role;
import by.testbot.services.ButtonService;
import by.testbot.services.CarService;
import by.testbot.services.MessageService;
import by.testbot.services.file.FileService;
import by.testbot.utils.Utils;
import by.testbot.validation.ValidationResult;
import by.testbot.validation.Validator;
import lombok.Setter;

public enum BotState {

    //#region Manager Fill Profile

    AskManagerFirstname(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            botContext.getViberService().getMessageService().sendYouAreBecomeManagerMessage(botContext.getUser().getManager());
            botContext.getViberService().getMessageService().sendAskManagerFirstnameMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendAskManagerSurnameMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendAskManagerPhoneNumberMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            ValidationResult validationResult = Validator.validatePhoneNumber(text);
            
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AskManagerPhoneNumber;
                return;
            }

            if (botContext.getViberService().getClientService().checkPhoneNumber(text) || botContext.getViberService().getManagerService().checkPhoneNumber(text)) {
                botContext.getViberService().getMessageService().sendManagerWithThisPhoneNumberAlreadyExistsMessage(botContext.getUser().getManager());
                nextState = AskManagerPhoneNumber;
                return;
            }

            botContext.getUser().getManager().setPhoneNumber(text);
            botContext.getViberService().getMessageService().sendSuccessfullyFilledManagerProfileMessage(botContext.getUser().getManager());
            nextState = MainMenu;
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            botContext.getUser().getManager().setPhoneNumber(botContext.getMessage().getContact().getPhoneNumber());
            botContext.getViberService().getMessageService().sendSuccessfullyFilledManagerProfileMessage(botContext.getUser().getManager());
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
            // botContext.getViberService().getMessageService().sendAdminMainMenuKeyboard(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendManagersMenuKeyboard(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendClientsMenuKeyboard(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendReportMenuKeyboard(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendIntegrationsMenuKeyboard(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendSettingsMenuKeyboard(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendBotMessageMenuKeyboard(botContext.getUser().getManager());
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

            if (!botContext.getViberService().getBotMessageService().isListOfBotMessagesEmpty()) {
                botContext.getViberService().getMessageService().sendListOfBotMessages(botContext.getUser().getManager());
            }
            else {
                botContext.getViberService().getMessageService().sendListOfBotMessagesIsEmptyMessage(botContext.getUser().getManager());
            }
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

            botContext.getViberService().getMessageService().sendAddNewBotMessageMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getBotMessageService().save(newBotMessage);
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

            botContext.getViberService().getMessageService().sendConfirmAddNewBotMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch (botContext.getMessage().getText()) {
                case "callback.botMessageMenu.confirmYes":
                    List<BotMessage> botMessages = botContext.getViberService().getBotMessageService().getAllAdded();
                    BotMessage newBotMessage = botContext.getUser().getManager().getBotMessage();
                    BotMessage lastMessage = botContext.getViberService().getBotMessageService().getLastMessage();
                    Long timestamp = new Date().getTime();

                    if (lastMessage != null) {
                        lastMessage.setNextMessage(newBotMessage);
                        newBotMessage.setPreviousMessage(lastMessage);
                        botContext.getViberService().getBotMessageService().save(lastMessage);
                    }

                    newBotMessage.setIsAdded(false);
                    newBotMessage.setLastUpdate(timestamp);

                    botMessages.forEach(m -> m.setLastUpdate(timestamp));

                    botContext.getViberService().getBotMessageService().save(newBotMessage);
                    botContext.getViberService().getBotMessageService().saveAll(botMessages);

                    botContext.getUser().getManager().setBotMessage(null);
                    botContext.getViberService().getMessageService().sendSuccessfullyAddedNewBotMessageMessage(botContext.getUser().getManager());

                    nextState = BotMessageMenu;
                    break;
                case "callback.botMessageMenu.confirmNo":
                    botContext.getViberService().getBotMessageService().delete(botContext.getUser().getManager().getBotMessage());
                    botContext.getViberService().getMessageService().sendCancellerationAddNewBotMessageMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendListOfBotMessages(botContext.getUser().getManager());
            botContext.getViberService().getMessageService().sendSelectBotMessageToDeleteMessage(botContext.getUser().getManager());
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
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = SelectBotMessageToDelete;
                return;
            }

            botMessages = botContext.getViberService().getBotMessageService().getAllAdded();
            messagePosition = Integer.parseInt(text);

            if (messagePosition < 1 || messagePosition > botMessages.size()) {
                botContext.getViberService().getMessageService().sendBotMessageNotFoundMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendConfirmDeleteBotMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch (botContext.getMessage().getText()) {
                case "callback.botMessageMenu.confirmYes":
                    BotMessage deleteMessage = botContext.getUser().getManager().getBotMessage();
                    BotMessage previousMessage = deleteMessage.getPreviousMessage();
                    BotMessage nextMessage = deleteMessage.getNextMessage();

                    Set<Dialogue> dialogues = deleteMessage.getDialogues();
                    Set<Manager> managers = deleteMessage.getManagers();

                    if (previousMessage != null) {
                        previousMessage.setNextMessage(nextMessage);
                        botContext.getViberService().getBotMessageService().save(previousMessage);
                    }
                    if (nextMessage != null) {
                        nextMessage.setPreviousMessage(previousMessage);
                        botContext.getViberService().getBotMessageService().save(nextMessage);
                    }

                    dialogues.forEach(d -> d.setBotMessage(previousMessage));
                    managers.forEach(m -> {
                        if (m.getBotMessage() != null && m.getBotMessage().getId() == deleteMessage.getId()) {
                            m.setBotMessage(null);
                        }
                    });

                    botContext.getViberService().getManagerService().saveAll(managers);
                    botContext.getViberService().getDialogueService().saveAll(dialogues);
                    botContext.getViberService().getBotMessageService().delete(deleteMessage);

                    botContext.getViberService().getMessageService().sendSuccessfullyDeleteBotMessageMessage(botContext.getUser().getManager());
                    nextState = BotMessageMenu;
                    break;
                case "callback.botMessageMenu.confirmNo":
                    botContext.getUser().getManager().setBotMessage(null);

                    botContext.getViberService().getMessageService().sendCancellerationDeleteBotMessageMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendCurrentBotMessageTextMessage(botContext.getUser().getManager());
            botContext.getViberService().getMessageService().sendEnterBotMessageTextMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendConfirmChangeBotMessageTextMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch(botContext.getMessage().getText()) {
                case "callback.botMessageMenu.confirmYes":
                    BotMessage botMessage = botContext.getUser().getManager().getBotMessage();

                    if (botMessage != null) {
                        botMessage.setMessage(botContext.getUser().getManager().getBotMessageText());

                        botContext.getUser().getManager().setBotMessageText(null);

                        botContext.getViberService().getBotMessageService().save(botMessage);
                        botContext.getViberService().getMessageService().sendSuccessEditBotMessageTextMessage(botContext.getUser().getManager());
                    }
                    else {
                        botContext.getUser().getManager().setBotMessageText(null);

                        botContext.getViberService().getMessageService().sendUnableToEditBotMessageMessage(botContext.getUser().getManager());
                    }

                    nextState = EditBotMessageMenu;
                    break;
                case "callback.botMessageMenu.confirmNo":
                    botContext.getUser().getManager().setBotMessageText(null);

                    botContext.getViberService().getMessageService().sendCancellerationChangeBotMessageTextMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendEnterNewBotMessageSequenceMessage(botContext.getUser().getManager());
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

            List<Integer> order = botContext.getViberService().getBotMessageService().parseBotMessageSequence(text);
            List<BotMessage> oldBotMessageSequence = botContext.getViberService().getBotMessageService().getAllAdded();
            ValidationResult validationResult = null;

            validationResult = Validator.validateBotMessageSequence(text);
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                
                nextState = ChangeBotMessagesOrder;
                return;
            }
            validationResult = Validator.validateBotMessageSequence(order, oldBotMessageSequence);
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                
                nextState = ChangeBotMessagesOrder;
                return;
            }

            botContext.getViberService().getBotMessageService().changeBotMessagesOrder(order, oldBotMessageSequence);
            botContext.getViberService().getMessageService().sendSuccessfullyChangedBotMessageSequenceMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendListOfBotMessages(botContext.getUser().getManager());
            botContext.getViberService().getMessageService().sendSelectBotMessageToEdit(botContext.getUser().getManager());
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
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = SelectBotMessageToEdit;
                return;
            }

            List<BotMessage> botMessages = botContext.getViberService().getBotMessageService().getAllAdded();
            Integer messageIndex = Integer.parseInt(text);

            if (messageIndex < 1 || messageIndex > botMessages.size()) {
                botContext.getViberService().getMessageService().sendBotMessageNotFoundMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendBotMessageButtonsMenuKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            if (botContext.getUser().getManager().getBotMessage() == null) {
                botContext.getViberService().getMessageService().sendUnableToEditBotMessageMessage(botContext.getUser().getManager());
                nextState = BotMessageMenu;
                return;
            }

            switch(botContext.getMessage().getText()) {
                case "callback.botMessageButtonsMenu.showMessageButton":
                    botContext.getViberService().getMessageService().sendBotMessageText(botContext.getUser().getManager());
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
                        botContext.getViberService().getMessageService().sendButtonsListIsEmptyMessage(botContext.getUser().getManager());
                        nextState = EditBotMessageMenu;
                    }
                    else {
                        nextState = DeleteBotMessageButton;
                    }
                    break;
                case "callback.botMessageButtonsMenu.addButton": 
                    if (botContext.getUser().getManager().getBotMessage().getButtons().size() > ButtonService.MAX_BUTTONS_COUNT) {
                        botContext.getViberService().getMessageService().sendMaxButtonsCountReachedMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendListOfBotMessageButtons(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendListOfBotMessageButtons(botContext.getUser().getManager());
            botContext.getViberService().getMessageService().sendAskNumberOfButtonToDeleteMessage(botContext.getUser().getManager());
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

                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = DeleteBotMessageButton;
                return;
            }

            buttons = botContext.getViberService().getButtonService().getAllByBotMessage(botContext.getUser().getManager().getBotMessage());
            buttonPosition = Integer.parseInt(text);

            if (buttonPosition < 1 || buttonPosition > buttons.size()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), "Такой кнопки не существует", null, null);
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

            botContext.getViberService().getMessageService().sendConfirmDeleteBotMessageButtonMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            switch(botContext.getMessage().getText()) {
                case "callback.botMessageMenu.botMessageButtonsMenu.confirmYes":
                    Button button = botContext.getUser().getManager().getButton();

                    if (button != null) {
                        botContext.getUser().getManager().setButton(null);

                        botContext.getViberService().getButtonService().delete(button);
                        botContext.getViberService().getMessageService().sendSuccessfullyDeletedButtonMessage(botContext.getUser().getManager());
                    }
                    else {
                        botContext.getViberService().getMessageService().sendUnableToDeleteButtonMessage(botContext.getUser().getManager());
                    }
                    
                    nextState = EditBotMessageMenu;
                    break;
                case "callback.botMessageMenu.botMessageButtonsMenu.confirmNo":
                    botContext.getUser().getManager().setButton(null);

                    botContext.getViberService().getMessageService().sendCancellerationDeleteButtonMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendAddBotMessageButtonMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            Button newButton = Button.builder()
                                     .text(botContext.getMessage().getText())
                                     .callbackData(botContext.getViberService().getButtonService().generateCallbackData())
                                     .build();
            
            botContext.getViberService().getButtonService().save(newButton);
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

            botContext.getViberService().getMessageService().sendSelectAnswerTypeMessage(botContext.getUser().getManager());
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

                    botContext.getViberService().getButtonService().save(button);
                    nextState = SelectIsButtonEndsDialogue;
                    break;
                case "callback.botMessageMenu.selectAnswerType.positive":
                    button.setAnswerType(AnswerType.POSITIVE);

                    botContext.getViberService().getButtonService().save(button);
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

            botContext.getViberService().getMessageService().sendIsButtonEndsDialogueMessage(botContext.getUser().getManager());
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

                    botContext.getViberService().getButtonService().save(button);
                    nextState = ConfirmAddNewBotMessageButton;
                    break;
                case "Нет": 
                    button.setDialogueEnds(true);

                    botContext.getViberService().getButtonService().save(button);
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

            botContext.getViberService().getMessageService().sendConfirmAddNewBotMessageButtonMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            Button button = botContext.getUser().getManager().getButton();

            switch (botContext.getMessage().getText()) {
                case "callback.botMessageMenu.botMessageButtonsMenu.confirmYes":
                    BotMessage botMessage = botContext.getUser().getManager().getBotMessage();

                    botContext.getUser().getManager().setButton(null);

                    if (botMessage != null) {
                        button.setBotMessage(botMessage);

                        botContext.getViberService().getBotMessageService().save(botMessage);
                        botContext.getViberService().getMessageService().sendSuccessfullyAddedButtonMessage(botContext.getUser().getManager());
                    }
                    else {
                        botContext.getViberService().getButtonService().delete(button);
                        botContext.getViberService().getMessageService().sendUnableToAddNewButtonMessage(botContext.getUser().getManager());
                    }

                    nextState = EditBotMessageMenu;
                    break;
                case "callback.botMessageMenu.botMessageButtonsMenu.confirmNo":
                    botContext.getUser().getManager().setButton(null);

                    botContext.getViberService().getButtonService().delete(button);
                    botContext.getViberService().getMessageService().sendCancellerationAddNewButtonMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendBotWorkKeyboard(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "Включить/выключить бота":
                    Boolean enabled = botContext.getViberService().getEnvironmentConfig().getEnabled();

                    if (enabled) {
                        botContext.getViberService().getConfigService().changeConfigProperty("bot.enabled", Boolean.toString(false));
                        botContext.getViberService().getMessageService().sendBotWillBeTurnedOffMessage(botContext.getUser().getManager());
                    }
                    else {
                        botContext.getViberService().getConfigService().changeConfigProperty("bot.enabled", Boolean.toString(true));
                        botContext.getViberService().getMessageService().sendBotWillBeTurnedOnMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendSendExcelFileWithPricesMessage(botContext.getUser().getManager());
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
            List<Car> cars = botContext.getViberService().getCarService().getAll();
            
            String filename = botContext.getViberService().getFileService().downloadFile(url, viberFilename);
            
            Integer countOfCarsUpdated = botContext.getViberService().getExcelService().updateCarInformation(filename);

            botContext.getViberService().getMessageService().sendCountOfCarsUpdatedMessage(botContext.getUser().getManager(), countOfCarsUpdated, cars.size());
            
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

            botContext.getViberService().getMessageService().sendAddTextMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            PostponeMessage postponeMessage = PostponeMessage.builder()
                                                             .text(botContext.getMessage().getText())
                                                             .viberId(botContext.getUser().getViberId())
                                                             .isLast(true)
                                                             .build();

            botContext.getViberService().getPostponeMessageService().save(postponeMessage);

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

            botContext.getViberService().getMessageService().sendAddPhotoMessageAndKeyboard(botContext.getUser().getManager());
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

            String filename = botContext.getViberService().getFileService().downloadPicture(botContext.getMessage().getMedia());
            PostponeMessage postponeMessage = botContext.getViberService().getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            postponeMessage.setPictureUrl(botContext.getViberService().getPictureEndpoint() + filename);

            botContext.getViberService().getPostponeMessageService().save(postponeMessage);

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

            botContext.getViberService().getMessageService().sendSelectDateAndTimeMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();
            PostponeMessage postponedMessage = botContext.getViberService().getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            ValidationResult validationResult = null;
            validationResult = Validator.validateDate(text);
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AddPostponeMessageDate;
            }
            else {
                try {
                    postponedMessage.setDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(text));
    
                    botContext.getViberService().getPostponeMessageService().save(postponedMessage);
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

            botContext.getViberService().getMessageService().sendConfirmPostponeMessageMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            PostponeMessage postponeMessage = botContext.getViberService().getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            switch (botContext.getMessage().getText()) {
                case "Да":
                    postponeMessage.setIsLast(false);
                    botContext.getUser().getManager().setCountOfPostponeMessages(botContext.getUser().getManager().getCountOfPostponeMessages() + 1);
                    botContext.getViberService().getMessageService().sendSuccessPostponeMessageConfirmationMessage(botContext.getUser().getManager());
                    nextState = MainMenu;
                    break;
                case "Нет":
                    botContext.getViberService().getPostponeMessageService().delete(postponeMessage);
                    botContext.getViberService().getMessageService().sendDeclinePostponeMessageConfirmationMessage(botContext.getUser().getManager());
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

            List<Manager> managers = botContext.getViberService().getManagerService().getAll();

            if (!managers.isEmpty()) {
                botContext.getViberService().getMessageService().sendListOfManagersMessage(botContext.getUser().getManager());
                botContext.getViberService().getMessageService().sendListOfManagers(botContext.getUser().getManager(), managers);
            }
            else {
                botContext.getViberService().getMessageService().sendListOfManagersIsEmptyMessage(botContext.getUser().getManager());
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
            
            botContext.getViberService().getMessageService().sendShareManagerContactMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber().substring(1);
            
            Client client = botContext.getViberService().getClientService().getByPhoneNumber(phoneNumber);

            if (client != null) {
                botContext.getUser().getManager().setSearchPhoneNumber(phoneNumber);
                nextState = ConfirmAddNewManager;
            }
            else {
                botContext.getViberService().getMessageService().sendClientNotFoundMessage(botContext.getUser().getManager());
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

            Client client = botContext.getViberService().getClientService().getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
            String avatarUrl = client.getUser().getAvatar() != null ? client.getUser().getAvatar() : FileService.PICTURE_PLACEHOLDER;

            botContext.getViberService().getMessageService().sendClientInformationMessage(botContext.getUser().getManager(), client, avatarUrl);
            botContext.getViberService().getMessageService().sendConfrimAddNewManagerMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch(botContext.getMessage().getText()) {
                case "callback.managers.confirmYes":
                    Client client = botContext.getViberService().getClientService().getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
                    User user = client.getUser();
                    Manager newManager = new Manager();

                    client.setUser(null);

                    newManager.setUser(user);
                    newManager.setPhoneNumber(client.getPhoneNumber());

                    user.setRole(Role.ADMIN);
                    user.setClient(null);
                    user.setManager(newManager);
                    user.setBotState(BotState.getAdminInitialState());

                    botContext.getViberService().getClientService().delete(client);
                    botContext.getViberService().getManagerService().save(newManager);

                    botContext.getUser().getManager().setSearchPhoneNumber(null);
                    botContext.getViberService().getMessageService().sendSuccessfullyAddedNewManagerMessage(botContext.getUser().getManager());
                    nextState = ManagersMenu;
                    break;
                case "callback.managers.confirmNo":
                    botContext.getUser().getManager().setSearchPhoneNumber(null);
                    botContext.getViberService().getMessageService().sendCancellerationAddManagerMessage(botContext.getUser().getManager());
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

            List<Manager> managers = botContext.getViberService().getManagerService().getAll();

            botContext.getViberService().getMessageService().sendListOfManagers(botContext.getUser().getManager(), managers);
            botContext.getViberService().getMessageService().sendSelectManagerToDelete(botContext.getUser().getManager());
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
            
            Manager manager = botContext.getViberService().getManagerService().getByPhoneNumber(phoneNumber);

            if (manager != null) {
                if (manager.equals(botContext.getUser().getManager())) {
                    botContext.getViberService().getMessageService().sendUnableToDeleteSelfMessage(botContext.getUser().getManager());
                    nextState = DeleteManager;
                }
                else {
                    botContext.getUser().getManager().setSearchPhoneNumber(phoneNumber);
                    nextState = ConfirmDeleteManager;
                }
            }
            else {
                botContext.getViberService().getMessageService().sendClientNotFoundMessage(botContext.getUser().getManager());
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

            Manager manager = botContext.getViberService().getManagerService().getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
            String avatarUrl = manager.getUser().getAvatar() != null ? manager.getUser().getAvatar() : FileService.PICTURE_PLACEHOLDER;

            botContext.getViberService().getMessageService().sendManagerInformationMessage(botContext.getUser().getManager(), manager, avatarUrl);
            botContext.getViberService().getMessageService().sendConfrimDeleteManagerMessage(botContext.getUser().getManager());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            switch (botContext.getMessage().getText()) {
                case "callback.managers.confirmYes": 
                    Manager manager = botContext.getViberService().getManagerService().getByPhoneNumber(botContext.getUser().getManager().getSearchPhoneNumber());
                    
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
                            botContext.getViberService().getButtonService().delete(manager.getButton());
                        }
    
                        botContext.getViberService().getManagerService().delete(manager);
    
                        user.setManager(null);
                        user.setClient(client);

                        botContext.getViberService().getManagerService().delete(manager);
                        botContext.getViberService().getClientService().save(client);
                        botContext.getViberService().getUserService().save(user);

                        botContext.getViberService().getMessageService().sendSuccessfullyDeleteManagerMessage(botContext.getUser().getManager());
                    }
                    else {
                        botContext.getViberService().getMessageService().sendUnableToDeleteManagerMessage(botContext.getUser().getManager());
                    }

                    botContext.getUser().getManager().setSearchPhoneNumber(null);

                    nextState = ManagersMenu;
                    break;
                case "callback.managers.confirmNo":
                    botContext.getUser().getManager().setSearchPhoneNumber(null);
                    botContext.getViberService().getMessageService().sendCancellerationDeleteManagerMessage(botContext.getUser().getManager());
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

            botContext.getViberService().getMessageService().sendListOfClientsMessage(botContext.getUser().getManager());
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
            botContext.getViberService().getMessageService().sendReportAbountManagersWork(botContext.getUser().getManager());
        }

        @Override
        public BotState nextState() {
            return Report;
        }
    },

    ReportAboutBotWork(false) {
        @Override
        public void enter(BotContext botContext) {

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

            botContext.getViberService().getMessageService().sendAskClientNameMessage(botContext.getUser().getClient());
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
                                    .botMessagesLastUpdate(botContext.getViberService().getBotMessageService().getLastUpdate())
                                    .lastUpdate(new Date().getTime())
                                    .mustBeAnswers(botContext.getViberService().getBotMessageService().getAddedBotMessagesCount())
                                    .build();

            botContext.getViberService().getDialogueService().save(currentDialogue);

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

            botContext.getViberService().getMessageService().sendAskBrandMessage(botContext.getUser().getClient(), 
                                                                                 botContext.getViberService().getCarService().getBrands(),
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
                    page = (int)Math.ceil((float)botContext.getViberService().getCarService().getBrands().size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskBrand;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < botContext.getViberService().getCarService().getBrands().size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskBrand;
            }
            else if (botContext.getViberService().getCarService().getBrands().contains(text)) {
                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());;

                if (currentDialogue == null && botContext.getUser().getClient() != null && botContext.getUser().getClient().getName() != null) {
                    currentDialogue = Dialogue.builder()
                                        .client(botContext.getUser().getClient())
                                        .dialogueIsOver(false)
                                        .botMessagesLastUpdate(botContext.getViberService().getBotMessageService().getLastUpdate())
                                        .lastUpdate(new Date().getTime())
                                        .mustBeAnswers(botContext.getViberService().getBotMessageService().getAddedBotMessagesCount())
                                        .build();
                }

                currentDialogue.setBrand(text);

                botContext.getUser().getClient().setKeyboardPage(1);
                botContext.getViberService().getDialogueService().save(currentDialogue);

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

            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());

            botContext.getViberService().getMessageService().sendAskModelMessage(botContext.getUser().getClient(),
                                                                                 botContext.getViberService().getCarService().getModelsByBrand(currentDialogue.getBrand()),
                                                                                 botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)botContext.getViberService().getCarService().getBrands().size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskModel;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < botContext.getViberService().getCarService().getModelsByBrand(currentDialogue.getBrand()).size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskModel;
            }
            else if (botContext.getViberService().getCarService().getModelsByBrand(currentDialogue.getBrand()).contains(text)) {
                currentDialogue.setModel(text);

                botContext.getUser().getClient().setKeyboardPage(1);
                botContext.getViberService().getDialogueService().save(currentDialogue);

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

            botContext.getViberService().getMessageService().sendAskYearOfIssueFromMessage(botContext.getUser().getClient(),
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
                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());;

                currentDialogue.setYearFrom(Integer.parseInt(text));

                botContext.getUser().getClient().setKeyboardPage(1);

                botContext.getViberService().getDialogueService().save(currentDialogue);

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

            botContext.getViberService().getMessageService().sendAskYearOfIssueToMessage(botContext.getUser().getClient(),
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
                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());
                
                currentDialogue.setBotMessage(botContext.getViberService().getBotMessageService().getFirstMessage());
                currentDialogue.setYearTo(Integer.parseInt(text));

                botContext.getUser().getClient().setKeyboardPage(1);
                botContext.getViberService().getDialogueService().save(currentDialogue);

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

            botContext.getViberService().getMessageService().sendNegativeDialogEndMessage(botContext.getUser().getClient());
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

            botContext.getViberService().getMessageService().sendAskAndEnterPhoneNumberMessage(botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            ValidationResult validationResult = Validator.validatePhoneNumber(text);
            
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AskPhoneNumber;
                return;
            }

            if (botContext.getViberService().getClientService().checkPhoneNumber(text) || botContext.getViberService().getManagerService().checkPhoneNumber(text)) {
                botContext.getViberService().getMessageService().sendClientWithThisPhoneNumberAlreadyExistsMessage(botContext.getUser().getClient());
                nextState = AskPhoneNumber;
                return;
            }

            Client client = botContext.getUser().getClient();
            client.setPhoneNumber(text);

            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());

            currentDialogue.setDialogueIsOver(true);

            botContext.getViberService().getDialogueService().save(currentDialogue);
            botContext.getViberService().getMessageService().sendPhoneNumberSuccessfullyEnteredMessage(botContext.getUser().getClient());
            nextState = PositiveDialogEnd;
        }

        @Override
        public void handleContact(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber();

            botContext.getUser().getClient().setPhoneNumber(phoneNumber);
            
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());

            currentDialogue.setDialogueIsOver(true);

            nextState = PositiveDialogEnd;

            botContext.getViberService().getDialogueService().save(currentDialogue);
            botContext.getViberService().getMessageService().sendPhoneNumberSuccessfullyEnteredMessage(botContext.getUser().getClient());
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

            botContext.getViberService().getMessageService().sendPositiveDialogEndMessage(botContext.getUser().getClient());
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

            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());
            
            if (currentDialogue == null) {
                nextState = AskBrand;
                return;
            }

            botContext.getViberService().getMessageService().sendBotMessageMessage(botContext.getUser().getClient(), currentDialogue.getBotMessage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getClient(), "Client is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogueByClient(botContext.getUser().getClient());

            if (currentDialogue == null) {
                nextState = AskBrand;
                return;
            }

            Set<Button> messageButtons = currentDialogue.getBotMessage().getButtons();

            for (Button button : messageButtons) {
                if (text.equals(button.getCallbackData())) {
                    Answer newAnswer = new Answer();
                    Answer previousAnswer = botContext.getViberService().getAnswerService().getLastByDialogue(currentDialogue);
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
                            botContext.getViberService().getMessageService().sendPositiveDialogEndAndPhoneEnteredMessage(botContext.getUser().getClient());   
                            nextState = PositiveDialogEnd;
                        }
                    }

                    if (previousAnswer != null) {
                        previousAnswer.setIsLast(false);
                        botContext.getViberService().getAnswerService().save(previousAnswer);
                    }
    
                    botContext.getViberService().getAnswerService().save(newAnswer);
                    botContext.getViberService().getDialogueService().save(currentDialogue);
    
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
    MessageService messageService;

    // public void setMessageService(MessageService messageService) {
    //     BotState.messageService = messageService;
    // }

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

    // @Component
    // public static class BotStateInjector {
    //     @Autowired
    //     private MessageService messageService;

    //     @PostConstruct
    //     public void postConstruct() {
    //         for (BotState botState : EnumSet.allOf(BotState.class))
    //             botState.setMessageService(messageService);
    //     }
    // }
}
