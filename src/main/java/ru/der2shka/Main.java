package ru.der2shka;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.der2shka.telegram.TelegramBot;

import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("Default charset: " + Charset.defaultCharset());

        startBot();
    }

    /**
     * Try to start telegram bot.
     * **/
    private static void startBot() {
        try {
            TelegramBotsApi botsApi =
                    new TelegramBotsApi(
                            DefaultBotSession.class
                    );
            botsApi.registerBot(new TelegramBot());

            System.out.println("*** Bot was started! ***");
        } catch (Exception ex) {
            System.out.println("*** Bot wasn't started:( ***");
            ex.printStackTrace();
        }
    }
}