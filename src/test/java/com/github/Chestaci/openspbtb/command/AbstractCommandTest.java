package com.github.Chestaci.openspbtb.command;

import com.github.Chestaci.openspbtb.bot.OpenSPbTelegramBot;
import com.github.Chestaci.openspbtb.repository.entity.TelegramUser;
import com.github.Chestaci.openspbtb.service.NewsSubService;
import com.github.Chestaci.openspbtb.service.TelegramUserService;
import com.github.Chestaci.openspbtb.service.SendBotMessageService;
import com.github.Chestaci.openspbtb.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

/**
 * Abstract class for testing {@link Command}s.
 */
abstract class AbstractCommandTest {

    protected OpenSPbTelegramBot openSPbBot = Mockito.mock(OpenSPbTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(openSPbBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    protected NewsSubService newsSubService = Mockito.mock(NewsSubService.class);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        TelegramUser telegramUser = new TelegramUser();
        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(Optional.of(telegramUser));
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(openSPbBot).execute(sendMessage);
    }
}
