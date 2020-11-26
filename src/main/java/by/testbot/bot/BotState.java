package by.testbot.bot;

import java.util.Arrays;

public enum BotState {
    
    // Main Menu
    MainMenu(0, true) {
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
    
    PostponeMessage(1, true) {
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
    
    Managers(2, true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            botContext.getMessageService().sendListOfManagersMessage(botContext.getMessageCallback().getSender().getId());
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
    
    ClientsList(3, true) {
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
    
    Report(4, true) {
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
    
    Integrations(5, true) {
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
    
    Settings(6, true) {
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
    ListOfManagers(7, false) {
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
    
    AddManager(8, true) {
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
    
    DeleteManager(9, true) {
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
    
    ChangeManagerPrivilegies(10, true) {
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
    
    ManagersBack(11, false) {
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
    private final Integer id;

    BotState(Integer id, Boolean isInputNeeded) {
        this.id = id;
        this.isInputNeeded = isInputNeeded;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public Integer getId() {
        return id;
    }

    public static BotState byId(Integer id) {
        return Arrays.asList(BotState.values()).stream().filter(b -> b.getId().equals(id)).findFirst().orElse(MainMenu);
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
