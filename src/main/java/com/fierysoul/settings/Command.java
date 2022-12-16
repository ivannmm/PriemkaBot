package com.fierysoul.settings;

import com.fierysoul.perms.PermGroups;

public class Command {

    public String commandName;
    public PermGroups userGroup;
    public String answer;

    public Command(String commandName, String answer, PermGroups userGroup) {
        this.commandName = commandName;
        this.answer = answer;
        this.userGroup = userGroup;
    }

}
