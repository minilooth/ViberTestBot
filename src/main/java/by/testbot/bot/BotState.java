package by.testbot.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.Date;

import by.testbot.models.ClientChatMessageHistory;
import by.testbot.models.PostponeMessage;
import by.testbot.models.User;

public enum BotState {

    // region Main Menu
    MainMenu(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendAdminMainMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Отложенное сообщение":
                    botState = AddText;
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
                case "Настройки":
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

    // PostponeMessage(true) {
    //     BotState botState;

    //     @Override
    //     public void enter(BotContext botContext) {
    //         botContext.getKeyboardService().sendPostponeMessageMenuKeyboard(botContext.getUser().getViberId());
    //     }

    //     @Override
    //     public void handleInput(BotContext botContext) {
    //         String text = botContext.getMessage().getText();

    //         switch (text) {
    //             case "Добавить текст + фото":
    //                 botState = AddTextAndPhoto;
    //                 break;
    //             case "Выбрать день и время":
    //                 botState = SetDayAndTime;
    //                 break;
    //             case "Подтвердить отправку":
    //                 botState = ConfirmationForSendMessage;
    //                 break;
    //             case "Назад":
    //                 botState = MainMenu;
    //                 break;
    //             default:
    //                 botState = PostponeMessage;
    //                 break;
    //         }
    //     }

    //     @Override
    //     public BotState nextState() {
    //         return botState;
    //     }
    // },

    Managers(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendManagersMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
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
            botContext.getKeyboardService().sendClientsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Получение списка клиентов и их информацию, которую получил бот":
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
            botContext.getKeyboardService().sendReportMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Получение отчёта о работе менеджеров":
                    botState = ReportAboutManagersWork;
                    break;
                case "Получение отчета о работе бота":
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
            botContext.getKeyboardService().sendIntegrationsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Добавление/удаление новой интеграции(AmoCRM) = ввод токена для соединения":
                    botState = AddOrDeleteIntegration;
                    break;
                // case "Удаление интергации":
                // botState = DeleteIntegration;
                // break;
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
            botContext.getKeyboardService().sendSettingsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Редактирование текстов, отправляемых клиентам через бота":
                    botState = EditTextsWhitchBotSend;
                    break;
                case "Настройка периода временного использования бота":
                    botState = BotUsagePeriod;
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

    // endregion

    // region Settings

    EditTextsWhitchBotSend(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendListOfMessagesWhichBotSendMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return Settings;
        }
    },

    BotUsagePeriod(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendBotUsagePeriodMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "В чате(время обработки)":
                    botState = InChatChatBotUsagePeriod;
                    break;
                case "В ночное время":
                    botState = AtNightBotUsagePeriod;
                    break;
                case "Назад":
                    botState = Settings;
                    break;
                default:
                    botState = BotUsagePeriod;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    InChatChatBotUsagePeriod(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendInChatBotUsagePeriodSettingsMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return BotUsagePeriod;
        }
    },

    AtNightBotUsagePeriod(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendAtNightBotUsagePeriodSettingsMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return BotUsagePeriod;
        }
    },

    // endregion

    // region PostponeMessage

    AddText(true) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendAddTextMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            PostponeMessage postponeMessage = new PostponeMessage();

            postponeMessage.setText(text);
            postponeMessage.setViberId(botContext.getUser().getViberId());
            postponeMessage.setIsLast(true);

            botContext.getPostponeMessageService().save(postponeMessage);
        }

        @Override
        public BotState nextState() {
            return AddPhoto;
        }
    },

    AddPhoto(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendAddPhotoMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Без фото":
                    botState = SetDayAndTime;
                    break;
                default:
                    botState = AddPhoto;
                    break;
            }
        }

        @Override
        public void handlePicture(BotContext botContext) {
            String pictureUrl = botContext.getMessage().getMedia();
            PostponeMessage postponeMessage = botContext.getPostponeMessageService()
                    .getLastByViberId(botContext.getUser().getViberId());

            postponeMessage.setPhotoFilename(pictureUrl);

            botContext.getPostponeMessageService().update(postponeMessage);

            botState = SetDayAndTime;
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    SetDayAndTime(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendSelectDateAndTimeMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            PostponeMessage postponedMessage = botContext.getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            try {
                postponedMessage.setDate(new SimpleDateFormat("dd.MM.yyyy hh:ss").parse(text));

                botContext.getPostponeMessageService().update(postponedMessage);

                botState = ConfirmationForSendMessage;
            } catch (ParseException e) {
                botState = SetDayAndTime;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    ConfirmationForSendMessage(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendConfirmPostponeMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            PostponeMessage postponeMessage = botContext.getPostponeMessageService().getLastByViberId(botContext.getUser().getViberId());

            switch (text) {
                case "Да":
                    postponeMessage.setIsLast(false);
                    botState = SuccessPostponedMessageConfirmation;
                    break;
                case "Нет":
                    botContext.getPostponeMessageService().delete(postponeMessage);
                    botState = DeclinePostponedMessageConfirmation;
                    break;
                case "Назад":
                    botContext.getPostponeMessageService().delete(postponeMessage);
                    botState = MainMenu;
                    break;
                default:
                    botState = ConfirmationForSendMessage;
                    break;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
        }
    },

    SuccessPostponedMessageConfirmation(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendSuccessPostponedMessageMessage(botContext.getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    },


    DeclinePostponedMessageConfirmation(false) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendDeclinePostponeMessageMessage(botContext.getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return MainMenu;
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

    //endregion
    
    //region UserDialog

    StartUserDialogAndAskClientName(true) {
        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendAskClientNameMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String treatName = botContext.getMessage().getText();

            User user = botContext.getUser();

            user.setTreatName(treatName);
        }

        @Override
        public BotState nextState() {
            return IsHaveAnyBenefits;
        }
    },
    
    IsHaveAnyBenefits(true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getKeyboardService().sendIsHaveAnyBenefitsMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            switch(text) {
                case "Да":
                    clientChatMessageHistory.setStep1(text);
                    botState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
                case "Нет":
                    clientChatMessageHistory.setStep1(text);
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    clientChatMessageHistory.setText(text);
                    botState = IsHaveAnyBenefits;
                    break;
            }

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);
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
            botContext.getKeyboardService().sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            switch(text) {
                case "Да":
                    clientChatMessageHistory.setStep2(text);
                    botState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
                case "Нет":
                    clientChatMessageHistory.setStep2(text);
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    clientChatMessageHistory.setText(text);
                    botState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
            }

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);
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
            botContext.getKeyboardService().sendNegativeDialogEndMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());
            clientChatMessageHistory.setStep7(text);

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);

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
            botContext.getKeyboardService().sendDontWorryAboutPricesAndIsLinkOpensMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            switch(text) {
                case "Да":
                    clientChatMessageHistory.setStep3(text);
                    botState = WhenArePlanningToBuyCar;
                    break;
                case "Нет":
                    clientChatMessageHistory.setStep3(text);
                    botState = NegativeDialogEnd;
                    break;
                default: 
                    clientChatMessageHistory.setText(text);
                    botState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
            }

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);
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
            botContext.getKeyboardService().sendWhenArePlanningToBuyCarMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            if (text.equals("В ближайшее время") || text.equals("После НГ")) {
                clientChatMessageHistory.setStep4(text);
                botState = IsInterestedInSpecificCarVariants;
            }
            else {
                clientChatMessageHistory.setText(text);
                botState = WhenArePlanningToBuyCar;
            }

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);
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
            botContext.getKeyboardService().sendIsInterestedInSpecificCarVariantsMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            switch(text) {
                case "Да":
                    clientChatMessageHistory.setStep5(text);
                    botState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
                case "Нет":
                    clientChatMessageHistory.setStep5(text);
                    botState = NegativeDialogEnd;
                    break;
                default:
                    clientChatMessageHistory.setText(text); 
                    botState = IsInterestedInSpecificCarVariants;
                    break;
            }

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);
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
            botContext.getKeyboardService().sendWillAskFewQuestionsRegardingYourCriteriaMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            switch(text) {
                case "Да":
                    clientChatMessageHistory.setStep6(text);
                    botState = AskAndEnterPhoneNumber;
                    break;
                case "Нет":
                    clientChatMessageHistory.setStep6(text);
                    botState = NegativeDialogEnd;
                    break;
                default:
                    clientChatMessageHistory.setText(text); 
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
            botContext.getMessageService().sendAskAndEnterPhoneNumberMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            botContext.getUser().setMobilePhone(text);
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
            botContext.getKeyboardService().sendPositiveDialogEndMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientChatMessageHistory clientChatMessageHistory = new ClientChatMessageHistory();

            clientChatMessageHistory.setViberId(botContext.getUser().getViberId());
            clientChatMessageHistory.setTimestamp(new Date().getTime());

            if (text.equals("Начать новый диалог")) {
                clientChatMessageHistory.setStep7(text);
                botState = StartUserDialogAndAskClientName;
            }
            else {
                clientChatMessageHistory.setText(text);
                botState = PositiveDialogEnd;
            }

            botContext.getClientChatMessageHistoryService().save(clientChatMessageHistory);
        }

        @Override
        public BotState nextState() {
            return botState;
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
        return StartUserDialogAndAskClientName;
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}
    public void handlePicture(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
