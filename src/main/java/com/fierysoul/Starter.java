package com.fierysoul;

import com.fierysoul.json.FileManager;
import com.fierysoul.settings.Settings;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class Starter {

    static FileManager fileManager;

    public static void main(String[] args) {

        fileManager = new FileManager(new File("./config"));

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(loadSettings()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Settings loadSettings() throws Exception {
        return fileManager.readOrWriteDefault(Settings.class, "botSettings");
    }

}
