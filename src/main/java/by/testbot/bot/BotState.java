package by.testbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.testbot.models.User;

public enum BotState {
    
    //region Main Menu
    MainMenu(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            if (botContext.getMessageCallback() != null) {
                botContext.getKeyboardService().sendAdminMainMenuKeyboard(botContext.getMessageCallback().getSender().getId());
            }
            if (botContext.getSubscribedCallback() != null) {
                botContext.getKeyboardService().sendAdminMainMenuKeyboard(botContext.getSubscribedCallback().getUser().getViberId());
            }
        }

        @Override 
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Отложенное сообщение": 
                    botState = PostponeMessage;
                    break;
                case "Список менеджеров":
                    botState = Managers;
                    break;
                case "Список клиентов": 
                    botState = Clients;
                    break;
                case "Отчет":
                    botState = Report;
                    break;
                case "Интеграции":
                    botState = Integrations;
                    break;
                case "Настройка":
                    botState = Settings;
                    break;
                default:
                    botState = MainMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    PostponeMessage(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendPostponeMessageMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Добавить текст + фото":
                    botState = AddTextAndPhoto;
                    break;
                case "Выбрать день и время":
                    botState = SetDayAndTime;
                    break;
                case "Подтвердить отправку":
                    botState = ConfirmationForSendMessage;
                    break;
                case "Назад":
                    botState = MainMenu;
                    break;
                default:
                    botState = PostponeMessage;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    Managers(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendListOfManagersMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Получить список менеджеров":
                    botState = ListOfManagers;
                    break;
                case "Добавить менеджера": 
                    botState = AddManager;
                    break;
                case "Удалить менеджера":
                    botState = DeleteManager;
                    break;
                case "Изменение привелегий":
                    botState = ChangeManagerPrivilegies;
                    break;
                case "Назад":
                    botState = MainMenu;
                    break;
                default:
                    botState = Managers;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    Clients(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendListOfClientsMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Получить список клиентов и информацию о них":
                    botState = GetListOfClients;
                    break;
                case "Дополнительные операции с клиентами":
                    botState = AdditionalOperationsWithClients;
                    break;
                case "Назад":
                    botState = MainMenu;
                    break;
                default: 
                    botState = Clients;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    Report(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendReportMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch (text) {
                case "Отчет о работе менеджеров": 
                    botState = ReportAboutManagersWork;
                    break;
                case "Отчет о работе бота":
                    botState = ReportAboutBotWork;
                    break;
                case "Назад":
                    botState = MainMenu;
                    break;
                default:
                    botState = Report;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    }, 
    
    Integrations(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendIntegrationsMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch (text) {
                case "Добавление новой интеграции": 
                    botState = AddIntegration;
                    break;
                case "Удаление интергации":
                    botState = DeleteIntegration;
                    break;
                case "Новые интеграции":
                    botState = NewIntegrations;
                    break;
                case "Назад":
                    botState = MainMenu;
                    break;
                default:
                    botState = Integrations;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    Settings(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendSettingsMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch (text) {
                case "Редактирование текстов, отправляемых клиентам через бота":
                    botState = EditTextSendedByBot;
                    break;
                case "Настройка периода временного использования бота":
                    botState = SetTimePeriodForBotActivity;
                    break;
                case "Назад":
                    botState = MainMenu;
                    break;
                default:
                    botState = Settings;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    //endregion

    //region Settings

    EditTextSendedByBot(true) {
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

    SetTimePeriodForBotActivity(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendBotUsagePeriodMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch (text) {
                case "В чате(время обработки)":
                    botState = InChatPeriodTime;
                    break;
                case "В ночное время":
                    botState = InNightTime;
                    break;
                case "Назад":
                    botState = Settings;
                    break;
                default:
                    botState = SetTimePeriodForBotActivity;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    InChatPeriodTime(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return SetTimePeriodForBotActivity;
        }
    },

    InNightTime(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return SetTimePeriodForBotActivity;
        }
    },

    //endregion

    //region PostponeMessage

    AddTextAndPhoto(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return PostponeMessage;
        }
    },

    SetDayAndTime(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return PostponeMessage;
        }
    },

    ConfirmationForSendMessage(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendConfirmPostponeMessageKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch (text) {
                case "Да":
                    botState = ConfirmButtonForSendMessage;
                    break;
                case "Нет":
                    botState = DeclineButtonForSendMessage;
                    break;
                case "Назад":
                    botState = PostponeMessage;
                    break;
                default:
                    botState = MainMenu;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return PostponeMessage;
        }
    },

    ConfirmButtonForSendMessage(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return PostponeMessage;
        }
    },


    DeclineButtonForSendMessage(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return PostponeMessage;
        }
    },
    //endregion

    //region Managers

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
    
    DeleteManager(true) {
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
    
    ChangeManagerPrivilegies(true) {
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


    //endregion
    
    //region Clients
    
    GetListOfClients(false) {
        @Override
        public void enter(BotContext botContext) {

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

    //endregion

    //region Report

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

    //endregion

    //region Integrations

    AddIntegration(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Integrations;
        }
    },

    DeleteIntegration(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Integrations;
        }
    },

    NewIntegrations(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Integrations;
        }
    }, 

    //endregion

    //region Settings
    
    ListOfMessagesWhichBotSend(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Settings;
        }
    }, 
    
    SetBotUsingPeriod(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendBotUsagePeriodMenuKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "В чате(время обработки)":
                    botState = InChatBotUsingPeriod;
                    break;
                case "В ночное время":
                    botState = AtNightChatBotUsingPeriod;
                    break;
                case "Назад":
                    botState = Settings;
                    break;
                default: 
                    botState = SetBotUsingPeriod;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    //endregion

    //region SetBotUsingPeriod

    InChatBotUsingPeriod(true) {
        @Override
        public void enter(BotContext botContext) {
            
        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return SetBotUsingPeriod;
        }
    },

    AtNightChatBotUsingPeriod(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return SetBotUsingPeriod;
        }
    },

    //endregion
    
    //region UserDialog

    StartUserDialogAndAskClientName(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            if (botContext.getMessageCallback() != null) {
                botContext.getMessageService().sendAskClientNameMessage(botContext.getMessageCallback().getSender().getId());
            }
            if (botContext.getSubscribedCallback() != null) {
                botContext.getMessageService().sendAskClientNameMessage(botContext.getSubscribedCallback().getUser().getViberId());
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            String tempName = botContext.getMessageCallback().getMessage().getText();

            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());
            
            if (user != null) {
                user.setTempName(tempName);
                botContext.getUserService().update(user);

                botState = IsHaveAnyBenefits;
            }
            else {
                logger.warn("User not found in state: " + this);

                botState = StartUserDialogAndAskClientName;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    IsHaveAnyBenefits(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());

            if (user != null) {
                botContext.getKeyboardService().sendIsHaveAnyBenefitsMessageKeyboard(botContext.getMessageCallback().getSender().getId(), user.getTempName());
            }
            else {
                logger.warn("User not found in state: " + this);
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Да":
                    botState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
                case "Нет":
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    botState = IsHaveAnyBenefits;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    AreInterestedToKnowAdditionalDataAboutCarsAtAuctions(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());
            // Null check
            botContext.getKeyboardService().sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageKeyboard(botContext.getMessageCallback().getSender().getId(), user.getTempName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Да":
                    botState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
                case "Нет":
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    botState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    NegativeDialogEnd(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendNegativeDialogEndMessageKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            if (text.equals("Начать новый диалог")) {
                botState = StartUserDialogAndAskClientName;
            }
            else {
                botState = NegativeDialogEnd;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    DontWorryAboutPricesAndIsLinkOpens(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());

            if (user != null) {
                botContext.getKeyboardService().sendDontWorryAboutPricesAndIsLinkOpensMessageKeyboard(botContext.getMessageCallback().getSender().getId(), user.getTempName());
            }
            else {
                logger.warn("User not found in state: " + this);
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Да":
                    botState = WhenArePlanningToBuyCar;
                    break;
                case "Нет":
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    botState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    WhenArePlanningToBuyCar(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());
            
            if (user != null) {
                botContext.getKeyboardService().sendWhenArePlanningToBuyCarMessageKeyboard(botContext.getMessageCallback().getSender().getId(), user.getTempName());
            }
            else {
                logger.warn("User not found in state: " + this);
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            if (text.equals("В ближайшее время") || text.equals("После НГ")) {
                //TODO: Set input
                botState = IsInterestedInSpecificCarVariants;
            }
            else {
                botState = WhenArePlanningToBuyCar;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    IsInterestedInSpecificCarVariants(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());
            
            if (user != null) {
                botContext.getKeyboardService().sendIsInterestedInSpecificCarVariantsMessageKeyboard(botContext.getMessageCallback().getSender().getId(), user.getTempName());
            }
            else {
                logger.warn("User not found in state: " + this);
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Да":
                    botState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
                case "Нет":
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    botState = IsInterestedInSpecificCarVariants;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    WillAskFewQuestionsRegardingYourCriteria(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            User user = botContext.getUserService().getByViberId(botContext.getMessageCallback().getSender().getId());
            // TODO: Null check
            botContext.getKeyboardService().sendWillAskFewQuestionsRegardingYourCriteriaMessageKeyboard(botContext.getMessageCallback().getSender().getId(), user.getTempName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            switch(text) {
                case "Да":
                    botState = AskAndEnterPhoneNumber;
                    break;
                case "Нет":
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    botState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },
    
    AskAndEnterPhoneNumber(true) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendAskAndEnterPhoneNumberMessage(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            //TODO: Set phone number
        }

        @Override
        public BotState nextState() {
            return PositiveDialogEnd;
        }
    },
    
    PositiveDialogEnd(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendPositiveDialogEndMessageKeyboard(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            if (text.equals("Начать новый диалог")) {
                botState = StartUserDialogAndAskClientName;
            }
            else {
                botState = PositiveDialogEnd;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    };

    //endregion

    private static final Logger logger = LoggerFactory.getLogger(BotState.class);

    private final Boolean isInputNeeded;

    BotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public static BotState getAdminInitialState() {
        return MainMenu;
    }

    public static BotState getUserInitialState() {
        return StartUserDialogAndAskClientName;
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
