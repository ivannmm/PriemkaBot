package com.fierysoul.command;

import com.fierysoul.Bot;
import com.fierysoul.appeals.CompetitiveListsAppeal;
import com.fierysoul.settings.Settings;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class CreateAppealCommand extends BotCommand {

    public static boolean needSave = false;
    Settings botSettings;

    public CreateAppealCommand(String commandIdentifier, String description, Settings settings) {
        super(commandIdentifier, description);
        this.botSettings = settings;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        boolean allClosed = false;
        if (CompetitiveListsAppeal.ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.containsKey(user.getId())) {
            List<CompetitiveListsAppeal> listsAppeals = CompetitiveListsAppeal.ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.get(user.getId());
            for (CompetitiveListsAppeal appeal : listsAppeals) {
                if (!appeal.isClosed()) {
                    Bot.sendMessage(absSender, chat.getId(), String.format("У вас уже есть одно открытое обращение, id - *%d*. Пожалуйста, заполните его", appeal.getAppealId()));
                    Bot.sendMessage(absSender, chat.getId(), appeal.getCurrentStage().message);
                    return;
                }
            }
            allClosed = true;
        }
        if (!CompetitiveListsAppeal.ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.containsKey(user.getId()) || allClosed) {
            Bot.sendMessage(absSender, chat.getId(), String.format("Создано новое обращение, id - *%d*", ++botSettings.lastAppealId));
            CompetitiveListsAppeal appeal = new CompetitiveListsAppeal(user.getId(), chat.getId(), botSettings.lastAppealId);
            Bot.sendMessage(absSender, chat.getId(), appeal.getCurrentStage().message);
        }
    }
}
