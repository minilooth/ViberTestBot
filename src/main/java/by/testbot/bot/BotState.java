package by.testbot.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import by.testbot.models.Client;
import by.testbot.models.Dialogue;
import by.testbot.models.PostponeMessage;
import by.testbot.validation.ValidationResult;
import by.testbot.validation.Validator;

public enum BotState {

    // region Main Menu
    MainMenu(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getViberService().getMessageService().sendAdminMainMenuMessageAndKeyboard(botContext.getUser().getViberId());
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
            botContext.getViberService().getMessageService().sendManagersMenuMessageAndKeyboard(botContext.getUser().getViberId());
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
            botContext.getViberService().getMessageService().sendClientsMenuMessageAndKeyboard(botContext.getUser().getViberId());
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
            botContext.getViberService().getMessageService().sendReportMenuMessageAndKeyboard(botContext.getUser().getViberId());
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
            botContext.getViberService().getMessageService().sendIntegrationsMenuMessageAndKeyboard(botContext.getUser().getViberId());
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
            botContext.getViberService().getMessageService().sendSettingsMenuMessageAndKeyboard(botContext.getUser().getViberId());
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

            postponeMessage.setPictureUrl(pictureUrl);

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
            String text = botContext.getMessage().getText();

            Client client = botContext.getUser().getClient();

            client.setName(text);

            Dialogue currentDialogue = Dialogue.builder()
                                    .client(botContext.getUser().getClient())
                                    .dialogIsOver(false)
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
            botContext.getViberService().getMessageService().sendAskBrandMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());;

            if (currentDialogue == null && botContext.getUser().getClient() != null && botContext.getUser().getClient().getName() != null) {
                currentDialogue = Dialogue.builder()
                                    .client(botContext.getUser().getClient())
                                    .dialogIsOver(false)
                                    .build();
            }

            if (botContext.getViberService().getCarService().getBrands().contains(text)) {
                currentDialogue.setBrand(text);
                nextState = AskModel;
            }
            else {
                nextState = AskBrand;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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

            botContext.getViberService().getMessageService().sendAskModelMessage(botContext.getUser().getViberId(), currentDialogue.getBrand());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            if (botContext.getViberService().getCarService().getModelsByBrand(currentDialogue.getBrand()).contains(text)) {
                currentDialogue.setModel(text);
                nextState = AskYearOfIssue;
            }
            else {
                nextState = AskModel;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
            botContext.getViberService().getMessageService().sendAskYearOfIssueMessage(botContext.getUser().getViberId());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            ValidationResult validationResult = Validator.validateYears(text);

            if (validationResult.getIsValid()) {
                String[] years = text.split("-");

                currentDialogue.setYearFrom(Integer.parseInt(years[0]));
                currentDialogue.setYearTo(Integer.parseInt(years[1]));
    
                botContext.getViberService().getDialogueService().save(currentDialogue);
                nextState = IsHaveAnyBenefits;
            }
            else {
                botContext.getViberService().getMessageService().sendTextMessage(botContext.getUser().getViberId(), validationResult.getMessage());
                nextState = AskYearOfIssue;
            }
            
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
            botContext.getViberService().getMessageService().sendIsHaveAnyBenefitsMessageAndKeyboard(botContext.getUser().getViberId(), botContext.getUser().getClient().getName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            switch(text) {
                case "Да":
                    currentDialogue.setStep1Answer(text);
                    nextState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
                case "Нет":
                    currentDialogue.setStep1Answer(text);
                    nextState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
                default: 
                    nextState = IsHaveAnyBenefits;
                    break;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
            botContext.getViberService().getMessageService().sendAreInterestedToKnowAdditionalDataAboutCarsAtAuctionsMessageAndKeyboard(botContext.getUser().getViberId(), botContext.getUser().getClient().getName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            switch(text) {
                case "Да":
                    currentDialogue.setStep2Answer(text);
                    nextState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
                case "Нет":
                    currentDialogue.setStep2Answer(text);
                    currentDialogue.setDialogIsOver(true);
                    nextState = NegativeDialogEnd;
                    break;
                default: 
                    nextState = AreInterestedToKnowAdditionalDataAboutCarsAtAuctions;
                    break;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
                    nextState = StartUserDialogAndAskClientName;
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
    
    DontWorryAboutPricesAndIsLinkOpens(true) {
        BotState nextState;

        @Override
        public void enter(BotContext botContext) {
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            String link = botContext.getViberService().getCarService().generateLink(currentDialogue.getBrand(), currentDialogue.getModel(), currentDialogue.getYearFrom(), currentDialogue.getYearTo());

            botContext.getViberService().getMessageService().sendDontWorryAboutPricesAndIsLinkOpensMessageAndKeyboard(botContext.getUser().getViberId(), botContext.getUser().getClient().getName(), link);
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            switch(text) {
                case "Да":
                    currentDialogue.setStep3Answer(text);
                    nextState = WhenArePlanningToBuyCar;
                    break;
                case "Нет":
                    currentDialogue.setStep3Answer(text);
                    currentDialogue.setDialogIsOver(true);
                    nextState = NegativeDialogEnd;
                    break;
                default: 
                    nextState = DontWorryAboutPricesAndIsLinkOpens;
                    break;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
            botContext.getViberService().getMessageService().sendWhenArePlanningToBuyCarMessageAndKeyboard(botContext.getUser().getViberId(), botContext.getUser().getClient().getName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            if (text.equals("В ближайшее время") || text.equals("После НГ")) {
                currentDialogue.setStep4Answer(text);
                nextState = IsInterestedInSpecificCarVariants;
            }
            else {
                nextState = WhenArePlanningToBuyCar;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
            botContext.getViberService().getMessageService().sendIsInterestedInSpecificCarVariantsMessageAndKeyboard(botContext.getUser().getViberId(), botContext.getUser().getClient().getName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            switch(text) {
                case "Да":
                    currentDialogue.setStep5Answer(text);
                    nextState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
                case "Нет":
                    currentDialogue.setStep5Answer(text);
                    currentDialogue.setDialogIsOver(true);
                    nextState = NegativeDialogEnd;
                    break;
                default:
                    nextState = IsInterestedInSpecificCarVariants;
                    break;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
            botContext.getViberService().getMessageService().sendWillAskFewQuestionsRegardingYourCriteriaMessageAndKeyboard(botContext.getUser().getViberId(), botContext.getUser().getClient().getName());
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();
            Dialogue currentDialogue = botContext.getViberService().getDialogueService().getCurrentDialogByClient(botContext.getUser().getClient());

            switch(text) {
                case "Да":
                    currentDialogue.setStep6Answer(text);

                    if (botContext.getUser().getClient() != null && botContext.getUser().getClient().getPhoneNumber() != null) {
                        nextState = PositiveDialogEnd;

                        currentDialogue.setDialogIsOver(true);

                        botContext.getViberService().getMessageService().sendPositiveDialogEndAndPhoneEnteredMessageAndKeyboard(botContext.getUser().getViberId());   
                    }
                    else {
                        nextState = AskAndEnterPhoneNumber;
                    }

                    break;
                case "Нет":
                    currentDialogue.setStep6Answer(text);
                    currentDialogue.setDialogIsOver(true);
                    nextState = NegativeDialogEnd;
                    break;
                default:
                    nextState = WillAskFewQuestionsRegardingYourCriteria;
                    break;
            }

            botContext.getViberService().getDialogueService().save(currentDialogue);
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
            botContext.getViberService().getMessageService().sendAskAndEnterPhoneNumberMessageAndKeyboard(botContext.getUser().getViberId());
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
        public void enter(BotContext botContext) {
            if (botContext.getUser().getClient() != null && botContext.getUser().getClient().getName() != null) {
                 
            }
            else {
                
            }
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessage().getText();

            if (text.equals("Начать новый диалог")) {
                if (botContext.getUser().getClient().getName() != null) {
                    nextState = AskBrand;
                }
                else {
                    nextState = StartUserDialogAndAskClientName;
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
