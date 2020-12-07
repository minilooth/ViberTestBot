package by.testbot.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.testbot.models.viber.Button;
import by.testbot.models.viber.Frame;
import by.testbot.models.viber.Keyboard;
import by.testbot.models.enums.button.ActionType;
import by.testbot.services.other.LocaleMessageService;

public class KeyboardSource {
    @Autowired
    private LocaleMessageService localeMessageService;

    //region AdminKeyboards

    public Keyboard getAdminMainMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        Frame frame = new Frame();

        frame.setCornerRadius(10);
        frame.setBorderWidth(1);
        frame.setBorderColor("#2db9b9");

        Button postponeMessageButton = new Button();
        postponeMessageButton.setFrame(frame);
        postponeMessageButton.setText(localeMessageService.getMessage("button.mainMenu.postponeMessage"));
        postponeMessageButton.setActionBody(localeMessageService.getMessage("button.mainMenu.postponeMessage"));
        postponeMessageButton.setColumns(3);
        postponeMessageButton.setRows(1);
        postponeMessageButton.setBackgroundColor("#2db9b9");
        postponeMessageButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(postponeMessageButton);

        Button listOfManagersButton = new Button();
        listOfManagersButton.setFrame(frame);
        listOfManagersButton.setText(localeMessageService.getMessage("button.mainMenu.managers"));
        listOfManagersButton.setActionBody(localeMessageService.getMessage("button.mainMenu.managers"));
        listOfManagersButton.setColumns(3);
        listOfManagersButton.setRows(1);
        listOfManagersButton.setBackgroundColor("#2db9b9");
        listOfManagersButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(listOfManagersButton);

        Button listOfClientsButton = new Button();
        listOfClientsButton.setFrame(frame);
        listOfClientsButton.setText(localeMessageService.getMessage("button.mainMenu.clients"));
        listOfClientsButton.setActionBody(localeMessageService.getMessage("button.mainMenu.clients"));
        listOfClientsButton.setColumns(3);
        listOfClientsButton.setRows(1);
        listOfClientsButton.setBackgroundColor("#2db9b9");
        listOfClientsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(listOfClientsButton);

        Button reportButton = new Button();
        reportButton.setFrame(frame);
        reportButton.setText(localeMessageService.getMessage("button.mainMenu.report"));
        reportButton.setActionBody(localeMessageService.getMessage("button.mainMenu.report"));
        reportButton.setColumns(3);
        reportButton.setRows(1);
        reportButton.setBackgroundColor("#2db9b9");
        reportButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportButton);

        Button integrationButton = new Button();
        integrationButton.setFrame(frame);
        integrationButton.setText(localeMessageService.getMessage("button.mainMenu.integrations"));
        integrationButton.setActionBody(localeMessageService.getMessage("button.mainMenu.integrations"));
        integrationButton.setColumns(3);
        integrationButton.setRows(1);
        integrationButton.setBackgroundColor("#2db9b9");
        integrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(integrationButton);

        Button settingsButton = new Button();
        settingsButton.setFrame(frame);
        settingsButton.setText(localeMessageService.getMessage("button.mainMenu.settings"));
        settingsButton.setActionBody(localeMessageService.getMessage("button.mainMenu.settings"));
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
        getListOfManagersButton.setActionBody(localeMessageService.getMessage("button.managersMenu.getManagersList"));
        getListOfManagersButton.setColumns(3);
        getListOfManagersButton.setRows(1);
        getListOfManagersButton.setBackgroundColor("#2db9b9");
        getListOfManagersButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(getListOfManagersButton);

        Button addManagerButton = new Button();
        addManagerButton.setText(localeMessageService.getMessage("button.managersMenu.addManager"));
        addManagerButton.setActionBody(localeMessageService.getMessage("button.managersMenu.addManager"));
        addManagerButton.setColumns(3);
        addManagerButton.setRows(1);
        addManagerButton.setBackgroundColor("#2db9b9");
        addManagerButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(addManagerButton);

        Button deleteManagerButton = new Button();
        deleteManagerButton.setText(localeMessageService.getMessage("button.managersMenu.deleteManager"));
        deleteManagerButton.setActionBody(localeMessageService.getMessage("button.managersMenu.deleteManager"));
        deleteManagerButton.setColumns(3);
        deleteManagerButton.setRows(1);
        deleteManagerButton.setBackgroundColor("#2db9b9");
        deleteManagerButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(deleteManagerButton);

        Button changePrivilegiesButton = new Button();
        changePrivilegiesButton.setText(localeMessageService.getMessage("button.managersMenu.changeManagerPrivilegies"));
        changePrivilegiesButton.setActionBody(localeMessageService.getMessage("button.managersMenu.changeManagerPrivilegies"));
        changePrivilegiesButton.setColumns(3);
        changePrivilegiesButton.setRows(1);
        changePrivilegiesButton.setBackgroundColor("#2db9b9");
        changePrivilegiesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(changePrivilegiesButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.managersMenu.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.managersMenu.back"));
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
        getListOfClientsAndOtherInformationButton.setActionBody(localeMessageService.getMessage("button.clientsMenu.getClientsListAndOtherInformation"));
        getListOfClientsAndOtherInformationButton.setColumns(6);
        getListOfClientsAndOtherInformationButton.setRows(1);
        getListOfClientsAndOtherInformationButton.setBackgroundColor("#2db9b9");
        getListOfClientsAndOtherInformationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(getListOfClientsAndOtherInformationButton);

        Button additionalActionsWithClientsButton = new Button();
        additionalActionsWithClientsButton.setText(localeMessageService.getMessage("button.clientsMenu.additionalActionsWithClients"));
        additionalActionsWithClientsButton.setActionBody(localeMessageService.getMessage("button.clientsMenu.additionalActionsWithClients"));
        additionalActionsWithClientsButton.setColumns(6);
        additionalActionsWithClientsButton.setRows(1);
        additionalActionsWithClientsButton.setBackgroundColor("#2db9b9");
        additionalActionsWithClientsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(additionalActionsWithClientsButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.clientsMenu.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.clientsMenu.back"));
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
        reportAboutManagersWorkButton.setActionBody(localeMessageService.getMessage("button.reportMenu.reportAboutManagersWork"));
        reportAboutManagersWorkButton.setColumns(6);
        reportAboutManagersWorkButton.setRows(1);
        reportAboutManagersWorkButton.setBackgroundColor("#2db9b9");
        reportAboutManagersWorkButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportAboutManagersWorkButton);

        Button reportAboutBotWorkButton = new Button();
        reportAboutBotWorkButton.setText(localeMessageService.getMessage("button.reportMenu.reportAboutBotWork"));
        reportAboutBotWorkButton.setActionBody(localeMessageService.getMessage("button.reportMenu.reportAboutBotWork"));
        reportAboutBotWorkButton.setColumns(6);
        reportAboutBotWorkButton.setRows(1);
        reportAboutBotWorkButton.setBackgroundColor("#2db9b9");
        reportAboutBotWorkButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportAboutBotWorkButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.reportMenu.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.reportMenu.back"));
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
        addOrDeleteIntegrationButton.setActionBody(localeMessageService.getMessage("button.integrationsMenu.addOrDeleteIntegration"));
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
        newIntegrationButton.setActionBody(localeMessageService.getMessage("button.integrationsMenu.newIntegrations"));
        newIntegrationButton.setColumns(6);
        newIntegrationButton.setRows(1);
        newIntegrationButton.setBackgroundColor("#2db9b9");
        newIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(newIntegrationButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.integrationsMenu.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.integrationsMenu.back"));
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
        editTextButton.setActionBody(localeMessageService.getMessage("button.settings.editTextsWhichBotSend"));
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setBackgroundColor("#2db9b9");
        editTextButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(editTextButton);

        Button setBotUsingPeriodButton = new Button();
        setBotUsingPeriodButton.setText(localeMessageService.getMessage("button.settings.botUsagePeriod"));
        setBotUsingPeriodButton.setActionBody(localeMessageService.getMessage("button.settings.botUsagePeriod"));
        setBotUsingPeriodButton.setColumns(6);
        setBotUsingPeriodButton.setRows(1);
        setBotUsingPeriodButton.setBackgroundColor("#2db9b9");
        setBotUsingPeriodButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(setBotUsingPeriodButton);

        Button backButton = new Button();
        backButton.setText(localeMessageService.getMessage("button.settings.back"));
        backButton.setActionBody(localeMessageService.getMessage("button.settings.back"));
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBotUsagePeriodMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button inChatButton = new Button();
        inChatButton.setText(localeMessageService.getMessage("button.botUsagePeriodMenu.inChat"));
        inChatButton.setActionBody(localeMessageService.getMessage("button.botUsagePeriodMenu.inChat"));
        inChatButton.setColumns(6);
        inChatButton.setRows(1);
        inChatButton.setBackgroundColor("#2db9b9");
        inChatButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(inChatButton);

        Button atNightButton = new Button();
        atNightButton.setText(localeMessageService.getMessage("button.botUsagePeriodMenu.atNight"));
        atNightButton.setActionBody(localeMessageService.getMessage("button.botUsagePeriodMenu.atNight"));
        atNightButton.setColumns(6);
        atNightButton.setRows(1);
        atNightButton.setBackgroundColor("#2db9b9");
        atNightButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(atNightButton);

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

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("Назад");
        backButton.setColumns(6);
        backButton.setRows(1);
        backButton.setBackgroundColor("#2db9b9");
        backButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(backButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public Keyboard getBackKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button backButton = new Button();
        backButton.setText("Назад");
        backButton.setActionBody("Назад");
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

}
