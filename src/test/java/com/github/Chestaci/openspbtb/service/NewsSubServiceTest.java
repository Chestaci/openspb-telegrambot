package com.github.Chestaci.openspbtb.service;

import com.github.Chestaci.openspbtb.openspbclient.OpenSPbNewsClient;
import com.github.Chestaci.openspbtb.openspbclient.dto.News;
import com.github.Chestaci.openspbtb.repository.NewsSubRepository;
import com.github.Chestaci.openspbtb.repository.entity.NewsSub;
import com.github.Chestaci.openspbtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for NewsSubService")
public class NewsSubServiceTest {

    private NewsSubService newsSubService;
    private NewsSubRepository newsSubRepository;
    private TelegramUser newUser;

    private final static Long CHAT_ID = 1L;

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        newsSubRepository = Mockito.mock(NewsSubRepository.class);
        OpenSPbNewsClient openSPbNewsClient = Mockito.mock(OpenSPbNewsClient.class);
        newsSubService = new NewsSubServiceImpl(newsSubRepository, telegramUserService, openSPbNewsClient);

        newUser = new TelegramUser();
        newUser.setActive(true);
        newUser.setChatId(CHAT_ID);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveNews() {
        //given

        News news = new News();
        news.setNewsId(1L);
        news.setTitle("n1");

        NewsSub expectedNewsSub = new NewsSub();
        expectedNewsSub.setId(news.getNewsId());
        expectedNewsSub.setTitle(news.getTitle());
        expectedNewsSub.addUser(newUser);
        expectedNewsSub.setLastNewsId(0L);

        //when
        newsSubService.save(CHAT_ID, news);

        //then
        Mockito.verify(newsSubRepository).save(expectedNewsSub);
    }

    @Test
    public void shouldProperlyAddUserToNewsSub() {
        //given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId(2L);
        oldTelegramUser.setActive(true);

        News news = new News();
        news.setNewsId(1L);
        news.setTitle("n1");

        NewsSub newsFromDB = new NewsSub();
        newsFromDB.setId(news.getNewsId());
        newsFromDB.setTitle(news.getTitle());
        newsFromDB.addUser(oldTelegramUser);

        Mockito.when(newsSubRepository.findById(news.getNewsId())).thenReturn(Optional.of(newsFromDB));

        NewsSub expectedNewsSub = new NewsSub();
        expectedNewsSub.setId(news.getNewsId());
        expectedNewsSub.setTitle(news.getTitle());
        expectedNewsSub.addUser(oldTelegramUser);
        expectedNewsSub.addUser(newUser);

        //when
        newsSubService.save(CHAT_ID, news);

        //then
        Mockito.verify(newsSubRepository).findById(news.getNewsId());
        Mockito.verify(newsSubRepository).save(expectedNewsSub);
    }

}
