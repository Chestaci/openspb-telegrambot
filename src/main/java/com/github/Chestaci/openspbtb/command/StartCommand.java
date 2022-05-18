package com.github.Chestaci.openspbtb.command;


import com.github.Chestaci.openspbtb.repository.entity.NewsSub;
import com.github.Chestaci.openspbtb.service.NewsSubService;
import com.github.Chestaci.openspbtb.service.TelegramUserService;
import com.github.Chestaci.openspbtb.repository.entity.TelegramUser;
import com.github.Chestaci.openspbtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final NewsSubService newsSubService;

    public final static String START_MESSAGE = "Привет. Я OpenSPb Telegram Bot. Я помогу тебе быть в курсе последних " +
            "новостей.";

    // Здесь не добавляем сервис через получение из Application Context.
    // Потому что если это сделать так, то будет циклическая зависимость, которая
    // ломает работу приложения.
    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, NewsSubService newsSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.newsSubService = newsSubService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        Long newsId = 1L;
        AtomicBoolean flag = new AtomicBoolean(false);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                    flag.set(true);
                });
        if (flag.get()) {
            TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
            //TODO add exception handling

            newsSubService.findById(newsId).ifPresentOrElse(
                    newsSub -> {
                        newsSub.addUser(telegramUser);
                        newsSubService.save(newsSub);
                        telegramUser.setNewsSub(newsSub);
                        telegramUserService.save(telegramUser);
                    },
                    () -> {
                        NewsSub newsSub = new NewsSub();
                        newsSub.setId(newsId);
                        newsSub.setLastNewsId(0L);
                        newsSub.setTitle("Новости и анонсы");
                        newsSub.addUser(telegramUser);
                        newsSubService.save(newsSub);
                        telegramUser.setNewsSub(newsSub);
                        telegramUserService.save(telegramUser);
                    }
            );
            flag.set(false);
        }

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
