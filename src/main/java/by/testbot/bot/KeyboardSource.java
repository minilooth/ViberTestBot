package by.testbot.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.testbot.models.Button;
import by.testbot.models.Keyboard;

public class KeyboardSource {

    public static Keyboard getAdminMainMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button postponeMessageButton = new Button();
        postponeMessageButton.setText("Отложенное сообщение");
        postponeMessageButton.setActionBody("Отложенное сообщение");
        postponeMessageButton.setColumns(3);
        postponeMessageButton.setRows(1);
        postponeMessageButton.setBackgroundColor("#2db9b9");
        postponeMessageButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(postponeMessageButton);

        Button listOfManagersButton = new Button();
        listOfManagersButton.setText("Список менеджеров");
        listOfManagersButton.setActionBody("Список менеджеров");
        listOfManagersButton.setColumns(3);
        listOfManagersButton.setRows(1);
        listOfManagersButton.setBackgroundColor("#2db9b9");
        listOfManagersButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(listOfManagersButton);

        Button listOfClientsButton = new Button();
        listOfClientsButton.setText("Список клиентов");
        listOfClientsButton.setActionBody("Список клиентов");
        listOfClientsButton.setColumns(3);
        listOfClientsButton.setRows(1);
        listOfClientsButton.setBackgroundColor("#2db9b9");
        listOfClientsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(listOfClientsButton);

        Button reportButton = new Button();
        reportButton.setText("Отчет");
        reportButton.setActionBody("Отчет");
        reportButton.setColumns(3);
        reportButton.setRows(1);
        reportButton.setBackgroundColor("#2db9b9");
        reportButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportButton);

        Button integrationButton = new Button();
        integrationButton.setText("Интеграции");
        integrationButton.setActionBody("Интеграции");
        integrationButton.setColumns(3);
        integrationButton.setRows(1);
        integrationButton.setBackgroundColor("#2db9b9");
        integrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(integrationButton);

        Button settingsButton = new Button();
        settingsButton.setText("Настройка");
        settingsButton.setActionBody("Настройка");
        settingsButton.setColumns(3);
        settingsButton.setRows(1);
        settingsButton.setBackgroundColor("#2db9b9");
        settingsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(settingsButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public static Keyboard getListOfManagersMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button getListOfManagersButton = new Button();
        getListOfManagersButton.setText("Получить список менеджеров");
        getListOfManagersButton.setActionBody("Получить список менеджеров");
        getListOfManagersButton.setColumns(3);
        getListOfManagersButton.setRows(1);
        getListOfManagersButton.setBackgroundColor("#2db9b9");
        getListOfManagersButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(getListOfManagersButton);

        Button addManagerButton = new Button();
        addManagerButton.setText("Добавить менеджера");
        addManagerButton.setActionBody("Добавить менеджера");
        addManagerButton.setColumns(3);
        addManagerButton.setRows(1);
        addManagerButton.setBackgroundColor("#2db9b9");
        addManagerButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(addManagerButton);

        Button deleteManagerButton = new Button();
        deleteManagerButton.setText("Удалить менеджера");
        deleteManagerButton.setActionBody("Удалить менеджера");
        deleteManagerButton.setColumns(3);
        deleteManagerButton.setRows(1);
        deleteManagerButton.setBackgroundColor("#2db9b9");
        deleteManagerButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(deleteManagerButton);

        Button changePrivilegesButton = new Button();
        changePrivilegesButton.setText("Изменение привелегий");
        changePrivilegesButton.setActionBody("Изменение привелегий");
        changePrivilegesButton.setColumns(3);
        changePrivilegesButton.setRows(1);
        changePrivilegesButton.setBackgroundColor("#2db9b9");
        changePrivilegesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(changePrivilegesButton);

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

    public static Keyboard getListOfClientsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();
        
        Button getListOfClientsAndOtherInformationButton = new Button();
        getListOfClientsAndOtherInformationButton.setText("Получить список клиентов и информацию о них");
        getListOfClientsAndOtherInformationButton.setActionBody("Получить список клиентов и ифнормацию о них");
        getListOfClientsAndOtherInformationButton.setColumns(6);
        getListOfClientsAndOtherInformationButton.setRows(1);
        getListOfClientsAndOtherInformationButton.setBackgroundColor("#2db9b9");
        getListOfClientsAndOtherInformationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(getListOfClientsAndOtherInformationButton);

        Button additionalActionsWithClientsButton = new Button();
        additionalActionsWithClientsButton.setText("Дополнительные операции с клиентами");
        additionalActionsWithClientsButton.setActionBody("Дополнительные операции с клиентами");
        additionalActionsWithClientsButton.setColumns(6);
        additionalActionsWithClientsButton.setRows(1);
        additionalActionsWithClientsButton.setBackgroundColor("#2db9b9");
        additionalActionsWithClientsButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(additionalActionsWithClientsButton);

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

    public static Keyboard getReportMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button reportAboutManagersWorkButton = new Button();
        reportAboutManagersWorkButton.setText("Отчет о работе менеджеров");
        reportAboutManagersWorkButton.setActionBody("Отчет о работе менеджеров");
        reportAboutManagersWorkButton.setColumns(6);
        reportAboutManagersWorkButton.setRows(1);
        reportAboutManagersWorkButton.setBackgroundColor("#2db9b9");
        reportAboutManagersWorkButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportAboutManagersWorkButton);

        Button reportAboutBotWorkButton = new Button();
        reportAboutBotWorkButton.setText("Отчет о работе бота");
        reportAboutBotWorkButton.setActionBody("Отчет о работе бота");
        reportAboutBotWorkButton.setColumns(6);
        reportAboutBotWorkButton.setRows(1);
        reportAboutBotWorkButton.setBackgroundColor("#2db9b9");
        reportAboutBotWorkButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(reportAboutBotWorkButton);

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

    public static Keyboard getIntegrationsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button addIntegrationButton = new Button();
        addIntegrationButton.setText("Добавление новой интеграции");
        addIntegrationButton.setActionBody("Добавление новой интеграции");
        addIntegrationButton.setColumns(6);
        addIntegrationButton.setRows(1);
        addIntegrationButton.setBackgroundColor("#2db9b9");
        addIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(addIntegrationButton);

        Button deleteIntegrationButton = new Button();
        deleteIntegrationButton.setText("Удаление интергации");
        deleteIntegrationButton.setActionBody("Удаление интеграции");
        deleteIntegrationButton.setColumns(6);
        deleteIntegrationButton.setRows(1);
        deleteIntegrationButton.setBackgroundColor("#2db9b9");
        deleteIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(deleteIntegrationButton);

        Button newIntegrationButton = new Button();
        newIntegrationButton.setText("Новые интеграции");
        newIntegrationButton.setActionBody("Новые интеграции");
        newIntegrationButton.setColumns(6);
        newIntegrationButton.setRows(1);
        newIntegrationButton.setBackgroundColor("#2db9b9");
        newIntegrationButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(newIntegrationButton);

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

    public static Keyboard getSettingsMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button editTextButton = new Button();
        editTextButton.setText("Редактировать текст, отправляемый ботом");
        editTextButton.setActionBody("Редактировать текст, отправляемый ботом");
        editTextButton.setColumns(6);
        editTextButton.setRows(1);
        editTextButton.setBackgroundColor("#2db9b9");
        editTextButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(editTextButton);

        Button setBotUsingPeriodButton = new Button();
        setBotUsingPeriodButton.setText("Настройка периода временного использования бота");
        setBotUsingPeriodButton.setActionBody("Настройка периода временного использования бота");
        setBotUsingPeriodButton.setColumns(6);
        setBotUsingPeriodButton.setRows(1);
        setBotUsingPeriodButton.setBackgroundColor("#2db9b9");
        setBotUsingPeriodButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(setBotUsingPeriodButton);

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

    public static Keyboard getSetBotUsagePeriodMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button inChatButton = new Button();
        inChatButton.setText("В чате(время обработки)");
        inChatButton.setActionBody("В чате(время обработки)");
        inChatButton.setColumns(6);
        inChatButton.setRows(1);
        inChatButton.setBackgroundColor("#2db9b9");
        inChatButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(inChatButton);

        Button atNightButton = new Button();
        atNightButton.setText("В ночное время");
        atNightButton.setActionBody("В ночное время");
        atNightButton.setColumns(6);
        atNightButton.setRows(1);
        atNightButton.setBackgroundColor("#2db9b9");
        atNightButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(atNightButton);

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

    public static Keyboard getPostponeMessageMenuKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button addTextAndPhotoButton = new Button();
        addTextAndPhotoButton.setText("Добавить текст + фото");
        addTextAndPhotoButton.setActionBody("Добавить текст + фото");
        addTextAndPhotoButton.setColumns(6);
        addTextAndPhotoButton.setRows(1);
        addTextAndPhotoButton.setBackgroundColor("#2db9b9");
        addTextAndPhotoButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(addTextAndPhotoButton);

        Button setDayAndTimeButton = new Button();
        setDayAndTimeButton.setText("Выбрать день и время");
        setDayAndTimeButton.setActionBody("Выбрать день и время");
        setDayAndTimeButton.setColumns(6);
        setDayAndTimeButton.setRows(1);
        setDayAndTimeButton.setBackgroundColor("#2db9b9");
        setDayAndTimeButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(setDayAndTimeButton);

        Button confirmSendMessageButton = new Button();
        confirmSendMessageButton.setText("Подтвердить отправку");
        confirmSendMessageButton.setActionBody("Подтвердить отправку");
        confirmSendMessageButton.setColumns(6);
        confirmSendMessageButton.setRows(1);
        confirmSendMessageButton.setBackgroundColor("#2db9b9");
        confirmSendMessageButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(confirmSendMessageButton);

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

    public static Keyboard getConfirmPostponeMessageKeyboard() {
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

    public static Keyboard getYesNoKeyboard() {
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

    public static Keyboard getEndDialogKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button startNewDialogButton = new Button();
        startNewDialogButton.setText("Начать новый диалог");
        startNewDialogButton.setActionBody("Начать новый диалог");
        startNewDialogButton.setColumns(6);
        startNewDialogButton.setRows(1);
        startNewDialogButton.setBackgroundColor("#2db9b9");
        startNewDialogButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(startNewDialogButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public static Keyboard getWhenWillBuyCarKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<Button> buttons = new ArrayList<>();

        Button yesButton = new Button();
        yesButton.setText("В ближайшее время");
        yesButton.setActionBody("В ближайшее время");
        yesButton.setColumns(6);
        yesButton.setRows(1);
        yesButton.setBackgroundColor("#2db9b9");
        yesButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(yesButton);

        Button noButton = new Button();
        noButton.setText("После НГ");
        noButton.setActionBody("После НГ");
        noButton.setColumns(6);
        noButton.setRows(1);
        noButton.setBackgroundColor("#2db9b9");
        noButton.setTextPaddings(Arrays.asList(12, 12, 12, 12));
        buttons.add(noButton);

        keyboard.setButtons(buttons);
        return keyboard;
    }

}
