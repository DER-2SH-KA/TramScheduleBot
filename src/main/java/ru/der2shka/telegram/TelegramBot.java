package ru.der2shka.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.der2shka.service.ParseWebsite;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class TelegramBot extends TelegramLongPollingBot {
    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void onUpdateReceived(Update update) {
        CompletableFuture.runAsync(() -> {

            if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                Long chatId = update.getMessage().getChatId();

                // Message receive.
                switch (messageText) {
                    case "/start":
                        String text = "Привет!";

                        sendMessage(chatId, text);
                        sendMenu(chatId, text);
                        break;

                    case "2 ЧС-КМ Б":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0002",
                                            "A",
                                            "11"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "2 КМ-ЧС Б":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0002",
                                            "B",
                                            "11"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "2 ЧС-КМ В":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0002",
                                            "A",
                                            "5"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "2 КМ-ЧС В":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0002",
                                            "B",
                                            "5"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "8 ЧС-Т Б":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0008",
                                            "A",
                                            "11"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "8 Т-ЧС Б":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0008",
                                            "B",
                                            "11"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "8 ЧС-Т В":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0008",
                                            "A",
                                            "5"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case "8 Т-ЧС В":
                        try {
                            String result = ParseWebsite
                                    .parseWebsite(
                                            "0008",
                                            "B",
                                            "5"
                                    )
                                    .get(5, TimeUnit.SECONDS);

                            sendMessage(chatId, result);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;

                    default:
                        sendMessage(chatId, "Извините, я не понимаю вас :(");
                        break;
                }
            }
        }, executor)
                .exceptionallyAsync( ex -> {
                            ex.printStackTrace();
                            return null;
                        }
                );
    }

    @Override
    public String getBotUsername() {
        return "TramScheduleBot";
    }

    @Override
    public String getBotToken() {
        String token = "";
        try {
            token = System.getenv("TgToken");
        }
        catch (Exception ex) {
            System.err.println("*** Value of Environment with key \"TgToken\" not found ***");
        }

        return token;
    }

    private void sendMessage(Long chatId, String text) {

        System.out.println("*** Start sendMessage() ***");

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            System.out.println("*** End sendMessage() ***");
        }
    }

    private void sendMenu(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Выберите маршурт");

        // UI-клавиатура.
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardList = new ArrayList();

        // Трамвай 2 Чистая Слобода - Карла Макрса Будни/Выходные.
        KeyboardRow row1 = new KeyboardRow();
        row1.add("2 ЧС-КМ Б");
        row1.add("2 ЧС-КМ В");

        // Трамвай 2 Карла Макрса - Чистая Слобода Будни/Выходные.
        KeyboardRow row2 = new KeyboardRow();
        row2.add("2 КМ-ЧС Б");
        row2.add("2 КМ-ЧС В");

        // Трамвай 8 Чистая Слобода - Титова Будни/Выходные.
        KeyboardRow row3 = new KeyboardRow();
        row1.add("8 ЧС-Т Б");
        row1.add("8 ЧС-Т В");

        // Трамвай 8 Титова - Чистая Слобода Будни/Выходные.
        KeyboardRow row4 = new KeyboardRow();
        row2.add("8 Т-ЧС Б");
        row2.add("8 Т-ЧС В");

        keyboardList.add(row1);
        keyboardList.add(row2);
        keyboardList.add(row3);
        keyboardList.add(row4);

        keyboardMarkup.setKeyboard(keyboardList); // Установка клавиатуры в шаблон.
        message.setReplyMarkup(keyboardMarkup); // Установка шаблона.

        try {
            execute(message);
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }

}
