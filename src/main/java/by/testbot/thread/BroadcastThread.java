// package by.testbot.thread;

// import org.springframework.context.annotation.Scope;
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.transaction.support.TransactionSynchronizationManager;

// import java.time.LocalDateTime;
// import java.time.ZoneId;
// import java.time.temporal.ChronoUnit;
// import java.util.ArrayList;
// import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;

// import by.testbot.services.UserService;
// import by.testbot.services.MessageService;
// import by.testbot.services.PostponeMessageService;
// import by.testbot.models.PostponeMessage;
// import by.testbot.models.User;

// @Component
// @Scope("prototype")
// public class BroadcastThread implements Runnable {
//     private static final Logger logger = LoggerFactory.getLogger(BroadcastThread.class);

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private MessageService messageService;

//     @Autowired
//     private PostponeMessageService postponeMessageService;

//     private Boolean isWorking = false;

//     @Override
//     public void run() {
//         if (!isWorking) {
//             isWorking = true;
//             idleMessages();
//         }
//     }

//     @Transactional
//     private void idleMessages() {
//         logger.info("Postpone message service started");
//         while(true) {
//             TransactionSynchronizationManager.setActualTransactionActive(true);

//             LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

//             List<PostponeMessage> postponeMessages = null;
            
//             synchronized(this.postponeMessageService) {
//                 postponeMessages = postponeMessageService.getAll(); 
//             }
            
//             for(PostponeMessage postponeMessage : postponeMessages) {
//                 LocalDateTime messageDateTime = postponeMessage.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

//                 if (localDateTime.isEqual(messageDateTime) && postponeMessage.getIsLast() != null) {
//                     List<User> users = null;

//                     synchronized(this.userService) {
//                         users = userService.getAll();
//                     }
                    
//                     List<String> broadcastList = new ArrayList<>();

//                     for(User user : users) {
//                         broadcastList.add(user.getViberId());
//                         if (broadcastList.size() == 300 || broadcastList.size() == users.size()) {
//                             synchronized(this.messageService) {
//                                 if (postponeMessage.getPictureUrl() != null) {
//                                     messageService.sendPictureMessageToAll(broadcastList, postponeMessage.getText(), postponeMessage.getPictureUrl());
//                                 }
//                                 else {
//                                     messageService.sendTextMessageToAll(broadcastList, postponeMessage.getText());
//                                 }
//                             }
//                             broadcastList.clear();
//                         }
//                     }

//                 }
//             }
//             try {
//                 Thread.sleep(60000);
//             }
//             catch (InterruptedException ex) {
//                 logger.info("Postpone message service stopped: " + ex.getMessage());
//             }
//         }
//     }
// }
