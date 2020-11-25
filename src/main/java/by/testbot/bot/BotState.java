package by.testbot.bot;

import java.util.Arrays;

import by.testbot.models.Sender;
import by.testbot.payload.requests.message.SendTextMessageRequest;

public enum BotState {
    Subscribed(0, true) {
        @Override
        public void enter(BotContext botContext) {
            SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
            Sender sender = new Sender();

            sender.setName("AutoCapitalBot");
            
            sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
            sendTextMessageRequest.setText("Привет, ты только что подписался на меня.");
            sendTextMessageRequest.setSender(sender);

            botContext.getViberService().sendTextMessage(sendTextMessageRequest);
            // botContext.getKeyboardService().sendAdminMainMenuRichMediaKeyboard(botContext.getConversationStartedCallback().getUser().getViberId());
        }

        @Override
        public BotState nextState() {
            return EnterMessage;
        }
    },
    
    EnterMessage(1, true) {
        @Override
        public void enter(BotContext botContext) {
            SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
            Sender sender = new Sender();

            sender.setName("AutoCapitalBot");
            
            sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
            sendTextMessageRequest.setText("Введи любое сообщение(Стэйт EnterMessage)");
            sendTextMessageRequest.setSender(sender);

            botContext.getViberService().sendTextMessage(sendTextMessageRequest);
        }

        @Override
        public void handleInput(BotContext botContext) {
            SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
            Sender sender = new Sender();

            sender.setName("AutoCapitalBot");
            
            sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
            sendTextMessageRequest.setText("Ты ввел: " + botContext.getMessageCallback().getMessage().getText());
            sendTextMessageRequest.setSender(sender);

            botContext.getViberService().sendTextMessage(sendTextMessageRequest);
        }

        @Override
        public BotState nextState() {
            return EnterHelloMessage;
        }
    },
    
    EnterHelloMessage(2, true) {
        BotState botState;

        @Override
        public void enter(BotContext botContext) {
            SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
            Sender sender = new Sender();

            sender.setName("AutoCapitalBot");
            
            sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
            sendTextMessageRequest.setText("Сейчас пройдет только сообщение Hello(Стэйт EnterHelloMessage)");
            sendTextMessageRequest.setSender(sender);

            botContext.getViberService().sendTextMessage(sendTextMessageRequest);
        }

        @Override
        public void handleInput(BotContext botContext) {
            String text = botContext.getMessageCallback().getMessage().getText();

            if (text.equals("Hello")) {
                SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
                Sender sender = new Sender();

                sender.setName("AutoCapitalBot");
                
                sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
                sendTextMessageRequest.setText("Вы ввели Hello!");
                sendTextMessageRequest.setSender(sender);

                botContext.getViberService().sendTextMessage(sendTextMessageRequest);

                botState = BotState.EnterMessage;
            }
            else {
                SendTextMessageRequest sendTextMessageRequest = new SendTextMessageRequest();
                Sender sender = new Sender();

                sender.setName("AutoCapitalBot");
                
                sendTextMessageRequest.setUserId(botContext.getMessageCallback().getSender().getId());
                sendTextMessageRequest.setText("Неверный ввод!");
                sendTextMessageRequest.setSender(sender);

                botContext.getViberService().sendTextMessage(sendTextMessageRequest);

                botState = EnterHelloMessage;
            }
        }

        @Override
        public BotState nextState() {
            return botState;
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
        return Arrays.asList(BotState.values()).stream().filter(b -> b.getId().equals(id)).findFirst().orElse(Subscribed);
    }

    public Boolean getIsInputNeeded() { return isInputNeeded; }

    public void handleInput(BotContext botContext) {}

    public abstract void enter(BotContext botContext);
    public abstract BotState nextState();
}
