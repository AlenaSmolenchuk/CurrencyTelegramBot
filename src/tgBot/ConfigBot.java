import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton; 
import org.telegram.telegrambots.meta.exceptions.TelegramApiException; 
 
import java.io.IOException;
import java.util.ArrayList; 
import java.util.List; 
  
public class ConfigBot<def> extends TelegramLongPollingBot {
    public static String TOKEN = "5156105238:AAG77UUnuTrjq6WrWwVf3UVrSnTZA-CzoNM";
    public static String NAME = "CURRENCY_BOT";

    @Override
    public String getBotUsername()  {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    try { 
                        Parser parser = new Parser();
                        parser.parseUrl();
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), parser));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
                message.setText(update.getCallbackQuery().getData());
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
  
    public static SendMessage sendInlineKeyBoardMessage(long chatId, Parser parser) throws IOException {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            keyboardButtonsRow1.add(new InlineKeyboardButton().builder()
                    .text(parser.Courses.get(i).getCode())
                    .callbackData(parser.Courses.get(i).getAmount()
                            + " " + parser.Courses.get(i).getCode() + " = "
                            + parser.getCourse(parser.Courses.get(i).getCode()) + " RUB").build());
        }
        for (int i = 7; i < 14; i++) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().builder()
                    .text(parser.Courses.get(i).getCode())
                    .callbackData(parser.Courses.get(i).getAmount()
                            + " " + parser.Courses.get(i).getCode() + " = "
                            + parser.getCourse(parser.Courses.get(i).getCode()) + " RUB").build());
        }
        for (int i = 14; i<21; i++) {
            keyboardButtonsRow3.add(new InlineKeyboardButton().builder()
                    .text(parser.Courses.get(i).getCode())
                    .callbackData(parser.Courses.get(i).getAmount()
                            + " " + parser.Courses.get(i).getCode() + " = "
                            + parser.getCourse(parser.Courses.get(i).getCode()) + " RUB").build());
        }
        for (int i = 21; i < 28; i++) {
            keyboardButtonsRow4.add(new InlineKeyboardButton().builder()
                    .text(parser.Courses.get(i).getCode())
                    .callbackData(parser.Courses.get(i).getAmount()
                            + " " + parser.Courses.get(i).getCode() + " = "
                            + parser.getCourse(parser.Courses.get(i).getCode()) + " RUB").build());
        }
        for (int i = 28; i < parser.Courses.size(); i++) {
            keyboardButtonsRow5.add(new InlineKeyboardButton().builder()
                    .text(parser.Courses.get(i).getCode())
                    .callbackData(parser.Courses.get(i).getAmount()
                            + " " + parser.Courses.get(i).getCode() + " = "
                            + parser.getCourse(parser.Courses.get(i).getCode()) + " RUB").build());
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Привет, я " + NAME + "." + " Курс какой валюты ты хочешь узнать?");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }
}

