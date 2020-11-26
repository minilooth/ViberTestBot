package by.testbot.bot;

public enum BotState {
    
    // Main Menu
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
                    botState = ClientsList;
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
    
    // EnterMessage(true) {
    //     @Override
    //     public void enter(BotContext botContext) {
    //         SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
    //         Sender sender = new Sender();

    //         sender.setName("AutoCapitalBot");
            
    //         sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
    //         sendTextMessageRequest.setText("Введи любое сообщение(Стэйт EnterMessage)");
    //         sendTextMessageRequest.setSender(sender);

    //         botContext.getViberService().sendTextMessage(sendTextMessageRequest);
    //     }

    //     @Override
    //     public void handleInput(BotContext botContext) {
    //         SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
    //         Sender sender = new Sender();

    //         sender.setName("AutoCapitalBot");
            
    //         sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
    //         sendTextMessageRequest.setText("Ты ввел: " + botContext.getMessageCallback().getMessage().getText());
    //         sendTextMessageRequest.setSender(sender);

    //         botContext.getViberService().sendTextMessage(sendTextMessageRequest);
    //     }

    //     @Override
    //     public BotState nextState() {
    //         return EnterHelloMessage;
    //     }
    // },
    
    // EnterHelloMessage(true) {
    //     BotState botState;

    //     @Override
    //     public void enter(BotContext botContext) {
    //         SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
    //         Sender sender = new Sender();

    //         sender.setName("AutoCapitalBot");
            
    //         sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
    //         sendTextMessageRequest.setText("Сейчас пройдет только сообщение Hello(Стэйт EnterHelloMessage)");
    //         sendTextMessageRequest.setSender(sender);

    //         botContext.getViberService().sendTextMessage(sendTextMessageRequest);
    //     }

    //     @Override
    //     public void handleInput(BotContext botContext) {
    //         String text = botContext.getMessageCallback().getMessage().getText();

    //         if (text.equals("Hello")) {
    //             SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
    //             Sender sender = new Sender();

    //             sender.setName("AutoCapitalBot");
                
    //             sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
    //             sendTextMessageRequest.setText("Вы ввели Hello!");
    //             sendTextMessageRequest.setSender(sender);

    //             botContext.getViberService().sendTextMessage(sendTextMessageRequest);

    //             botState = BotState.EnterMessage;
    //         }
    //         else {
    //             SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
    //             Sender sender = new Sender();

    //             sender.setName("AutoCapitalBot");
                
    //             sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
    //             sendTextMessageRequest.setText("Неверный ввод!");
    //             sendTextMessageRequest.setSender(sender);

    //             botContext.getViberService().sendTextMessage(sendTextMessageRequest);

    //             botState = EnterHelloMessage;
    //         }
    //     }

    //     @Override
    //     public BotState nextState() {
    //         return botState;
    //     }
    // },
    
    PostponeMessage(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return MainMenu;
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
    
    ClientsList(true) {
        @Override
        public void enter(BotContext botContext) {
            
        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    },
    
    Report(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    }, 
    
    Integrations(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    },
    
    Settings(true) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    },
    



    // Managers
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
    
    ManagersBack(false) {
        @Override
        public void enter(BotContext botContext) {

        }

        @Override
        public void handleInput(BotContext botContext) {

        }

        @Override
        public BotState nextState() {
            return MainMenu;
        }
    };

    private final Boolean isInputNeeded;

    BotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public static BotState getInitialState() {
        return MainMenu;
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
