package by.autocapital.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import by.autocapital.config.EnvironmentConfig;
import by.autocapital.models.BotMessage;
import by.autocapital.models.enums.button.ActionType;
import by.autocapital.models.enums.button.ImageScaleType;
import by.autocapital.models.enums.button.TextSize;
import by.autocapital.models.enums.button.TextVerticalAlign;
import by.autocapital.models.viber.Button;
import by.autocapital.models.viber.Frame;
import by.autocapital.models.viber.Keyboard;
import by.autocapital.services.other.LocaleMessageService;
import by.autocapital.services.viber.ViberService;

public class KeyboardSource {
    @Autowired
    private LocaleMessageService localeMessageService;

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private ViberService viberService;

    final static Integer MAX_ITEMS_AT_PAGE = 9;
    final static Integer PAGABLE_BUTTON_SMALL_WIDTH = 2;
    final static Integer PAGABLE_BUTTON_MEDIUM_WIDTH = 3;
    final static Integer PAGABLE_BUTTON_LARGE_WIDTH = 6;
    final static Integer PAGABLE_BUTTON_HEIGHT = 1;

    final static String POSTPONE_MESSAGE_ICON_FILENAME = "postpone_message.png";
    final static String MANAGERS_ICON_FILENAME = "managers.png";
    final static String CLIENTS_ICON_FILENAME = "clients.png";
    final static String REPORT_ICON_FILENAME = "report.png";
    final static String INTEGRATIONS_ICON_FILENAME = "integrations.png";
    final static String SETTINGS_ICON_FILENAME = "settings.png";
    final static String ADD_ICON_FILENAME = "add.png";
    final static String BACK_ICON_FILENAME = "back.png";
    final static String BOT_ICON_FILENAME = "bot.png";
    final static String CAR_ICON_FILENAME = "car.png";
    final static String DELETE_ICON_FILENAME = "delete.png";
    final static String EDIT_ICON_FILENAME = "edit.png";
    final static String LIST_ICON_FILENAME = "list.png";
    final static String SEQUENCE_ICON_FILENAME = "sequence.png";
    final static String STATISTICS_ICON_FILENAME = "statistics.png";
    final static String XLS_ICON_FILENAME = "xls.png";
    final static String MESSAGE_ICON_FILENAME = "message.png";
    final static String ON_OFF_ICON_FILENAME = "on_off.png";
    final static String REFRESH_ICON_FILENAME = "refresh.png";
    final static String PHONE_ICON_FILENAME = "phone.png";
    final static String CONFIRM_ICON_FILENAME = "confirm.png";
    final static String REJECT_ICON_FILENAME = "reject.png";
    final static String YES_ICON_FILENAME = "yes.png";
    final static String NO_ICON_FILENAME = "no.png";

    @PostConstruct
    public void postConstruct() {
        if (MAX_ITEMS_AT_PAGE % 3 != 0) {
            throw new IllegalArgumentException("Max items at page is incorrect");
        }
    }

    //region AdminKeyboards

    public Keyboard getAdminMainMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        Frame frame = new Frame();
        List<Button> buttons = new ArrayList<>();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button postponeMessageButton = new Button();
        postponeMessageButton.setText(localeMessageService.getMessage("button.mainMenu.postponeMessage"));
        postponeMessageButton.setActionBody("callback.adminMainMenu.postponeMessage");
        postponeMessageButton.setColumns(3);
        postponeMessageButton.setRows(1);
        postponeMessageButton.setImageScaleType(ImageScaleType.FIT);
        postponeMessageButton.setImageUrl(viberService.getIconEndpoint() + POSTPONE_MESSAGE_ICON_FILENAME);
        postponeMessageButton.setFrame(frame);
        postponeMessageButton.setTextSize(TextSize.SMALL);
        postponeMessageButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        postponeMessageButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        postponeMessageButton.setBackgroundColor("#c0c3c9");
        buttons.add(postponeMessageButton);

        Button managersButton = new Button();
        managersButton.setText(localeMessageService.getMessage("button.mainMenu.managers"));
        managersButton.setActionBody("callback.adminMainMenu.managers");
        managersButton.setColumns(3);
        managersButton.setRows(1);
        managersButton.setImageScaleType(ImageScaleType.FIT);
        managersButton.setImageUrl(viberService.getIconEndpoint() + MANAGERS_ICON_FILENAME);
        managersButton.setFrame(frame);
        managersButton.setTextSize(TextSize.SMALL);
        managersButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        managersButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        managersButton.setBackgroundColor("#c0c3c9");
        buttons.add(managersButton);

        Button clientsButton = new Button();
        clientsButton.setText(localeMessageService.getMessage("button.mainMenu.clients"));
        clientsButton.setActionBody("callback.adminMainMenu.clients");
        clientsButton.setColumns(3);
        clientsButton.setRows(1);
        clientsButton.setImageScaleType(ImageScaleType.FIT);
        clientsButton.setImageUrl(viberService.getIconEndpoint() + CLIENTS_ICON_FILENAME);
        clientsButton.setFrame(frame);
        clientsButton.setTextSize(TextSize.SMALL);
        clientsButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        clientsButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        clientsButton.setBackgroundColor("#c0c3c9");
        buttons.add(clientsButton);

        Button reportButton = new Button();
        reportButton.setText(localeMessageService.getMessage("button.mainMenu.report"));
        reportButton.setActionBody("callback.adminMainMenu.report");
        reportButton.setColumns(3);
        reportButton.setRows(1);
        reportButton.setImageScaleType(ImageScaleType.FIT);
        reportButton.setImageUrl(viberService.getIconEndpoint() + REPORT_ICON_FILENAME);
        reportButton.setFrame(frame);
        reportButton.setTextSize(TextSize.SMALL);
        reportButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        reportButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        reportButton.setBackgroundColor("#c0c3c9");
        buttons.add(reportButton);

        Button integrationsButton = new Button();
        integrationsButton.setText(localeMessageService.getMessage("button.mainMenu.integrations"));
        integrationsButton.setActionBody("callback.adminMainMenu.integrations");
        integrationsButton.setColumns(3);
        integrationsButton.setRows(1);
        integrationsButton.setImageScaleType(ImageScaleType.FIT);
        integrationsButton.setImageUrl(viberService.getIconEndpoint() + INTEGRATIONS_ICON_FILENAME);
        integrationsButton.setFrame(frame);
        integrationsButton.setTextSize(TextSize.SMALL);
        integrationsButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        integrationsButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        integrationsButton.setBackgroundColor("#c0c3c9");
        buttons.add(integrationsButton);

        Button settingsButton = new Button();
        settingsButton.setText(localeMessageService.getMessage("button.mainMenu.settings"));
        settingsButton.setActionBody("callback.adminMainMenu.settings");
        settingsButton.setColumns(3);
        settingsButton.setRows(1);
        settingsButton.setImageScaleType(ImageScaleType.FIT);
        settingsButton.setImageUrl(viberService.getIconEndpoint() + SETTINGS_ICON_FILENAME);
        settingsButton.setFrame(frame);
        settingsButton.setTextSize(TextSize.SMALL);
        settingsButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        settingsButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        settingsButton.setBackgroundColor("#c0c3c9");
        buttons.add(settingsButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getManagersMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button getListOfManagersButton = new Button();
        getListOfManagersButton.setText(localeMessageService.getMessage("button.managersMenu.getManagersList"));
        getListOfManagersButton.setActionBody("callback.managersMenu.list");
        getListOfManagersButton.setColumns(3);
        getListOfManagersButton.setRows(1);
        getListOfManagersButton.setImageScaleType(ImageScaleType.FIT);
        getListOfManagersButton.setImageUrl(viberService.getIconEndpoint() + LIST_ICON_FILENAME);
        getListOfManagersButton.setFrame(frame);
        getListOfManagersButton.setTextSize(TextSize.SMALL);
        getListOfManagersButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        getListOfManagersButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        getListOfManagersButton.setBackgroundColor("#c0c3c9");
        buttons.add(getListOfManagersButton);

        Button addManagerButton = new Button();
        addManagerButton.setText(localeMessageService.getMessage("button.managersMenu.addManager"));
        addManagerButton.setActionBody("callback.managersMenu.add");
        addManagerButton.setColumns(3);
        addManagerButton.setRows(1);
        addManagerButton.setImageScaleType(ImageScaleType.FIT);
        addManagerButton.setImageUrl(viberService.getIconEndpoint() + ADD_ICON_FILENAME);
        addManagerButton.setFrame(frame);
        addManagerButton.setTextSize(TextSize.SMALL);
        addManagerButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        addManagerButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        addManagerButton.setBackgroundColor("#c0c3c9");
        buttons.add(addManagerButton);

        Button deleteManagerButton = new Button();
        deleteManagerButton.setText(localeMessageService.getMessage("button.managersMenu.deleteManager"));
        deleteManagerButton.setActionBody("callback.managersMenu.delete");
        deleteManagerButton.setColumns(3);
        deleteManagerButton.setRows(1);
        deleteManagerButton.setImageScaleType(ImageScaleType.FIT);
        deleteManagerButton.setImageUrl(viberService.getIconEndpoint() + DELETE_ICON_FILENAME);
        deleteManagerButton.setFrame(frame);
        deleteManagerButton.setTextSize(TextSize.SMALL);
        deleteManagerButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        deleteManagerButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        deleteManagerButton.setBackgroundColor("#c0c3c9");
        buttons.add(deleteManagerButton);

        Button changePrivilegiesButton = new Button();
        changePrivilegiesButton.setText(localeMessageService.getMessage("button.managersMenu.changeManagerPrivilegies"));
        changePrivilegiesButton.setActionBody("callback.managersMenu.changePrivilegies");
        changePrivilegiesButton.setColumns(3);
        changePrivilegiesButton.setRows(1);
        changePrivilegiesButton.setImageScaleType(ImageScaleType.FIT);
        changePrivilegiesButton.setImageUrl(viberService.getIconEndpoint() + EDIT_ICON_FILENAME);
        changePrivilegiesButton.setFrame(frame);
        changePrivilegiesButton.setTextSize(TextSize.SMALL);
        changePrivilegiesButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        changePrivilegiesButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        changePrivilegiesButton.setBackgroundColor("#c0c3c9");
        buttons.add(changePrivilegiesButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.managersMenu.back"));
        backButton.setActionBody("callback.managersMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getClientsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");
        
        Button getListOfClientsAndOtherInformationButton = new Button();
        getListOfClientsAndOtherInformationButton.setText(localeMessageService.getMessage("button.clientsMenu.getClientsListAndOtherInformation"));
        getListOfClientsAndOtherInformationButton.setActionBody("callback.clientsMenu.list");
        getListOfClientsAndOtherInformationButton.setColumns(6);
        getListOfClientsAndOtherInformationButton.setRows(1);
        getListOfClientsAndOtherInformationButton.setImageScaleType(ImageScaleType.FIT);
        getListOfClientsAndOtherInformationButton.setImageUrl(viberService.getIconEndpoint() + LIST_ICON_FILENAME);
        getListOfClientsAndOtherInformationButton.setFrame(frame);
        getListOfClientsAndOtherInformationButton.setTextSize(TextSize.SMALL);
        getListOfClientsAndOtherInformationButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        getListOfClientsAndOtherInformationButton.setTextPaddings(Arrays.asList(0, 0, 1, 0));
        getListOfClientsAndOtherInformationButton.setBackgroundColor("#c0c3c9");
        buttons.add(getListOfClientsAndOtherInformationButton);

        Button additionalActionsWithClientsButton = new Button();
        additionalActionsWithClientsButton.setText(localeMessageService.getMessage("button.clientsMenu.additionalActionsWithClients"));
        additionalActionsWithClientsButton.setActionBody("callback.clientsMenu.additionalActions");
        additionalActionsWithClientsButton.setColumns(6);
        additionalActionsWithClientsButton.setRows(1);
        additionalActionsWithClientsButton.setImageScaleType(ImageScaleType.FIT);
        additionalActionsWithClientsButton.setImageUrl(viberService.getIconEndpoint() + ADD_ICON_FILENAME);
        additionalActionsWithClientsButton.setFrame(frame);
        additionalActionsWithClientsButton.setTextSize(TextSize.SMALL);
        additionalActionsWithClientsButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        additionalActionsWithClientsButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        additionalActionsWithClientsButton.setBackgroundColor("#c0c3c9");
        buttons.add(additionalActionsWithClientsButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.clientsMenu.back"));
        backButton.setActionBody("callback.clientsMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getReportMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button reportAboutManagersWorkButton = new Button();
        reportAboutManagersWorkButton.setText(localeMessageService.getMessage("button.reportMenu.reportAboutManagersWork"));
        reportAboutManagersWorkButton.setActionBody("callback.reportMenu.aboutManagersWork");
        reportAboutManagersWorkButton.setColumns(6);
        reportAboutManagersWorkButton.setRows(1);
        reportAboutManagersWorkButton.setImageScaleType(ImageScaleType.FIT);
        reportAboutManagersWorkButton.setImageUrl(viberService.getIconEndpoint() + MANAGERS_ICON_FILENAME);
        reportAboutManagersWorkButton.setFrame(frame);
        reportAboutManagersWorkButton.setTextSize(TextSize.SMALL);
        reportAboutManagersWorkButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        reportAboutManagersWorkButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        reportAboutManagersWorkButton.setBackgroundColor("#c0c3c9");
        buttons.add(reportAboutManagersWorkButton);

        Button reportAboutBotWorkButton = new Button();
        reportAboutBotWorkButton.setText(localeMessageService.getMessage("button.reportMenu.reportAboutBotWork"));
        reportAboutBotWorkButton.setActionBody("callback.reportMenu.aboutBotWork");
        reportAboutBotWorkButton.setColumns(6);
        reportAboutBotWorkButton.setRows(1);
        reportAboutBotWorkButton.setImageScaleType(ImageScaleType.FIT);
        reportAboutBotWorkButton.setImageUrl(viberService.getIconEndpoint() + BOT_ICON_FILENAME);
        reportAboutBotWorkButton.setFrame(frame);
        reportAboutBotWorkButton.setTextSize(TextSize.SMALL);
        reportAboutBotWorkButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        reportAboutBotWorkButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        reportAboutBotWorkButton.setBackgroundColor("#c0c3c9");
        buttons.add(reportAboutBotWorkButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.reportMenu.back"));
        backButton.setActionBody("callback.reportMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getIntegrationsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button addOrDeleteIntegrationButton = new Button();
        addOrDeleteIntegrationButton.setText(localeMessageService.getMessage("button.integrationsMenu.addOrDeleteIntegration"));
        addOrDeleteIntegrationButton.setActionBody("callback.integrationsMenu.addOrDelete");
        addOrDeleteIntegrationButton.setColumns(6);
        addOrDeleteIntegrationButton.setRows(1);
        addOrDeleteIntegrationButton.setImageScaleType(ImageScaleType.FIT);
        // addOrDeleteIntegrationButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        addOrDeleteIntegrationButton.setFrame(frame);
        addOrDeleteIntegrationButton.setTextSize(TextSize.SMALL);
        addOrDeleteIntegrationButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        addOrDeleteIntegrationButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        addOrDeleteIntegrationButton.setBackgroundColor("#c0c3c9");
        buttons.add(addOrDeleteIntegrationButton);

        // Button deleteIntegrationButton = new Button();
        // deleteIntegrationButton.setText("Удаление интергации");
        // deleteIntegrationButton.setActionBody("Удаление интеграции");
        // deleteIntegrationButton.setColumns(6);
        // deleteIntegrationButton.setRows(1);
        // deleteIntegrationButton.setImageScaleType(ImageScaleType.FIT);
        // deleteIntegrationButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        // deleteIntegrationButton.setFrame(frame);
        // deleteIntegrationButton.setTextSize(TextSize.SMALL);
        // deleteIntegrationButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        // deleteIntegrationButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        // deleteIntegrationButton.setBackgroundColor("#c0c3c9");
        // buttons.add(deleteIntegrationButton);

        Button newIntegrationButton = new Button();
        newIntegrationButton.setText(localeMessageService.getMessage("button.integrationsMenu.newIntegrations"));
        newIntegrationButton.setActionBody("callback.integrationsMenu.new");
        newIntegrationButton.setColumns(6);
        newIntegrationButton.setRows(1);
        newIntegrationButton.setImageScaleType(ImageScaleType.FIT);
        // newIntegrationButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        newIntegrationButton.setFrame(frame);
        newIntegrationButton.setTextSize(TextSize.SMALL);
        newIntegrationButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        newIntegrationButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        newIntegrationButton.setBackgroundColor("#c0c3c9");
        buttons.add(newIntegrationButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.integrationsMenu.back"));
        backButton.setActionBody("callback.integrationsMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getSettingsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button editTextButton = new Button();
        editTextButton.setText(localeMessageService.getMessage("button.settings.editTextsWhichBotSend"));
        editTextButton.setActionBody("callback.settings.editTextsWhichBotSend");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setImageScaleType(ImageScaleType.FIT);
        editTextButton.setImageUrl(viberService.getIconEndpoint() + EDIT_ICON_FILENAME);
        editTextButton.setFrame(frame);
        editTextButton.setTextSize(TextSize.SMALL);
        editTextButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        editTextButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        editTextButton.setBackgroundColor("#c0c3c9");
        buttons.add(editTextButton);

        Button setBotUsingPeriodButton = new Button();
        setBotUsingPeriodButton.setText(localeMessageService.getMessage("button.settings.botWork"));
        setBotUsingPeriodButton.setActionBody("callback.settings.botWork");
        setBotUsingPeriodButton.setColumns(6);
        setBotUsingPeriodButton.setRows(1);
        setBotUsingPeriodButton.setImageScaleType(ImageScaleType.FIT);
        setBotUsingPeriodButton.setImageUrl(viberService.getIconEndpoint() + BOT_ICON_FILENAME);
        setBotUsingPeriodButton.setFrame(frame);
        setBotUsingPeriodButton.setTextSize(TextSize.SMALL);
        setBotUsingPeriodButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        setBotUsingPeriodButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        setBotUsingPeriodButton.setBackgroundColor("#c0c3c9");
        buttons.add(setBotUsingPeriodButton);

        Button updateCarsButton = new Button();
        updateCarsButton.setText(localeMessageService.getMessage("button.settings.updateCars"));
        updateCarsButton.setActionBody("callback.settings.updateCars");
        updateCarsButton.setColumns(6);
        updateCarsButton.setRows(1);
        updateCarsButton.setImageScaleType(ImageScaleType.FIT);
        updateCarsButton.setImageUrl(viberService.getIconEndpoint() + CAR_ICON_FILENAME);
        updateCarsButton.setFrame(frame);
        updateCarsButton.setTextSize(TextSize.SMALL);
        updateCarsButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        updateCarsButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        updateCarsButton.setBackgroundColor("#c0c3c9");
        buttons.add(updateCarsButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.settings.back"));
        backButton.setActionBody("callback.settings.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotMessageButtonsMenuKeyabord() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button showMessageButton = new Button();
        showMessageButton.setText("Показать текст сообщения");
        showMessageButton.setActionBody("callback.botMessageButtonsMenu.showMessageButton");
        showMessageButton.setColumns(6);
        showMessageButton.setRows(1);
        showMessageButton.setImageScaleType(ImageScaleType.FIT);
        showMessageButton.setImageUrl(viberService.getIconEndpoint() + MESSAGE_ICON_FILENAME);
        showMessageButton.setFrame(frame);
        showMessageButton.setTextSize(TextSize.SMALL);
        showMessageButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        showMessageButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        showMessageButton.setBackgroundColor("#c0c3c9");
        buttons.add(showMessageButton);

        Button editTextButton = new Button();
        editTextButton.setText("Изменить текст сообщения");
        editTextButton.setActionBody("callback.botMessageButtonsMenu.editTextButton");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setImageScaleType(ImageScaleType.FIT);
        editTextButton.setImageUrl(viberService.getIconEndpoint() + EDIT_ICON_FILENAME);
        editTextButton.setFrame(frame);
        editTextButton.setTextSize(TextSize.SMALL);
        editTextButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        editTextButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        editTextButton.setBackgroundColor("#c0c3c9");
        buttons.add(editTextButton);

        Button listOfButtonsButton = new Button();
        listOfButtonsButton.setText("Список кнопок");
        listOfButtonsButton.setActionBody("callback.botMessageButtonsMenu.listButton");
        listOfButtonsButton.setColumns(6);
        listOfButtonsButton.setRows(1);
        listOfButtonsButton.setImageScaleType(ImageScaleType.FIT);
        listOfButtonsButton.setImageUrl(viberService.getIconEndpoint() + LIST_ICON_FILENAME);
        listOfButtonsButton.setFrame(frame);
        listOfButtonsButton.setTextSize(TextSize.SMALL);
        listOfButtonsButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        listOfButtonsButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        listOfButtonsButton.setBackgroundColor("#c0c3c9");
        buttons.add(listOfButtonsButton);

        Button deleteButton = new Button();
        deleteButton.setText("Удалить кнопку");
        deleteButton.setActionBody("callback.botMessageButtonsMenu.deleteButton");
        deleteButton.setColumns(3);
        deleteButton.setRows(1);
        deleteButton.setImageScaleType(ImageScaleType.FIT);
        deleteButton.setImageUrl(viberService.getIconEndpoint() + DELETE_ICON_FILENAME);
        deleteButton.setFrame(frame);
        deleteButton.setTextSize(TextSize.SMALL);
        deleteButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        deleteButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        deleteButton.setBackgroundColor("#c0c3c9");
        buttons.add(deleteButton);

        Button addButton = new Button();
        addButton.setText("Добавить кнопку");
        addButton.setActionBody("callback.botMessageButtonsMenu.addButton");
        addButton.setColumns(3);
        addButton.setImageScaleType(ImageScaleType.FIT);
        addButton.setImageUrl(viberService.getIconEndpoint() + ADD_ICON_FILENAME);
        addButton.setFrame(frame);
        addButton.setTextSize(TextSize.SMALL);
        addButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        addButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        addButton.setBackgroundColor("#c0c3c9");
        buttons.add(addButton);

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("callback.botMessageButtonsMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotMessageMenuKeyabord() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button listButton = new Button();
        listButton.setText("Список сообщений");
        listButton.setActionBody("callback.botMessageMenu.list");
        listButton.setColumns(6);
        listButton.setRows(1);
        listButton.setImageScaleType(ImageScaleType.FIT);
        listButton.setImageUrl(viberService.getIconEndpoint() + LIST_ICON_FILENAME);
        listButton.setFrame(frame);
        listButton.setTextSize(TextSize.SMALL);
        listButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        listButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        listButton.setBackgroundColor("#c0c3c9");
        buttons.add(listButton);

        Button editTextButton = new Button();
        editTextButton.setText("Изменить сообщение");
        editTextButton.setActionBody("callback.botMessageMenu.edit");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setImageScaleType(ImageScaleType.FIT);
        editTextButton.setImageUrl(viberService.getIconEndpoint() + EDIT_ICON_FILENAME);
        editTextButton.setFrame(frame);
        editTextButton.setTextSize(TextSize.SMALL);
        editTextButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        editTextButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        editTextButton.setBackgroundColor("#c0c3c9");
        buttons.add(editTextButton);

        Button addButton = new Button();
        addButton.setText("Добавить сообщение");
        addButton.setActionBody("callback.botMessageMenu.add");
        addButton.setColumns(3);
        addButton.setRows(1);
        addButton.setImageScaleType(ImageScaleType.FIT);
        addButton.setImageUrl(viberService.getIconEndpoint() + ADD_ICON_FILENAME);
        addButton.setFrame(frame);
        addButton.setTextSize(TextSize.SMALL);
        addButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        addButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        addButton.setBackgroundColor("#c0c3c9");
        buttons.add(addButton);

        Button deleteButton = new Button();
        deleteButton.setText("Удалить сообщение");
        deleteButton.setActionBody("callback.botMessageMenu.delete");
        deleteButton.setColumns(3);
        deleteButton.setRows(1);
        deleteButton.setImageScaleType(ImageScaleType.FIT);
        deleteButton.setImageUrl(viberService.getIconEndpoint() + DELETE_ICON_FILENAME);
        deleteButton.setFrame(frame);
        deleteButton.setTextSize(TextSize.SMALL);
        deleteButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        deleteButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        deleteButton.setBackgroundColor("#c0c3c9");
        buttons.add(deleteButton);

        Button editOrderButton = new Button();
        editOrderButton.setText("Изменить порядок сообщений");
        editOrderButton.setActionBody("callback.botMessageMenu.editOrder");
        editOrderButton.setColumns(6);
        editOrderButton.setRows(1);
        editOrderButton.setImageScaleType(ImageScaleType.FIT);
        editOrderButton.setImageUrl(viberService.getIconEndpoint() + SEQUENCE_ICON_FILENAME);
        editOrderButton.setFrame(frame);
        editOrderButton.setTextSize(TextSize.SMALL);
        editOrderButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        editOrderButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        editOrderButton.setBackgroundColor("#c0c3c9");
        buttons.add(editOrderButton);

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("callback.botMessageMenu.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getAnswerTypeKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button positiveButton = new Button();
        positiveButton.setText("Положительный");
        positiveButton.setActionBody("callback.botMessageMenu.selectAnswerType.positive");
        positiveButton.setColumns(6);
        positiveButton.setRows(1);
        positiveButton.setImageScaleType(ImageScaleType.FIT);
        positiveButton.setImageUrl(viberService.getIconEndpoint() + YES_ICON_FILENAME);
        positiveButton.setFrame(frame);
        positiveButton.setTextSize(TextSize.SMALL);
        positiveButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        positiveButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        positiveButton.setBackgroundColor("#c0c3c9");
        buttons.add(positiveButton);

        Button negativeButton = new Button();
        negativeButton.setText("Отрицательный");
        negativeButton.setActionBody("callback.botMessageMenu.selectAnswerType.negative");
        negativeButton.setColumns(6);
        negativeButton.setRows(1);
        negativeButton.setImageScaleType(ImageScaleType.FIT);
        negativeButton.setImageUrl(viberService.getIconEndpoint() + NO_ICON_FILENAME);
        negativeButton.setFrame(frame);
        negativeButton.setTextSize(TextSize.SMALL);
        negativeButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        negativeButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        negativeButton.setBackgroundColor("#c0c3c9");
        buttons.add(negativeButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotUsagePeriodMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button inChatButton = new Button();
        inChatButton.setText((environmentConfig.getEnabled() == true ? "Выключить" : "Включить") + " бота");
        inChatButton.setActionBody("Включить/выключить бота");
        inChatButton.setColumns(6);
        inChatButton.setRows(1);
        inChatButton.setImageScaleType(ImageScaleType.FIT);
        inChatButton.setImageUrl(viberService.getIconEndpoint() + ON_OFF_ICON_FILENAME);
        inChatButton.setFrame(frame);
        inChatButton.setTextSize(TextSize.SMALL);
        inChatButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        inChatButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        inChatButton.setBackgroundColor("#c0c3c9");
        buttons.add(inChatButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.botUsagePeriodMenu.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.botUsagePeriodMenu.back"));
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getConfirmKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button confirmButton = new Button();
        confirmButton.setText("Подтвердить");
        confirmButton.setActionBody("callback.confirm");
        confirmButton.setColumns(6);
        confirmButton.setRows(1);
        confirmButton.setImageScaleType(ImageScaleType.FIT);
        confirmButton.setImageUrl(viberService.getIconEndpoint() + CONFIRM_ICON_FILENAME);
        confirmButton.setFrame(frame);
        confirmButton.setTextSize(TextSize.SMALL);
        confirmButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        confirmButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        confirmButton.setBackgroundColor("#c0c3c9");
        buttons.add(confirmButton);

        Button rejectButton = new Button();
        rejectButton.setText("Отклонить");
        rejectButton.setActionBody("callback.reject");
        rejectButton.setColumns(6);
        rejectButton.setRows(1);
        rejectButton.setImageScaleType(ImageScaleType.FIT);
        rejectButton.setImageUrl(viberService.getIconEndpoint() + REJECT_ICON_FILENAME);
        rejectButton.setFrame(frame);
        rejectButton.setTextSize(TextSize.SMALL);
        rejectButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        rejectButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        rejectButton.setBackgroundColor("#c0c3c9");
        buttons.add(rejectButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBackKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("callback.back");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setImageScaleType(ImageScaleType.FIT);
        backButton.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        backButton.setFrame(frame);
        backButton.setTextSize(TextSize.SMALL);
        backButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        backButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        backButton.setBackgroundColor("#c0c3c9");
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    

    //endregion

    //region UserKeyboards

    public Keyboard getYesNoKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button yesButton = new Button();
        yesButton.setText(localeMessageService.getMessage("button.yes"));
        yesButton.setActionBody(localeMessageService.getMessage("button.yes"));
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setImageScaleType(ImageScaleType.FIT);
        yesButton.setImageUrl(viberService.getIconEndpoint() + YES_ICON_FILENAME);
        yesButton.setFrame(frame);
        yesButton.setTextSize(TextSize.SMALL);
        yesButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        yesButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        yesButton.setBackgroundColor("#c0c3c9");
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText(localeMessageService.getMessage("button.no"));
        noButton.setActionBody(localeMessageService.getMessage("button.no"));
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setImageScaleType(ImageScaleType.FIT);
        noButton.setImageUrl(viberService.getIconEndpoint() + NO_ICON_FILENAME);
        noButton.setFrame(frame);
        noButton.setTextSize(TextSize.SMALL);
        noButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        noButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        noButton.setBackgroundColor("#c0c3c9");
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getEndDialogKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button startNewDialogButton = new Button();
        startNewDialogButton.setText(localeMessageService.getMessage("button.userDialog.startNewDialog"));
        startNewDialogButton.setActionBody(localeMessageService.getMessage("button.userDialog.startNewDialog"));
        startNewDialogButton.setColumns(6);
        startNewDialogButton.setRows(1);
        startNewDialogButton.setImageScaleType(ImageScaleType.FIT);
        startNewDialogButton.setImageUrl(viberService.getIconEndpoint() + MESSAGE_ICON_FILENAME);
        startNewDialogButton.setFrame(frame);
        startNewDialogButton.setTextSize(TextSize.SMALL);
        startNewDialogButton.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        startNewDialogButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        startNewDialogButton.setBackgroundColor("#c0c3c9");
        buttons.add(startNewDialogButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getWithoutPhotoKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button withoutPhoto = new Button();
        withoutPhoto.setText(localeMessageService.getMessage("button.postponeMessage.withoutPhoto"));
        withoutPhoto.setActionBody(localeMessageService.getMessage("button.postponeMessage.withoutPhoto"));
        withoutPhoto.setColumns(6);
        withoutPhoto.setRows(1);
        withoutPhoto.setImageScaleType(ImageScaleType.FIT);
        // withoutPhoto.setImageUrl(viberService.getIconEndpoint() + BACK_ICON_FILENAME);
        withoutPhoto.setFrame(frame);
        withoutPhoto.setTextSize(TextSize.SMALL);
        withoutPhoto.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        withoutPhoto.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        withoutPhoto.setBackgroundColor("#c0c3c9");
        buttons.add(withoutPhoto);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getSharePhoneKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        Button sharePhone = new Button();
        sharePhone.setText("Поделиться номером телефона");
        sharePhone.setActionBody("callback.sharePhone");
        sharePhone.setActionType(ActionType.SHARE_PHONE);
        sharePhone.setColumns(6);
        sharePhone.setRows(1);
        sharePhone.setImageScaleType(ImageScaleType.FIT);
        sharePhone.setImageUrl(viberService.getIconEndpoint() + PHONE_ICON_FILENAME);
        sharePhone.setFrame(frame);
        sharePhone.setTextSize(TextSize.SMALL);
        sharePhone.setTextVerticalAlign(TextVerticalAlign.BOTTOM);
        sharePhone.setTextPaddings(Arrays.asList(0, 0, 12, 0));
        sharePhone.setBackgroundColor("#c0c3c9");

        buttons.add(sharePhone);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    //endregion

    public Keyboard generateKeyboard(BotMessage botMessage) {
        Keyboard keyboard = new Keyboard();

        List<by.autocapital.models.Button> messageButtons = List.copyOf(botMessage.getButtons()).stream().sorted(Comparator.comparing(by.autocapital.models.Button::getId)).collect(Collectors.toList());
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

        if (messageButtons.isEmpty()) {
            return null;
        }

        for (by.autocapital.models.Button button : messageButtons) {
            Button newButton = new Button();
            newButton.setText(button.getText());
            newButton.setActionBody(button.getCallbackData());
            newButton.setColumns(6);
            newButton.setRows(1);
            newButton.setFrame(frame);
            newButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
            newButton.setBackgroundColor("#c0c3c9");

            buttons.add(newButton);
        }

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard generatePagableKeyboard(List<String> items, Integer page) {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setBorderWidth(2);
        frame.setCornerRadius(10);
        frame.setBorderColor("#428dff");

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
            
            button.setFrame(frame);
            button.setTextPaddings(Arrays.asList(0, 0, 12, 0));
            button.setBackgroundColor("#c0c3c9");

            buttons.add(button);
        }

        if (items.size() > MAX_ITEMS_AT_PAGE) {
            Button previousPageButton = new Button();
            previousPageButton.setText("<<<");
            previousPageButton.setActionBody("Назад");
            previousPageButton.setColumns(3);
            previousPageButton.setRows(1);
            previousPageButton.setBackgroundColor("#2db9b9");
            previousPageButton.setFrame(frame);
            previousPageButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
            previousPageButton.setBackgroundColor("#c0c3c9");
            buttons.add(previousPageButton);

            Button nextPageButton = new Button();
            nextPageButton.setText(">>>");
            nextPageButton.setActionBody("Далее");
            nextPageButton.setColumns(3);
            nextPageButton.setRows(1);
            nextPageButton.setBackgroundColor("#2db9b9");
            nextPageButton.setFrame(frame);
            nextPageButton.setTextPaddings(Arrays.asList(0, 0, 12, 0));
            nextPageButton.setBackgroundColor("#c0c3c9");
            buttons.add(nextPageButton);
        }

        keyboard.setButtons(buttons);
        return keyboard;
    }

}
