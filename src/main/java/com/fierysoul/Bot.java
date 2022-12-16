package com.fierysoul;

import com.fierysoul.command.AutoAnswerCommand;
import com.fierysoul.settings.Command;
import com.fierysoul.settings.Settings;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.List;

public final class Bot extends TelegramLongPollingCommandBot {

    public Settings settings;

    public Bot(Settings settings) {
        this.settings = settings;

        for (Command command : settings.commands) {
            register(new AutoAnswerCommand(command.commandName, command.commandDesc, command.answer));
        }

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

    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }
}

