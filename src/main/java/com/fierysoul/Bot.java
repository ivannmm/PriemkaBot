package com.fierysoul;

import com.fierysoul.command.AutoAnswerCommand;
import com.fierysoul.command.CreateAppealCommand;
import com.fierysoul.command.NonCommand;
import com.fierysoul.settings.Command;
import com.fierysoul.settings.Settings;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public final class Bot extends TelegramLongPollingCommandBot {

    public Settings settings;

    public NonCommand nonCommand;

    public Bot(Settings settings) {
        this.settings = settings;

        for (Command command : settings.commands) {
            register(new AutoAnswerCommand(command.commandName, command.commandDesc, command.answer));
        }

        register(new CreateAppealCommand("appeal", "Appeal create", settings));
        nonCommand = new NonCommand();
        new Thread(() -> {
            try {
                Thread.sleep(1000 * 60);
                if (CreateAppealCommand.needSave)
                    if (saveData())
                        CreateAppealCommand.needSave = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return settings.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return settings.BOT_TOKEN;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        Long userId = msg.getFrom().getId();

        sendMessage(this, chatId, nonCommand.nonCommandExecute(userId, chatId, msg.getText()));
    }

    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    public boolean saveData() {
        try {
            Starter.fileManager.writeFile("botSettings", settings);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendMessage(AbsSender absSender, Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

