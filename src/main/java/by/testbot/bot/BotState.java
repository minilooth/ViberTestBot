package by.testbot.bot;

public enum BotState {
    
    //region Main Menu
    MainMenu(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendAdminMainMenuKeyboard(botContext.getMessageCallback().getSender().getId());
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
    //end region

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
    //end region

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
    
    StartUserDialog(true) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendUserStartMessage(botContext.getMessageCallback().getSender().getId());
        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return StartUserDialog;
        }
    };

    //endregion

    private final Boolean isInputNeeded;

    BotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public static BotState getAdminInitialState() {
        return MainMenu;
    }

    public static BotState getUserInitialState() {
        return StartUserDialog;
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
