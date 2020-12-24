package by.testbot.bot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.testbot.config.EnvironmentConfig;
import by.testbot.services.AnswerService;
import by.testbot.services.BotMessageService;
import by.testbot.services.ButtonService;
import by.testbot.services.CarService;
import by.testbot.services.ClientService;
import by.testbot.services.DialogueService;
import by.testbot.services.ManagerService;
import by.testbot.services.MessageService;
import by.testbot.services.PostponeMessageService;
import by.testbot.services.UserService;
import by.testbot.services.file.ExcelService;
import by.testbot.services.file.FileService;
import by.testbot.services.other.ConfigService;

@Component
public class BotStateInjector {
    @Autowired
    private MessageService messageService;

    @Autowired
    private BotMessageService botMessageService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private PostponeMessageService postponeMessageService;

    @Autowired
    private ButtonService buttonService;

    @Autowired
    private CarService carService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private AnswerService answerService;

    @PostConstruct
    public void postConstruct() {
        BotState.setMessageService(messageService);
        BotState.setBotMessageService(botMessageService);
        BotState.setClientService(clientService);
        BotState.setManagerService(managerService);
        BotState.setPostponeMessageService(postponeMessageService);
        BotState.setButtonService(buttonService);
        BotState.setCarService(carService);
        BotState.setFileService(fileService);
        BotState.setUserService(userService);
        BotState.setEnvironmentConfig(environmentConfig);
        BotState.setConfigService(configService);
        BotState.setExcelService(excelService);
        BotState.setDialogueService(dialogueService);
        BotState.setAnswerService(answerService);
    }
}
