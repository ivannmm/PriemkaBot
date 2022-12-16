package com.fierysoul.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AutoAnswerCommand extends BotCommand{

    String answer;

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     */
    public AutoAnswerCommand(String commandIdentifier, String description, String autoAnswer) {
        super(commandIdentifier, description);
        this.answer = autoAnswer;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chat.getId().toString());
        message.setText(answer);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
