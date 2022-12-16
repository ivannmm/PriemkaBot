package com.fierysoul.settings;

import com.fierysoul.perms.PermGroups;

public class Command {

    public String commandName, answer, commandDesc;
    public PermGroups userGroup;

    public Command(String commandName, String commandDesc, String answer, PermGroups userGroup) {
        this.commandName = commandName;
        this.commandDesc = commandDesc;
        this.answer = answer;
        this.userGroup = userGroup;
    }

}
