package by.testbot.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import by.testbot.models.viber.Button;
import by.testbot.models.viber.Keyboard;
import by.testbot.config.EnvironmentConfig;
import by.testbot.models.BotMessage;
import by.testbot.models.enums.button.ActionType;
import by.testbot.services.other.LocaleMessageService;

public class KeyboardSource {
    @Autowired
    private LocaleMessageService localeMessageService;

    @Autowired
    private EnvironmentConfig environmentConfig;

    final static Integer MAX_ITEMS_AT_PAGE = 9;
    final static Integer PAGABLE_BUTTON_SMALL_WIDTH = 2;
    final static Integer PAGABLE_BUTTON_MEDIUM_WIDTH = 3;
    final static Integer PAGABLE_BUTTON_LARGE_WIDTH = 6;
    final static Integer PAGABLE_BUTTON_HEIGHT = 1;

    @PostConstruct
    public void postConstruct() {
        if (MAX_ITEMS_AT_PAGE % 3 != 0) {
            throw new IllegalArgumentException("Max items at page is incorrect");
        }
    }

    //region AdminKeyboards

    public Keyboard getAdminMainMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button postponeMessageButton = new Button();
        // postponeMessageButton.setImageUrl(viberService.getIconEndpoint() + "postpone_message.png");
        // postponeMessageButton.setImageUrl("https://img.icons8.com/material-outlined/24/000000/smoking.png");
        // postponeMessageButton.setImageScaleType(ImageScaleType.FIT);
        postponeMessageButton.setText(localeMessageService.getMessage("button.mainMenu.postponeMessage"));
        // postponeMessageButton.setText("<font color=\"#494E67\">Smoking</font><br><br>");
        postponeMessageButton.setActionBody("callback.adminMainMenu.postponeMessage");
        postponeMessageButton.setColumns(3);
        postponeMessageButton.setRows(1);
        postponeMessageButton.setBackgroundColor("#2db9b9");
        buttons.add(postponeMessageButton);

        Button listOfManagersButton = new Button();
        listOfManagersButton.setText(localeMessageService.getMessage("button.mainMenu.managers"));
        listOfManagersButton.setActionBody("callback.adminMainMenu.managers");
        listOfManagersButton.setColumns(3);
        listOfManagersButton.setRows(1);
        listOfManagersButton.setBackgroundColor("#2db9b9");
        listOfManagersButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(listOfManagersButton);

        Button listOfClientsButton = new Button();
        listOfClientsButton.setText(localeMessageService.getMessage("button.mainMenu.clients"));
        listOfClientsButton.setActionBody("callback.adminMainMenu.clients");
        listOfClientsButton.setColumns(3);
        listOfClientsButton.setRows(1);
        listOfClientsButton.setBackgroundColor("#2db9b9");
        listOfClientsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(listOfClientsButton);

        Button reportButton = new Button();
        reportButton.setText(localeMessageService.getMessage("button.mainMenu.report"));
        reportButton.setActionBody("callback.adminMainMenu.report");
        reportButton.setColumns(3);
        reportButton.setRows(1);
        reportButton.setBackgroundColor("#2db9b9");
        reportButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportButton);

        Button integrationButton = new Button();
        integrationButton.setText(localeMessageService.getMessage("button.mainMenu.integrations"));
        integrationButton.setActionBody("callback.adminMainMenu.integrations");
        integrationButton.setColumns(3);
        integrationButton.setRows(1);
        integrationButton.setBackgroundColor("#2db9b9");
        integrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(integrationButton);

        Button settingsButton = new Button();
        settingsButton.setText(localeMessageService.getMessage("button.mainMenu.settings"));
        settingsButton.setActionBody("callback.adminMainMenu.settings");
        settingsButton.setColumns(3);
        settingsButton.setRows(1);
        settingsButton.setBackgroundColor("#2db9b9");
        settingsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(settingsButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getManagersMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button getListOfManagersButton = new Button();
        getListOfManagersButton.setText(localeMessageService.getMessage("button.managersMenu.getManagersList"));
        getListOfManagersButton.setActionBody("callback.managersMenu.list");
        getListOfManagersButton.setColumns(3);
        getListOfManagersButton.setRows(1);
        getListOfManagersButton.setBackgroundColor("#2db9b9");
        getListOfManagersButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(getListOfManagersButton);

        Button addManagerButton = new Button();
        addManagerButton.setText(localeMessageService.getMessage("button.managersMenu.addManager"));
        addManagerButton.setActionBody("callback.managersMenu.add");
        addManagerButton.setColumns(3);
        addManagerButton.setRows(1);
        addManagerButton.setBackgroundColor("#2db9b9");
        addManagerButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(addManagerButton);

        Button deleteManagerButton = new Button();
        deleteManagerButton.setText(localeMessageService.getMessage("button.managersMenu.deleteManager"));
        deleteManagerButton.setActionBody("callback.managersMenu.delete");
        deleteManagerButton.setColumns(3);
        deleteManagerButton.setRows(1);
        deleteManagerButton.setBackgroundColor("#2db9b9");
        deleteManagerButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(deleteManagerButton);

        Button changePrivilegiesButton = new Button();
        changePrivilegiesButton.setText(localeMessageService.getMessage("button.managersMenu.changeManagerPrivilegies"));
        changePrivilegiesButton.setActionBody("callback.managersMenu.changePrivilegies");
        changePrivilegiesButton.setColumns(3);
        changePrivilegiesButton.setRows(1);
        changePrivilegiesButton.setBackgroundColor("#2db9b9");
        changePrivilegiesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(changePrivilegiesButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.managersMenu.back"));
        backButton.setActionBody("callback.managersMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getClientsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        
        Button getListOfClientsAndOtherInformationButton = new Button();
        getListOfClientsAndOtherInformationButton.setText(localeMessageService.getMessage("button.clientsMenu.getClientsListAndOtherInformation"));
        getListOfClientsAndOtherInformationButton.setActionBody("callback.clientsMenu.list");
        getListOfClientsAndOtherInformationButton.setColumns(6);
        getListOfClientsAndOtherInformationButton.setRows(1);
        getListOfClientsAndOtherInformationButton.setBackgroundColor("#2db9b9");
        getListOfClientsAndOtherInformationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(getListOfClientsAndOtherInformationButton);

        Button additionalActionsWithClientsButton = new Button();
        additionalActionsWithClientsButton.setText(localeMessageService.getMessage("button.clientsMenu.additionalActionsWithClients"));
        additionalActionsWithClientsButton.setActionBody("callback.clientsMenu.additionalActions");
        additionalActionsWithClientsButton.setColumns(6);
        additionalActionsWithClientsButton.setRows(1);
        additionalActionsWithClientsButton.setBackgroundColor("#2db9b9");
        additionalActionsWithClientsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(additionalActionsWithClientsButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.clientsMenu.back"));
        backButton.setActionBody("callback.clientsMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getReportMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button reportAboutManagersWorkButton = new Button();
        reportAboutManagersWorkButton.setText(localeMessageService.getMessage("button.reportMenu.reportAboutManagersWork"));
        reportAboutManagersWorkButton.setActionBody("callback.reportMenu.aboutManagersWork");
        reportAboutManagersWorkButton.setColumns(6);
        reportAboutManagersWorkButton.setRows(1);
        reportAboutManagersWorkButton.setBackgroundColor("#2db9b9");
        reportAboutManagersWorkButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportAboutManagersWorkButton);

        Button reportAboutBotWorkButton = new Button();
        reportAboutBotWorkButton.setText(localeMessageService.getMessage("button.reportMenu.reportAboutBotWork"));
        reportAboutBotWorkButton.setActionBody("callback.reportMenu.aboutBotWork");
        reportAboutBotWorkButton.setColumns(6);
        reportAboutBotWorkButton.setRows(1);
        reportAboutBotWorkButton.setBackgroundColor("#2db9b9");
        reportAboutBotWorkButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportAboutBotWorkButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.reportMenu.back"));
        backButton.setActionBody("callback.reportMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getIntegrationsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button addOrDeleteIntegrationButton = new Button();
        addOrDeleteIntegrationButton.setText(localeMessageService.getMessage("button.integrationsMenu.addOrDeleteIntegration"));
        addOrDeleteIntegrationButton.setActionBody("callback.integrationsMenu.addOrDelete");
        addOrDeleteIntegrationButton.setColumns(6);
        addOrDeleteIntegrationButton.setRows(1);
        addOrDeleteIntegrationButton.setBackgroundColor("#2db9b9");
        addOrDeleteIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(addOrDeleteIntegrationButton);

        // Button deleteIntegrationButton = new Button();
        // deleteIntegrationButton.setText("Удаление интергации");
        // deleteIntegrationButton.setActionBody("Удаление интеграции");
        // deleteIntegrationButton.setColumns(6);
        // deleteIntegrationButton.setRows(1);
        // deleteIntegrationButton.setBackgroundColor("#2db9b9");
        // deleteIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        // buttons.add(deleteIntegrationButton);

        Button newIntegrationButton = new Button();
        newIntegrationButton.setText(localeMessageService.getMessage("button.integrationsMenu.newIntegrations"));
        newIntegrationButton.setActionBody("callback.integrationsMenu.new");
        newIntegrationButton.setColumns(6);
        newIntegrationButton.setRows(1);
        newIntegrationButton.setBackgroundColor("#2db9b9");
        newIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(newIntegrationButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.integrationsMenu.back"));
        backButton.setActionBody("callback.integrationsMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getSettingsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button editTextButton = new Button();
        editTextButton.setText(localeMessageService.getMessage("button.settings.editTextsWhichBotSend"));
        editTextButton.setActionBody("callback.settings.editTextsWhichBotSend");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setBackgroundColor("#2db9b9");
        editTextButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(editTextButton);

        Button setBotUsingPeriodButton = new Button();
        setBotUsingPeriodButton.setText(localeMessageService.getMessage("button.settings.botWork"));
        setBotUsingPeriodButton.setActionBody("callback.settings.botWork");
        setBotUsingPeriodButton.setColumns(6);
        setBotUsingPeriodButton.setRows(1);
        setBotUsingPeriodButton.setBackgroundColor("#2db9b9");
        setBotUsingPeriodButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(setBotUsingPeriodButton);

        Button updateCarsButton = new Button();
        updateCarsButton.setText(localeMessageService.getMessage("button.settings.updateCars"));
        updateCarsButton.setActionBody("callback.settings.updateCars");
        updateCarsButton.setColumns(6);
        updateCarsButton.setRows(1);
        updateCarsButton.setBackgroundColor("#2db9b9");
        updateCarsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(updateCarsButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.settings.back"));
        backButton.setActionBody("callback.settings.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotMessageButtonsMenuKeyabord() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button showMessageButton = new Button();
        showMessageButton.setText("Показать текст сообщения");
        showMessageButton.setActionBody("callback.botMessageButtonsMenu.showMessageButton");
        showMessageButton.setColumns(6);
        showMessageButton.setRows(1);
        showMessageButton.setBackgroundColor("#2db9b9");
        buttons.add(showMessageButton);

        Button editTextButton = new Button();
        editTextButton.setText("Изменить текст сообщения");
        editTextButton.setActionBody("callback.botMessageButtonsMenu.editTextButton");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setBackgroundColor("#2db9b9");
        buttons.add(editTextButton);

        Button listOfButtonsButton = new Button();
        listOfButtonsButton.setText("Список кнопок");
        listOfButtonsButton.setActionBody("callback.botMessageButtonsMenu.listButton");
        listOfButtonsButton.setColumns(6);
        listOfButtonsButton.setRows(1);
        listOfButtonsButton.setBackgroundColor("#2db9b9");
        buttons.add(listOfButtonsButton);

        Button deleteButton = new Button();
        deleteButton.setText("Удалить кнопку");
        deleteButton.setActionBody("callback.botMessageButtonsMenu.deleteButton");
        deleteButton.setColumns(3);
        deleteButton.setRows(1);
        deleteButton.setBackgroundColor("#2db9b9");
        buttons.add(deleteButton);

        Button addButton = new Button();
        addButton.setText("Добавить кнопку");
        addButton.setActionBody("callback.botMessageButtonsMenu.addButton");
        addButton.setColumns(3);
        addButton.setRows(1);
        addButton.setBackgroundColor("#2db9b9");
        buttons.add(addButton);

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("callback.botMessageButtonsMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotMessageMenuKeyabord() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button listButton = new Button();
        listButton.setText("Список сообщений");
        listButton.setActionBody("callback.botMessageMenu.list");
        listButton.setColumns(6);
        listButton.setRows(1);
        listButton.setBackgroundColor("#2db9b9");
        buttons.add(listButton);

        Button editTextButton = new Button();
        editTextButton.setText("Изменить сообщение");
        editTextButton.setActionBody("callback.botMessageMenu.edit");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setBackgroundColor("#2db9b9");
        buttons.add(editTextButton);

        Button addButton = new Button();
        addButton.setText("Добавить сообщение");
        addButton.setActionBody("callback.botMessageMenu.add");
        addButton.setColumns(3);
        addButton.setRows(1);
        addButton.setBackgroundColor("#2db9b9");
        buttons.add(addButton);

        Button deleteButton = new Button();
        deleteButton.setText("Удалить сообщение");
        deleteButton.setActionBody("callback.botMessageMenu.delete");
        deleteButton.setColumns(3);
        deleteButton.setRows(1);
        deleteButton.setBackgroundColor("#2db9b9");
        buttons.add(deleteButton);

        Button editOrderButton = new Button();
        editOrderButton.setText("Изменить порядок сообщений");
        editOrderButton.setActionBody("callback.botMessageMenu.editOrder");
        editOrderButton.setColumns(6);
        editOrderButton.setRows(1);
        editOrderButton.setBackgroundColor("#2db9b9");
        buttons.add(editOrderButton);

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("callback.botMessageMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getAnswerTypeKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button positiveButton = new Button();
        positiveButton.setText("Положительный");
        positiveButton.setActionBody("callback.botMessageMenu.selectAnswerType.positive");
        positiveButton.setColumns(6);
        positiveButton.setRows(1);
        positiveButton.setBackgroundColor("#2db9b9");
        buttons.add(positiveButton);

        Button negativeButton = new Button();
        negativeButton.setText("Отрицательный");
        negativeButton.setActionBody("callback.botMessageMenu.selectAnswerType.negative");
        negativeButton.setColumns(6);
        negativeButton.setRows(1);
        negativeButton.setBackgroundColor("#2db9b9");
        buttons.add(negativeButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getConfirmBotMessageKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText("Да");
        yesButton.setActionBody("callback.botMessageMenu.confirmYes");
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText("Нет");
        noButton.setActionBody("callback.botMessageMenu.confirmNo");
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotUsagePeriodMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button inChatButton = new Button();
        inChatButton.setText((environmentConfig.getEnabled() == true ? "Выключить" : "Включить") + " бота");
        inChatButton.setActionBody("Включить/выключить бота");
        inChatButton.setColumns(6);
        inChatButton.setRows(1);
        inChatButton.setBackgroundColor("#2db9b9");
        inChatButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(inChatButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.botUsagePeriodMenu.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.botUsagePeriodMenu.back"));
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getConfirmPostponeMessageKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText("Да");
        yesButton.setActionBody("Да");
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        yesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText("Нет");
        noButton.setActionBody("Нет");
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        noButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getConfirmBotMessageButtonKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText("Да");
        yesButton.setActionBody("callback.botMessageMenu.botMessageButtonsMenu.confirmYes");
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        yesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText("Нет");
        noButton.setActionBody("callback.botMessageMenu.botMessageButtonsMenu.confirmNo");
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        noButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getConfirmManagerKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText("Да");
        yesButton.setActionBody("callback.managers.confirmYes");
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        yesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText("Нет");
        noButton.setActionBody("callback.managers.confirmNo");
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        noButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBackKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("callback.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    

    //endregion

    //region UserKeyboards

    public Keyboard getYesNoKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText(localeMessageService.getMessage("button.yes"));
        yesButton.setActionBody(localeMessageService.getMessage("button.yes"));
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        yesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText(localeMessageService.getMessage("button.no"));
        noButton.setActionBody(localeMessageService.getMessage("button.no"));
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        noButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getEndDialogKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button startNewDialogButton = new Button();
        startNewDialogButton.setText(localeMessageService.getMessage("button.userDialog.startNewDialog"));
        startNewDialogButton.setActionBody(localeMessageService.getMessage("button.userDialog.startNewDialog"));
        startNewDialogButton.setColumns(6);
        startNewDialogButton.setRows(1);
        startNewDialogButton.setBackgroundColor("#2db9b9");
        startNewDialogButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(startNewDialogButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getWhenWillBuyCarKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText(localeMessageService.getMessage("button.userDialog.nearFuture"));
        yesButton.setActionBody(localeMessageService.getMessage("button.userDialog.nearFuture"));
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        yesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText(localeMessageService.getMessage("button.userDialog.afterNewYear"));
        noButton.setActionBody(localeMessageService.getMessage("button.userDialog.afterNewYear"));
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        noButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getWithoutPhotoKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button withoutPhoto = new Button();
        withoutPhoto.setText(localeMessageService.getMessage("button.postponeMessage.withoutPhoto"));
        withoutPhoto.setActionBody(localeMessageService.getMessage("button.postponeMessage.withoutPhoto"));
        withoutPhoto.setColumns(6);
        withoutPhoto.setRows(1);
        withoutPhoto.setBackgroundColor("#2db9b9");
        withoutPhoto.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(withoutPhoto);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getAskAndEnterPhoneNumberKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button sharePhone = new Button();
        sharePhone.setText(localeMessageService.getMessage("button.userDialog.sharePhone"));
        sharePhone.setActionBody(localeMessageService.getMessage("button.userDialog.sharePhone"));
        sharePhone.setActionType(ActionType.SHARE_PHONE);
        sharePhone.setColumns(6);
        sharePhone.setRows(1);
        sharePhone.setBackgroundColor("#2db9b9");

        buttons.add(sharePhone);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getAskManagerPhoneNumberKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button sharePhone = new Button();
        sharePhone.setText("Поделиться номером телефона");
        sharePhone.setActionBody("button.manager.sharePhone");
        sharePhone.setActionType(ActionType.SHARE_PHONE);
        sharePhone.setColumns(6);
        sharePhone.setRows(1);
        sharePhone.setBackgroundColor("#2db9b9");

        buttons.add(sharePhone);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getSkipStepKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button skipButton = new Button();
        skipButton.setText(localeMessageService.getMessage("button.userDialog.skip"));
        skipButton.setActionBody(localeMessageService.getMessage("button.userDialog.skip"));
        skipButton.setColumns(6);
        skipButton.setRows(1);
        skipButton.setBackgroundColor("#2db9b9");

        buttons.add(skipButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    //endregion

    public Keyboard generateKeyboard(BotMessage botMessage) {
        Keyboard keyboard = new Keyboard();

        List<by.testbot.models.Button> messageButtons = List.copyOf(botMessage.getButtons()).stream().sorted(Comparator.comparing(by.testbot.models.Button::getId)).collect(Collectors.toList());
        List<Button> buttons = new ArrayList<>();

        for (by.testbot.models.Button button : messageButtons) {
            Button newButton = new Button();
            newButton.setText(button.getText());
            newButton.setActionBody(button.getCallbackData());
            newButton.setColumns(6);
            newButton.setRows(1);
            newButton.setBackgroundColor("#2db9b9");

            buttons.add(newButton);
        }

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard generatePagableKeyboard(List<String> items, Integer page) {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        for(Integer i = ((page - 1) * MAX_ITEMS_AT_PAGE); i < page * MAX_ITEMS_AT_PAGE && i < items.size(); i++) {
            Button button = new Button();
            button.setText(items.get(i));
            button.setActionBody(items.get(i));

            if ((i == items.size() - 1 || i == items.size() - 2)  && ((items.size() % MAX_ITEMS_AT_PAGE) / 2 == 1)) {
                button.setColumns(PAGABLE_BUTTON_MEDIUM_WIDTH);
                button.setRows(PAGABLE_BUTTON_HEIGHT);
            }
            else if (i == items.size() - 1 && (items.size() % MAX_ITEMS_AT_PAGE) % 4 == 0 && (items.size() % MAX_ITEMS_AT_PAGE) % 8 != 0) {
                button.setColumns(PAGABLE_BUTTON_LARGE_WIDTH);
                button.setRows(PAGABLE_BUTTON_HEIGHT);
            }
            else if ((i == items.size() - 1 || i == items.size() - 2)  && ((items.size() % MAX_ITEMS_AT_PAGE) % 5 == 0 || (items.size() % MAX_ITEMS_AT_PAGE) % 8 == 0)) {
                button.setColumns(PAGABLE_BUTTON_MEDIUM_WIDTH);
                button.setRows(PAGABLE_BUTTON_HEIGHT);
            }
            else {
                button.setColumns(PAGABLE_BUTTON_SMALL_WIDTH);
                button.setRows(PAGABLE_BUTTON_HEIGHT);
            }
            

            
            button.setBackgroundColor("#2db9b9");

            buttons.add(button);
        }

        if (items.size() > MAX_ITEMS_AT_PAGE) {
            Button previousPageButton = new Button();
            previousPageButton.setText("<<<");
            previousPageButton.setActionBody("Назад");
            previousPageButton.setColumns(3);
            previousPageButton.setRows(1);
            previousPageButton.setBackgroundColor("#2db9b9");
            buttons.add(previousPageButton);

            Button nextPageButton = new Button();
            nextPageButton.setText(">>>");
            nextPageButton.setActionBody("Далее");
            nextPageButton.setColumns(3);
            nextPageButton.setRows(1);
            nextPageButton.setBackgroundColor("#2db9b9");
            buttons.add(nextPageButton);
        }

        keyboard.setButtons(buttons);
        return keyboard;
    }

}
