package by.testbot.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import by.testbot.models.Answer;
import by.testbot.models.BotMessage;
import by.testbot.models.Button;
import by.testbot.models.Client;
import by.testbot.models.Dialogue;
import by.testbot.models.Manager;
import by.testbot.models.PostponeMessage;
import by.testbot.models.enums.AnswerType;
import by.testbot.services.ButtonService;
import by.testbot.utils.Utils;
import by.testbot.validation.ValidationResult;
import by.testbot.validation.Validator;

public enum BotState {

    //#region Main Menu

    MainMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendAdminMainMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            if (botContext.getMessage() == null) {
                throw new NullPointerException("Message is null");
            }

            String text = botContext.getMessage().getText();

            switch (text) {
                case "callback.adminMainMenu.postponeMessage":
                    nextState = AddText;
                    break;
                case "callback.adminMainMenu.managers":
                    nextState = Managers;
                    break;
                case "callback.adminMainMenu.clients":
                    nextState = Clients;
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

    Managers(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendManagersMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            if (botContext.getMessage() == null) {
                throw new NullPointerException("Message is null");
            }

            String text = botContext.getMessage().getText();

            switch (text) {
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
                    nextState = Managers;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    Clients(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendClientsMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            if (botContext.getMessage() == null) {
                throw new NullPointerException("Message is null");
            }

            String text = botContext.getMessage().getText();

            switch (text) {
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
                    nextState = Clients;
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
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendReportMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            if (botContext.getMessage() == null) {
                throw new NullPointerException("Message is null");
            }

            String text = botContext.getMessage().getText();

            switch (text) {
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
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendIntegrationsMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            if (botContext.getMessage() == null) {
                throw new NullPointerException("Message is null");
            }

            String text = botContext.getMessage().getText();

            switch (text) {
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
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendSettingsMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            if (botContext.getMessage() == null) {
                throw new NullPointerException("Message is null");
            }

            String text = botContext.getMessage().getText();

            switch (text) {
                case "callback.settings.editTextsWhichBotSend":
                    nextState = BotMessageMenu;
                    break;
                case "callback.settings.botUsagePeriod":
                    nextState = BotUsagePeriod;
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

            botContext.getViberService().getMessageService().sendBotMessageMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            switch (text) {
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

            botContext.getViberService().getMessageService().sendListOfBotMessagesMessage(botContext.getUser().getViberId());
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

            botContext.getViberService().getMessageService().sendAddNewBotMessageMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();
            BotMessage newBotMessage = new BotMessage();

            newBotMessage.setMessage(text);
            newBotMessage.setIsAdded(true);
            newBotMessage.setLastUpdate(new Date().getTime());

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

            botContext.getViberService().getMessageService().sendConfirmAddNewBotMessageMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            switch (text) {
                case "callback.botMessageMenu.addNewBotMessage.confirmYes":
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
                    botContext.getViberService().getMessageService().sendSuccessfullyAddedNewBotMessageMessage(botContext.getUser().getViberId());

                    nextState = BotMessageMenu;
                    break;
                case "callback.botMessageMenu.addNewBotMessage.confirmNo":
                    botContext.getViberService().getBotMessageService().delete(botContext.getUser().getManager().getBotMessage());
                    botContext.getViberService().getMessageService().sendCancellerationAddNewBotMessageMessage(botContext.getUser().getViberId());

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

            botContext.getViberService().getMessageService().sendListOfBotMessagesMessage(botContext.getUser().getViberId());
            botContext.getViberService().getMessageService().sendSelectBotMessageToDelete(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {
                nextState = BotMessageMenu;
            }
            else {
                ValidationResult validationResult = Validator.validateId(text);
                if (!validationResult.getIsValid()) {
                    botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                    nextState = SelectBotMessageToDelete;
                }
                else {
                    List<BotMessage> botMessages = botContext.getViberService().getBotMessageService().getAllAdded();
                    Integer messagePosition = Integer.parseInt(text);

                    if (messagePosition < 1 || messagePosition > botMessages.size()) {
                        botContext.getViberService().getMessageService().sendBotMessageNotFoundMessage(botContext.getUser().getViberId());
                        nextState = SelectBotMessageToDelete;
                    }
                    else {
                        Integer counter = 1;
                        BotMessage botMessage = botContext.getViberService().getBotMessageService().getFirstMessage();

                        while(botMessage != null && counter != messagePosition) {
                            botMessage = botMessage.getNextMessage();
                            counter++;
                        }

                        if (botMessage != null) {
                            botContext.getUser().getManager().setBotMessage(botMessage);
                            botContext.getUser().getManager().setButtons(botContext.getViberService().getButtonService().copyButtons(botMessage.getButtons(), botContext.getUser().getManager()));

                            nextState = ConfirmDeleteBotMessage;
                        }
                        else {
                            nextState = SelectBotMessageToDelete;
                        }
                    }
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

            botContext.getViberService().getMessageService().sendConfirmDeleteBotMessageMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            switch (text) {
                case "callback.botMessageMenu.deleteBotMessage.confirmYes":
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

                    botContext.getViberService().getMessageService().sendSuccessfullyDeleteBotMessageMessage(botContext.getUser().getViberId());
                    nextState = BotMessageMenu;
                    break;
                case "callback.botMessageMenu.deleteBotMessage.confirmNo":
                    botContext.getUser().getManager().setBotMessage(null);

                    botContext.getViberService().getMessageService().sendCancellerationDeleteBotMessageMessage(botContext.getUser().getViberId());
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

            botContext.getViberService().getMessageService().sendBotTextMessage(botContext.getUser().getManager());
            botContext.getViberService().getMessageService().sendEnterBotMessageTextMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            botContext.getUser().getManager().setBotMessageText(text);

            botContext.getViberService().getMessageService().sendSuccessEditBotMessageTextMessage(botContext.getUser().getViberId());

            nextState = EditBotMessageMenu;
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

            botContext.getViberService().getMessageService().sendEnterNewBotMessageSequence(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");

            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {

            }
            else {
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
                botContext.getViberService().getMessageService().sendSuccessfullyChangedBotMessageSequenceMessage(botContext.getUser().getViberId());
                nextState = BotMessageMenu;
            }

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

            botContext.getViberService().getMessageService().sendListOfBotMessagesMessage(botContext.getUser().getViberId());
            botContext.getViberService().getMessageService().sendSelectBotMessageToEdit(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {
                nextState = BotMessageMenu;
            }
            else {
                ValidationResult validationResult = Validator.validateId(text);
                if (!validationResult.getIsValid()) {
                    botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                    nextState = SelectBotMessageToEdit;
                }
                else {
                    List<BotMessage> botMessages = botContext.getViberService().getBotMessageService().getAllAdded();
                    Integer messageIndex = Integer.parseInt(text);

                    if (messageIndex < 1 || messageIndex > botMessages.size()) {
                        botContext.getViberService().getMessageService().sendBotMessageNotFoundMessage(botContext.getUser().getViberId());
                        nextState = SelectBotMessageToEdit;
                    }
                    else {
                        BotMessage botMessage = botMessages.get(messageIndex - 1);

                        if (botMessage != null) {
                            botContext.getUser().getManager().setBotMessage(botMessage);
                            botContext.getUser().getManager().setButtons(botContext.getViberService().getButtonService().copyButtons(botMessage.getButtons(), botContext.getUser().getManager()));

                            nextState = EditBotMessageMenu;
                        }
                        else {
                            nextState = SelectBotMessageToEdit;
                        }
                    }
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

            botContext.getViberService().getMessageService().sendBotMessageButtonsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            Objects.requireNonNull(botContext.getUser(), "User is null");
            Objects.requireNonNull(botContext.getMessage(), "Message is null");
            Objects.requireNonNull(botContext.getUser().getManager(), "Manager is null");

            String text = botContext.getMessage().getText();

            switch(text) {
                case "callback.botMessageButtonsMenu.editTextButton":
                    nextState = EditBotMessageText;
                    break;
                case "callback.botMessageButtonsMenu.listButton": 
                    nextState = ListOfBotMessageButtons;
                    break;
                case "callback.botMessageButtonsMenu.deleteButton":
                    if (botContext.getUser().getManager().getButtons().size() < 1) {
                        botContext.getViberService().getMessageService().sendMinButtonsCountReachedMessage(botContext.getUser().getViberId());
                        nextState = EditBotMessageMenu;
                    }
                    else {
                        nextState = DeleteBotMessageButton;
                    }
                    break;
                case "callback.botMessageButtonsMenu.addButton": 
                    if (botContext.getUser().getManager().getButtons().size() > ButtonService.MAX_BUTTONS_COUNT) {
                        botContext.getViberService().getMessageService().sendMaxButtonsCountReachedMessage(botContext.getUser().getViberId());
                        nextState = EditBotMessageMenu;
                    }
                    else {
                        nextState = AddBotMessageButton;
                    }
                    break;
                case "callback.botMessageButtonsMenu.saveButton":
                    if (botContext.getUser().getManager().getBotMessage() != null) {
                        Set<Button> oldButtons = botContext.getUser().getManager().getBotMessage().getButtons();
                        Set<Button> newButtons = botContext.getUser().getManager().getButtons();

                        String newText = botContext.getUser().getManager().getBotMessageText();

                        if (newText != null) {
                            botContext.getUser().getManager().getBotMessage().setMessage(newText);
                        }

                        oldButtons.forEach(b -> {
                            if (!newButtons.contains(b)) {
                                botContext.getViberService().getButtonService().delete(b);
                            }
                        });

                        BotMessage botMessage = botContext.getUser().getManager().getBotMessage();
                        botMessage.setButtons(newButtons);

                        botContext.getUser().getManager().setButtons(null);
                        botContext.getUser().getManager().setBotMessageText(null);
                        botContext.getUser().getManager().setBotMessage(null);

                        botContext.getViberService().getUserService().save(botContext.getUser());
                        botContext.getViberService().getBotMessageService().save(botMessage);

                        botContext.getViberService().getButtonService().deleteAll(oldButtons);
                        botContext.getViberService().getMessageService().sendChangesSuccessfullySavedMessage(botContext.getUser().getViberId());
                    }
                    else {
                        Set<Button> buttons = botContext.getUser().getManager().getButtons();

                        botContext.getViberService().getButtonService().deleteAll(buttons);
                        botContext.getViberService().getMessageService().sendUnableSaveBotMessage(botContext.getUser().getViberId());
                    }

                    nextState = BotMessageMenu; 
                    break;
                case "callback.botMessageButtonsMenu.cancelButton":
                    Set<Button> buttons = botContext.getUser().getManager().getButtons();

                    botContext.getUser().getManager().setBotMessage(null);
                    botContext.getUser().getManager().setButtons(null);
                    botContext.getUser().getManager().setBotMessageText(null);

                    botContext.getViberService().getButtonService().deleteAll(buttons);
                    botContext.getViberService().getMessageService().sendChangesIsCancelledMessage(botContext.getUser().getViberId());

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

            botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), 
                                                                             botContext.getViberService().getButtonService().formatButtons(botContext.getUser().getManager().getButtons()), null, null);
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
            botContext.getViberService().getMessageService().sendDeleteBotMessageButtonMessage(botContext.getUser().getViberId(), botContext.getUser().getManager().getButtons());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            if (text.equals("callback.back")) {
                nextState = EditBotMessageMenu;
            }
            else {
                ValidationResult validationResult = Validator.validateId(text);
                if (!validationResult.getIsValid()) {
                    if (botContext.getUser() == null) {
                        throw new NullPointerException("User is null");
                    }

                    botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                    nextState = DeleteBotMessageButton;
                }
                else {
                    Set<Button> buttons = botContext.getUser().getManager().getButtons();
                    Integer buttonPosition = Integer.parseInt(text);

                    if (buttonPosition < 1 || buttonPosition > buttons.size()) {
                        botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), "Такой кнопки не существует", null, null);
                        nextState = DeleteBotMessageButton;
                    }
                    else {
                        List<Button> buttonsCopy = List.copyOf(buttons);

                        Button button = buttonsCopy.get(buttonPosition - 1);

                        if (button != null) {
                            buttons.remove(button);

                            botContext.getViberService().getButtonService().delete(button);
                            botContext.getUser().getManager().setButtons(buttons);
                        }

                        botContext.getViberService().getMessageService().sendSuccessfullyDeletedButtonMessage(botContext.getUser().getViberId());

                        nextState = EditBotMessageMenu;
                    }
                }
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
            botContext.getViberService().getMessageService().sendAddBotMessageButtonMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            String callbackData = StringUtils.EMPTY;

            Button newButton = new Button();
            newButton.setText(text);
            newButton.setIsLast(true);

            do {
                callbackData = botContext.getViberService().getButtonService().generateCallbackData();
            }
            while(botContext.getViberService().getButtonService().getByCallbackData(callbackData) != null);

            newButton.setCallbackData(callbackData);

            if (newButton.getManagers() != null) {
                newButton.getManagers().add(botContext.getUser().getManager());
            }
            else {
                Set<Manager> managers = new HashSet<>();
                managers.add(botContext.getUser().getManager());
                newButton.setManagers(managers);
            }

            botContext.getViberService().getButtonService().save(newButton);
            botContext.getUser().getManager().getButtons().add(newButton);

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
            botContext.getViberService().getMessageService().sendSelectAnswerTypeMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            Button lastButton = botContext.getUser().getManager().getButtons().stream().filter(b -> b.getIsLast() == true).findAny().orElse(null);

            if (lastButton == null) {
                throw new NullPointerException("Unable to find manager last button");
            }

            switch(text) {
                case "callback.botMessageMenu.selectAnswerType.negative": 
                    lastButton.setAnswerType(AnswerType.POSITIVE);
                    botContext.getViberService().getButtonService().save(lastButton);
                    nextState = SelectIsButtonEndsDialogue;
                    break;
                case "callback.botMessageMenu.selectAnswerType.positive":
                    lastButton.setAnswerType(AnswerType.NEGATIVE);
                    botContext.getViberService().getButtonService().save(lastButton);
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
            botContext.getViberService().getMessageService().sendIsButtonEndsDialogueMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            Button lastButton = botContext.getUser().getManager().getButtons().stream().filter(b -> b.getIsLast() == true).findAny().orElse(null);

            if (lastButton == null) {
                throw new NullPointerException("Unable to find manager last button");
            }

            switch (text) {
                case "Да": 
                    lastButton.setDialogueEnds(false);
                    lastButton.setIsLast(false);

                    botContext.getViberService().getButtonService().save(lastButton);
                    botContext.getViberService().getMessageService().sendSuccessfullyAddedButtonMessage(botContext.getUser().getViberId());

                    nextState = EditBotMessageMenu;
                    break;
                case "Нет": 
                    lastButton.setDialogueEnds(true);
                    lastButton.setIsLast(false);

                    botContext.getViberService().getButtonService().save(lastButton);
                    botContext.getViberService().getMessageService().sendSuccessfullyAddedButtonMessage(botContext.getUser().getViberId());

                    nextState = EditBotMessageMenu;
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

    //#endregion

    //#region BotUsagePeriod

    BotUsagePeriod(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendBotUsagePeriodMenuMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "В чате(время обработки)":
                    nextState = InChatChatBotUsagePeriod;
                    break;
                case "В ночное время":
                    nextState = AtNightBotUsagePeriod;
                    break;
                case "Назад":
                    nextState = Settings;
                    break;
                default:
                    nextState = BotUsagePeriod;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    InChatChatBotUsagePeriod(false) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendInChatBotUsagePeriodSettingsMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            nextState = BotUsagePeriod;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AtNightBotUsagePeriod(false) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendAtNightBotUsagePeriodSettingsMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            nextState = BotUsagePeriod;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    //#endregion

    //#endregion

    //#region PostponeMessage

    AddText(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            if (botContext.getUser() == null) {
                throw new NullPointerException("User is null");
            }

            botContext.getViberService().getMessageService().sendAddTextMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            PostponeMessage postponeMessage = new PostponeMessage();

            postponeMessage.setText(text);
            postponeMessage.setViberId(botContext.getUser().getViberId());
            postponeMessage.setIsLast(true);

            botContext.getViberService().getPostponeMessageService().save(postponeMessage);

            nextState = AddPhoto;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AddPhoto(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendAddPhotoMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Без фото":
                    nextState = SetDayAndTime;
                    break;
                default:
                    nextState = AddPhoto;
                    break;
            }
        }

        @Override
        public void handlePicture(BotContext botContext) {
            String pictureUrl = botContext.getMessage().getMedia();
            PostponeMessage postponeMessage = botContext.getViberService().getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            String filename = botContext.getViberService().getFileService().downloadPicture(pictureUrl);

            postponeMessage.setPictureUrl(botContext.getViberService().getPictureEndpoint() + filename);

            botContext.getViberService().getPostponeMessageService().update(postponeMessage);

            nextState = SetDayAndTime;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    SetDayAndTime(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendSelectDateAndTimeMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            PostponeMessage postponedMessage = botContext.getViberService().getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            ValidationResult validationResult = null;
            validationResult = Validator.validateDate(text);
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = SetDayAndTime;
            }
            else {
                try {
                    postponedMessage.setDate(new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(text));
    
                    botContext.getViberService().getPostponeMessageService().update(postponedMessage);
    
                    nextState = ConfirmationForSendMessage;
                } catch (ParseException e) {
                    nextState = SetDayAndTime;
                }
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    ConfirmationForSendMessage(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendConfirmPostponeMessageMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            PostponeMessage postponeMessage = botContext.getViberService().getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            switch (text) {
                case "Да":
                    postponeMessage.setIsLast(false);
                    nextState = SuccessPostponeMessageConfirmation;
                    break;
                case "Нет":
                    botContext.getViberService().getPostponeMessageService().delete(postponeMessage);
                    nextState = DeclinePostponeMessageConfirmation;
                    break;
                default:
                    nextState = ConfirmationForSendMessage;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    SuccessPostponeMessageConfirmation(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendSuccessPostponeMessageConfirmationMessage(botContext.getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    },


    DeclinePostponeMessageConfirmation(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendDeclinePostponeMessageConfirmationMessage(botContext.getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    },

    //#endregion

    //#region Managers

    ListOfManagers(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Managers;
        }
    },
    
    AddManager(true) {
        BotState nextState;
        
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {
            nextState = Managers;
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

        }

        @Override
        public void handleInput(BotContext botContext) {
            nextState = Managers;
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
            nextState = Managers;
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
            botContext.getViberService().getMessageService().sendListOfClientsMessage(botContext.getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return Clients;
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
            return Clients;
        }
    }, 

    //#endregion

    //#region Report

    ReportAboutManagersWork(false) {
        @Override
        public void enter(BotContext botContext) {

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
            botContext.getViberService().getMessageService().sendAskClientNameMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            Client client = botContext.getUser().getClient();

            client.setName(text);

            Dialogue currentDialogue = Dialogue.builder()
                                    .client(botContext.getUser().getClient())
                                    .dialogIsOver(false)
                                    .botMessagesLastUpdate(botContext.getViberService().getBotMessageService().getLastUpdate())
                                    .lastUpdate(new Date().getTime())
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
            botContext.getViberService().getMessageService().sendAskBrandMessage(botContext.getUser().getViberId(), 
                                                                                 botContext.getViberService().getCarService().getBrands(),
                                                                                 botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
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
                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());;

                if (currentDialogue == null && botContext.getUser().getClient() != null && botContext.getUser().getClient().getName() != null) {
                    currentDialogue = Dialogue.builder()
                                        .client(botContext.getUser().getClient())
                                        .dialogIsOver(false)
                                        .botMessagesLastUpdate(botContext.getViberService().getBotMessageService().getLastUpdate())
                                        .lastUpdate(new Date().getTime())
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
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            botContext.getViberService().getMessageService().sendAskModelMessage(botContext.getUser().getViberId(),
                                                                                 botContext.getViberService().getCarService().getModelsByBrand(currentDialogue.getBrand()),
                                                                                 botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());;

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
            botContext.getViberService().getMessageService().sendAskYearOfIssueFromMessage(botContext.getUser().getViberId(),
                                                                                           Utils.generateYears(1975),
                                                                                           botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)Utils.generateYears(1975).size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueFrom;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < Utils.generateYears(1975).size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueFrom;
            }
            else if (Utils.generateYears(1975).contains(text)) {
                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());;

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
            botContext.getViberService().getMessageService().sendAskYearOfIssueToMessage(botContext.getUser().getViberId(),
                                                                                           Utils.generateYears(1975),
                                                                                           botContext.getUser().getClient().getKeyboardPage());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            if (text.equals("Назад")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page > 1) {
                    page--;
                }
                else {
                    page = (int)Math.ceil((float)Utils.generateYears(1975).size() / (float)KeyboardSource.MAX_ITEMS_AT_PAGE);
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueTo;
            }
            else if (text.equals("Далее")) {
                Integer page = botContext.getUser().getClient().getKeyboardPage();

                if (page * KeyboardSource.MAX_ITEMS_AT_PAGE < Utils.generateYears(1975).size()) {
                    page++;
                }
                else {
                    page = 1;
                }

                botContext.getUser().getClient().setKeyboardPage(page);

                nextState = AskYearOfIssueTo;
            }
            else if (Utils.generateYears(1975).contains(text)) {
                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());
                
                currentDialogue.setBotMessage(botContext.getViberService().getBotMessageService().getFirstMessage());
                currentDialogue.setYearTo(Integer.parseInt(text));

                botContext.getUser().getClient().setKeyboardPage(1);
                botContext.getViberService().getDialogueService().save(currentDialogue);

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
            botContext.getViberService().getMessageService().sendNegativeDialogEndMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            if (text.equals("Начать новый диалог")) {
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
            botContext.getViberService().getMessageService().sendAskAndEnterPhoneNumberMessageAndKeyboard(botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            ValidationResult validationResult = Validator.validatePhoneNumber(text);
            
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage(), null, null);
                nextState = AskPhoneNumber;
                
            }
            else {
                Client client = botContext.getUser().getClient();
                client.setPhoneNumber(text);

                Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

                currentDialogue.setDialogIsOver(true);

                nextState = PositiveDialogEnd;

                botContext.getViberService().getDialogueService().save(currentDialogue);

                botContext.getViberService().getMessageService().sendPositiveDialogEndMessageAndKeyboard(botContext.getUser().getViberId());
            }
        }

        @Override
        public void handleContact(BotContext botContext) {
            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber();

            botContext.getUser().getClient().setPhoneNumber(phoneNumber);
            
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            currentDialogue.setDialogIsOver(true);

            nextState = PositiveDialogEnd;

            botContext.getViberService().getDialogueService().save(currentDialogue);

            botContext.getViberService().getMessageService().sendPositiveDialogEndMessageAndKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    PositiveDialogEnd(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {}

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            if (text.equals("Начать новый диалог")) {
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
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());
            
            if (currentDialogue == null) {
                nextState = AskBrand;
                return;
            }

            botContext.getViberService().getMessageService().sendBotMessageMessage(botContext.getUser().getViberId(), currentDialogue.getBotMessage(), botContext.getUser().getClient());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

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

                            currentDialogue.setDialogIsOver(true);
                            botContext.getViberService().getMessageService().sendPositiveDialogEndAndPhoneEnteredMessageAndKeyboard(botContext.getUser().getViberId());   
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

    BotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public static BotState getAdminInitialState() {
        return MainMenu;
    }

    public static BotState getUserInitialState() {
        return AskClientName;
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}
    public void handlePicture(BotContext botContext) {}
    public void handleContact(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
