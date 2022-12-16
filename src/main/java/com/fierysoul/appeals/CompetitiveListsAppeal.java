package com.fierysoul.appeals;

import com.fierysoul.command.CreateAppealCommand;

import java.util.*;

public class CompetitiveListsAppeal {

    public static Map<Long, List<CompetitiveListsAppeal>> ALL_CURRENT_COMPETITIVE_LISTS_APPEALS = new HashMap<>();

    String name, snils, comment;
    long timeCreate;
    long userId, chatId, appealId;
    boolean isClosed = false;
    AppealStage currentStage;

    public CompetitiveListsAppeal(long userId, long chatId, long appealId) {
        this.chatId = chatId;
        if (ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.containsKey(userId)) {
            ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.get(userId).add(this);
        } else {
            List<CompetitiveListsAppeal> listsAppeals = List.of(this);
            ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.put(userId, listsAppeals);
        }
        currentStage = AppealStage.GET_NAME;
        CreateAppealCommand.needSave = true;
        this.appealId = appealId;
    }

    public boolean changeState() {
        currentStage = currentStage.nextStage;
        if (currentStage.nextStage ==  null)
            isClosed = true;
        return isClosed;
    }

    public long getAppealId() {
        return appealId;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public AppealStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(AppealStage currentStage) {
        this.currentStage = currentStage;
    }

    public String toString() {
        return String.format("Id обращения - %d\nИмя - %s\nСНИЛС - %s\nОписание проблемы - %s\nChatId - %d", appealId, name, snils, comment, chatId);
    }

    public enum AppealStage {
        CLOSED("Заявка принята в обработку", null),
        GET_COMMENT("Опишите проблему", CLOSED),
        GET_SNILS("Введите СНИЛС абитуриента, формат - *XXXXXXXXXXX*", GET_COMMENT),
        GET_NAME("Введите ФИО абитуриента", GET_SNILS);

        public final String message;
        public final AppealStage nextStage;

        AppealStage(String message, AppealStage nextStage) {
            this.message = message;
            this.nextStage = nextStage;
        }
    }

}
