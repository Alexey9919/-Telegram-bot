package com.example.SpringDemoBot.service;

import com.example.SpringDemoBot.config.BotConfig;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {


    //Финальные переменные
    final BotConfig config;
    static final String HELP_TEXT = "Вы нажали кнопку помощи, в данный момент я не могу решить вашу проблему\n\nОбратитесь позже";
    static final String DELETE_DATA = "Ваша анкета удалена!";

    //Телеграм бот, который реализует 3 метода. Меню
    public TelegramBot(BotConfig config){
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Запуск бота TWIN"));
        listofCommands.add(new BotCommand("/deletedata", "Удалить мою анкету"));
        listofCommands.add(new BotCommand("/help", "Помощь"));
        try{
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(),null));
        }
        catch(TelegramApiException e){
            log.error("Loger ne rabotaet");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    //Мелод "при получении обновления"
    @Override
    public void onUpdateReceived(Update update) {

        //При получении сообщения, выяcнить ChatId
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            //Проверить, что находится в сообщении и сверить с кейсами
            switch (messageText) {

                case "/start":
                    String boris = String.valueOf(chatId);
                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\gotviyLogo.jpg", boris);
                    sendMessage(chatId, "Чтобы продолжить, нажмите кнопку <Моя анкета>");
                    break;

                case "/deletedata":
                    sendMessage(chatId, DELETE_DATA);
                    break;

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;




                ///////////////////////////////КНОПКА - МОЯ АНКЕТА//////////////////////////////

                case "Моя анкета":

                    switch (update.getMessage().getChatId().intValue()) {

                        case 318297600:
                            createText(chatId);
                            Nicholas(chatId);
                            break;


                        case 1678196856:
                            createText(chatId);
                            Maks(chatId);
                            break;


                        case 810251155:
                            createText(chatId);
                            Yana(chatId);
                            break;


                        case 1102166538:
                            createText(chatId);
                            Kristina(chatId);
                            break;


                        default:
                            String names = update.getMessage().getChat().getFirstName();
                            createText(chatId);
                            sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\Anon.jpg", String.valueOf(chatId));
                            sendMessage(chatId, names);
                    }
                    break;




                /////////////////////////////КНОПКА - TWIN/////////////////////////////////////

                case "TWIN":

                    switch (update.getMessage().getChatId().intValue()) {

                        case 318297600:
                            searchText(chatId);
                            Yana(chatId);
                            break;


                        case 1678196856:
                            searchText(chatId);
                            Kristina(chatId);
                            break;


                        case 810251155:
                            searchText(chatId);
                            Nicholas(chatId);
                            break;


                        case 1102166538:
                            searchText(chatId);
                            Maks(chatId);
                            break;


                        default:
                            searchText(chatId);
                            Sergey(chatId);
                    }
                    break;
            }
        }
    }


    //Анкеты участников
    private void Nicholas(long chatId) {
        sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\Kol.jpg", String.valueOf(chatId));
        sendMessage(chatId, "Николай, 20 лет, Зеленоград \nОбразование: МИЭТ \n" +
                "Интересы: Горные лыжи, футбол\n" +
                "Ссылка тг: https://t.me/my_pepel\n");
    }

    private void Maks(long chatId) {
        sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\Maks.jpg", String.valueOf(chatId));
        sendMessage(chatId, "Максим, 20 лет, Зеленоград \nОбразование: МИЭТ \n" +
                "Интересы: Теория автоматического управления, плавание, футбол\n" +
                "Номер телефона: +79537165110\n");
    }

    private void Kristina(long chatId) {
        sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\Kristina.jpg", String.valueOf(chatId));
        sendMessage(chatId, "Кристина, 18 лет, Тверь \nОбразование: ТГМУ \n" +
                "Интересы: Гулять с Мускатом, книги, фильмы\n" +
                "Ссылка тг: https://t.me/osyanya");

    }

    private void Yana(long chatId) {
        sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\Yana.jpg", String.valueOf(chatId));
        sendMessage(chatId, "Яна, 18 лет, Тверь \nОбразование: ТГМУ \n" +
                "Интересы: Фигурное катание, книги, рисование, бег(особенно, когда опазывает на пары)\n" +
                "Ссылка тг: https://t.me/yanamarsel\n");
    }

    private void Sergey(long chatId) {
        sendImageUploadingAFile("C:\\JDK\\SpringNew\\SpringDemoBot\\src\\main\\resources\\static\\Lazarev.jpg", String.valueOf(chatId));
        sendMessage(chatId, "Сергей Лазарев, 39 лет, Москва \nЛауреат премии <Золотой граммофон> \n" +
                "Интересы: Творчество, спорт, фильмы\n" +
                "Номер телефона: +79537784610\n");
    }



    //Действия, которые происходят при поиске пары
    private void searchText(long chatId) {
        sendMessage(chatId, EmojiParser.parseToUnicode("Подбираю вам пару..." + ":two_hearts:"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendMessage(chatId, "Еще секунду...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendMessage(chatId, EmojiParser.parseToUnicode("Готово! Я нашел вам идеальную пару!" + ":heart:"));
    }


    //Дейстия, которые происходят при создании анкеты
    private void createText(long chatId) {
        sendMessage(chatId, EmojiParser.parseToUnicode("Создаем вашу анкету..." + ":open_book:"));

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    //Стартующий метод, который вызывается при команде "start"
    private void startCommandReceived(long chatId, String name) throws TelegramApiException, FileNotFoundException, InterruptedException {

        String answer = EmojiParser.parseToUnicode(name + ", рады видеть тебя!\nТвин – телеграм бот, работающий на основе нейросети «Маяк». Данная сеть\n" +
                "анализирует информацию с вашего телефона и подбирает вам идеальную пару.  Все очень просто, бот сам создаст вашу анкету, останется лишь нажать TWIN.\n" +
                ":love_letter:");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);

    }



    //Метод по отправке фото
    public void sendImageUploadingAFile(String filePath, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a constructor overload)
        sendPhotoRequest.setPhoto(new InputFile(new File(filePath)));
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    //Метод по отправке сообщений
    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(EmojiParser.parseToUnicode("Моя анкета"));
        row.add(EmojiParser.parseToUnicode("TWIN"));

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(Collections.singletonList(row));

        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }


    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("ERROR_TEXT" + e.getMessage());
        }
    }
}