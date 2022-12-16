package com.fierysoul.command;

import com.fierysoul.appeals.CompetitiveListsAppeal;

import java.util.List;

public class NonCommand {

    public String nonCommandExecute(Long userId, Long chatId, String text) {

        if (text == null)
            return null;

        if (CompetitiveListsAppeal.ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.containsKey(userId)) {
            List<CompetitiveListsAppeal> listsAppeals = CompetitiveListsAppeal.ALL_CURRENT_COMPETITIVE_LISTS_APPEALS.get(userId);
            for (CompetitiveListsAppeal appeal : listsAppeals) {
                if (!appeal.isClosed()) {
                    switch (appeal.getCurrentStage()) {
                        case GET_NAME -> {
                            appeal.setName(text);
                        }
                        case GET_SNILS -> {
                            appeal.setSnils(text);
                        }
                        case GET_COMMENT -> {
                            appeal.setComment(text);
                        }
                    }

                    appeal.changeState();
                    return appeal.getCurrentStage().message;
                }
            }
        }
        return null;
    }

}
