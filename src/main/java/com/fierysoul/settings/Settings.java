package com.fierysoul.settings;

import com.fierysoul.perms.PermGroups;

public class Settings {

    public String BOT_TOKEN = "";
    public String BOT_USERNAME = "";

    public String HELP_COMMAND_ANSWER = "";

    public Command[] commands = new Command[] {new Command("help", "Тестовый ответ", PermGroups.ALL)};

}
