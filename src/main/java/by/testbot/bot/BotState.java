package by.testbot.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.testbot.models.ClientMessage;
import by.testbot.models.PostponeMessage;
import by.testbot.models.User;
import by.testbot.validation.ValidationResult;
import by.testbot.validation.Validator;

public enum BotState {

    // region Main Menu
    MainMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendAdminMainMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Отложенное сообщение":
                    nextState = AddText;
                    break;
                case "Список менеджеров":
                    nextState = Managers;
                    break;
                case "Список клиентов":
                    nextState = Clients;
                    break;
                case "Отчет":
                    nextState = Report;
                    break;
                case "Интеграции":
                    nextState = Integrations;
                    break;
                case "Настройки":
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
            botContext.getViberService().getKeyboardService().sendManagersMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Получить список менеджеров":
                    nextState = ListOfManagers;
                    break;
                case "Добавить менеджера":
                    nextState = AddManager;
                    break;
                case "Удалить менеджера":
                    nextState = DeleteManager;
                    break;
                case "Изменение привелегий":
                    nextState = ChangeManagerPrivilegies;
                    break;
                case "Назад":
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
            botContext.getViberService().getKeyboardService().sendClientsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Получение списка клиентов и их информацию, которую получил бот":
                    nextState = GetListOfClients;
                    break;
                case "Дополнительные операции с клиентами":
                    nextState = AdditionalOperationsWithClients;
                    break;
                case "Назад":
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
            botContext.getViberService().getKeyboardService().sendReportMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Получение отчёта о работе менеджеров":
                    nextState = ReportAboutManagersWork;
                    break;
                case "Получение отчета о работе бота":
                    nextState = ReportAboutBotWork;
                    break;
                case "Назад":
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
            botContext.getViberService().getKeyboardService().sendIntegrationsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Добавление/удаление новой интеграции(AmoCRM) = ввод токена для соединения":
                    nextState = AddOrDeleteIntegration;
                    break;
                // case "Удаление интергации":
                // botState = DeleteIntegration;
                // break;
                case "Новые интеграции":
                    nextState = NewIntegrations;
                    break;
                case "Назад":
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
            botContext.getViberService().getKeyboardService().sendSettingsMenuKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            switch (text) {
                case "Редактирование текстов, отправляемых клиентам через бота":
                    nextState = EditTextsWhitchBotSend;
                    break;
                case "Настройка периода временного использования бота":
                    nextState = BotUsagePeriod;
                    break;
                case "Назад":
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

    // endregion

    // region Settings

    EditTextsWhitchBotSend(false) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendListOfMessagesWhichBotSendMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            nextState = Settings;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    BotUsagePeriod(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendBotUsagePeriodMenuKeyboard(botContext.getUser().getViberId());
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

    // endregion

    // region PostponeMessage

    AddText(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
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
            botContext.getViberService().getKeyboardService().sendAddPhotoMessageKeyboard(botContext.getUser().getViberId());
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

            postponeMessage.setPhotoFilename(pictureUrl);

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
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage());
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
            botContext.getViberService().getKeyboardService().sendConfirmPostponeMessageKeyboard(botContext.getUser().getViberId());
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
                case "Назад":
                    botContext.getViberService().getPostponeMessageService().delete(postponeMessage);
                    nextState = MainMenu;
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
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendAskClientNameMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String treatName = botContext.getMessage().getText();

            User user = botContext.getUser();

            user.setTreatName(treatName);

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
            botContext.getViberService().getKeyboardService().sendAskBrandMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Пропустить": 
                    nextState = IsHaveAnyBenefits;
                    break;
                default:
                    clientMessage.setBrand(text);
                    nextState = AskModel;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
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
            ClientMessage clientMessage = botContext.getViberService().getClientMessageService().getLastByViberId(botContext.getUser().getViberId());

            botContext.getViberService().getKeyboardService().sendAskModelMessageKeyboard(botContext.getUser().getViberId(), clientMessage.getBrand());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Пропустить": 
                    nextState = AskYearOfIssue;
                    break;
                default:
                    clientMessage.setModel(text);
                    nextState = AskYearOfIssue;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    AskYearOfIssue(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendAskYearOfIssueMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Пропустить":
                    break;
                default:
                    String[] years = text.split("-");

                    clientMessage.setYearOfIssueFrom(Integer.parseInt(years[0]));
                    clientMessage.setYearOfIssueTo(Integer.parseInt(years[1]));
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
            nextState = IsHaveAnyBenefits;
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },

    IsHaveAnyBenefits(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendIsHaveAnyBenefitsMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Да":
                    clientMessage.setStep1(text);
                    nextState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
                case "Нет":
                    clientMessage.setStep1(text);
                    nextState = NegativeDialogEnd;
                    break;
                default: 
                    clientMessage.setText(text);
                    nextState = IsHaveAnyBenefits;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    AreInterestedToKnowAdditionalDataAboutCarsAtAuctions(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Да":
                    clientMessage.setStep2(text);
                    nextState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
                case "Нет":
                    clientMessage.setStep2(text);
                    nextState = NegativeDialogEnd;
                    break;
                default: 
                    clientMessage.setText(text);
                    nextState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
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
            botContext.getViberService().getKeyboardService().sendNegativeDialogEndMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .step7(text)
                                                .build();

            botContext.getViberService().getClientMessageService().save(clientMessage);

            if (text.equals("Начать новый диалог")) {
                nextState = StartUserDialogAndAskClientName;
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
    
    DontWorryAboutPricesAndIsLinkOpens(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            ClientMessage brandMessage = botContext.getViberService().getClientMessageService().getBrandMessageByViberId(botContext.getUser().getViberId()); 
            ClientMessage modelMessage = botContext.getViberService().getClientMessageService().getModelMessageByViberId(botContext.getUser().getViberId());
            ClientMessage yearMessage = botContext.getViberService().getClientMessageService().getYearsMessageByViberId(botContext.getUser().getViberId());

            String link = botContext.getViberService().getCarService().generateLink(brandMessage.getBrand(), modelMessage.getModel(), yearMessage.getYearOfIssueFrom(), yearMessage.getYearOfIssueTo());

            botContext.getViberService().getKeyboardService().sendDontWorryAboutPricesAndIsLinkOpensMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName(), link);
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Да":
                    clientMessage.setStep3(text);
                    nextState = WhenArePlanningToBuyCar;
                    break;
                case "Нет":
                    clientMessage.setStep3(text);
                    nextState = NegativeDialogEnd;
                    break;
                default: 
                    clientMessage.setText(text);
                    nextState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    WhenArePlanningToBuyCar(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendWhenArePlanningToBuyCarMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            if (text.equals("В ближайшее время") || text.equals("После НГ")) {
                clientMessage.setStep4(text);
                nextState = IsInterestedInSpecificCarVariants;
            }
            else {
                clientMessage.setText(text);
                nextState = WhenArePlanningToBuyCar;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    IsInterestedInSpecificCarVariants(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendIsInterestedInSpecificCarVariantsMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Да":
                    clientMessage.setStep5(text);
                    nextState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
                case "Нет":
                    clientMessage.setStep5(text);
                    nextState = NegativeDialogEnd;
                    break;
                default:
                    clientMessage.setText(text); 
                    nextState = IsInterestedInSpecificCarVariants;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    WillAskFewQuestionsRegardingYourCriteria(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendWillAskFewQuestionsRegardingYourCriteriaMessageKeyboard(botContext.getUser().getViberId(), botContext.getUser().getTreatName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            switch(text) {
                case "Да":
                    clientMessage.setStep6(text);
                    nextState = AskAndEnterPhoneNumber;
                    break;
                case "Нет":
                    clientMessage.setStep6(text);
                    nextState = NegativeDialogEnd;
                    break;
                default:
                    clientMessage.setText(text); 
                    nextState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
        }
    },
    
    AskAndEnterPhoneNumber(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getKeyboardService().sendAskAndEnterPhoneNumberMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            ValidationResult validationResult = Validator.validatePhoneNumber(text);
            
            if (!validationResult.getIsValid()) {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage());
                nextState = AskAndEnterPhoneNumber;
            }
            else {
                botContext.getUser().setMobilePhone(text);
                nextState = PositiveDialogEnd;
            }
        }

        @Override
        public void handleContact(BotContext botContext) {
            String phoneNumber = botContext.getMessage().getContact().getPhoneNumber();

            botContext.getUser().setMobilePhone(phoneNumber);
            
            nextState = PositiveDialogEnd;
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
            botContext.getViberService().getKeyboardService().sendPositiveDialogEndMessageKeyboard(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            ClientMessage clientMessage = ClientMessage.builder()
                                                .viberId(botContext.getUser().getViberId())
                                                .timestamp(new Date().getTime())
                                                .build();

            if (text.equals("Начать новый диалог")) {
                clientMessage.setStep7(text);
                nextState = StartUserDialogAndAskClientName;
            }
            else {
                clientMessage.setText(text);
                nextState = PositiveDialogEnd;
            }

            botContext.getViberService().getClientMessageService().save(clientMessage);
        }

        @Override
        public BotState nextState() {
            return nextState;
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
    public void handleContact(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
