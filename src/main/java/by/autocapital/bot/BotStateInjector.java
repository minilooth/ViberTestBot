package by.autocapital.bot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.autocapital.config.EnvironmentConfig;
import by.autocapital.services.AnswerService;
import by.autocapital.services.BotMessageService;
import by.autocapital.services.ButtonService;
import by.autocapital.services.CarService;
import by.autocapital.services.ClientService;
import by.autocapital.services.DialogueService;
import by.autocapital.services.ManagerService;
import by.autocapital.services.MessageService;
import by.autocapital.services.PostponeMessageService;
import by.autocapital.services.UserService;
import by.autocapital.services.file.ExcelService;
import by.autocapital.services.file.FileService;
import by.autocapital.services.other.ConfigService;

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
