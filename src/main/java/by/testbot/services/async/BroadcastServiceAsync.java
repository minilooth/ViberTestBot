package by.testbot.services.async;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import by.testbot.models.PostponeMessage;
import by.testbot.models.User;
import by.testbot.models.viber.Failed;
import by.testbot.services.MessageService;
import by.testbot.services.PostponeMessageService;
import by.testbot.services.UserService;

@Service
public class BroadcastServiceAsync {
    private static final Logger logger = LoggerFactory.getLogger(BroadcastServiceAsync.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PostponeMessageService postponeMessageService;

    @PersistenceContext
    private EntityManager entityManager;

    private Boolean isWorking = false;

    @Async
    public void runAsync() {
        if (!isWorking) {
            isWorking = true;
            idleMessagesAsync();
        }
    }

    @Async
    private void idleMessagesAsync() {
        logger.info("Postpone message service started");
        while(true) {
            List<PostponeMessage> postponeMessages = postponeMessageService.getAllWithoutLast(); 
            List<Failed> failedList = new ArrayList<>();

            LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            for(PostponeMessage postponeMessage : postponeMessages) {
                LocalDateTime messageDateTime = postponeMessage.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                if (localDateTime.isEqual(messageDateTime)) {
                    List<User> users = userService.getAll();

                    List<String> broadcastList = new ArrayList<>();

                    for(User user : users) {
                        broadcastList.add(user.getViberId());
                        if (broadcastList.size() == 300 || broadcastList.size() == users.size()) {
                            if (postponeMessage.getPictureUrl() != null) {
                                if (postponeMessage.getText().length() > 120) {
                                    failedList.addAll(messageService.sendPictureMessageToAll(broadcastList, StringUtils.EMPTY, postponeMessage.getPictureUrl()));
                                    failedList.addAll(messageService.sendTextMessageToAll(broadcastList, postponeMessage.getText()));
                                }
                                else {
                                    failedList = messageService.sendPictureMessageToAll(broadcastList, postponeMessage.getText(), postponeMessage.getPictureUrl());
                                }
                            }
                            else {
                                failedList = messageService.sendTextMessageToAll(broadcastList, postponeMessage.getText());
                            }

                            if (!failedList.isEmpty()) {
                                for (Failed failed : failedList) {
                                    logger.info("Message not sended to " + failed.getUserId() + ". Status: " + failed.getStatus() + ", reason: " + failed.getStatusMessage());
                                }
                            }

                            broadcastList.clear();
                        }
                    }
                }
            }
            try {
                Thread.sleep(60000);
            }
            catch (InterruptedException ex) {
                logger.info("Postpone message service stopped: " + ex.getMessage());
            }
        }
    }
}
