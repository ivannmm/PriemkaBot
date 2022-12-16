package com.fierysoul.command;

import com.fierysoul.Bot;
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
                            if (isName(text)) {
                                appeal.setName(text);
                            } else {
                                return "¬ведите корректное им€";
                            }
                        }
                        case GET_SNILS -> {
                            if (isSnils(text)) {
                                appeal.setSnils(text);
                            } else {
                                return "¬ведите корректный снилс";
                            }
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

    public boolean isName(String text) {
        String[] parts = text.split(" ");
        return parts.length > 1;
    }

    public boolean isSnils(String text) {
        if (text.length() != 11)
            return false;

        if (text.matches("[^0-9-]"))
            return false;

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(String.valueOf(text.charAt(i))) * (9 - i);
        }

        int checkDigit = 0;
        if (sum < 100)
            checkDigit = sum;
        else if (sum > 101) {
            checkDigit = sum % 101;
            if (checkDigit == 100)
                checkDigit = 0;
        }
        return checkDigit == Integer.parseInt(text.substring(9));
    }

}
