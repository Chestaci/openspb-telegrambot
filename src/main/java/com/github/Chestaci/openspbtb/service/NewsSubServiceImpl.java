package com.github.Chestaci.openspbtb.service;

import com.github.Chestaci.openspbtb.openspbclient.OpenSPbNewsClient;
import com.github.Chestaci.openspbtb.openspbclient.dto.News;
import com.github.Chestaci.openspbtb.repository.NewsSubRepository;
import com.github.Chestaci.openspbtb.repository.entity.NewsSub;
import com.github.Chestaci.openspbtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class NewsSubServiceImpl implements NewsSubService{

    private final NewsSubRepository newsSubRepository;
    private final TelegramUserService telegramUserService;
    private final OpenSPbNewsClient openSPbNewsClient;

    @Autowired
    public NewsSubServiceImpl(NewsSubRepository newsSubRepository, TelegramUserService telegramUserService, OpenSPbNewsClient openSPbNewsClient) {
        this.newsSubRepository = newsSubRepository;
        this.telegramUserService = telegramUserService;
        this.openSPbNewsClient = openSPbNewsClient;
    }

    @Override
    public NewsSub save(Long chatId, News news) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        NewsSub newsSub;

        Optional<NewsSub> newsSubFromDB = newsSubRepository.findById(news.getNewsId());
        if(newsSubFromDB.isPresent()) {
            newsSub = newsSubFromDB.get();
            Optional<TelegramUser> first = newsSub.getUsers().stream()
                    .filter(it -> it.getChatId().equals(chatId))
                    .findFirst();
            if(first.isEmpty()) {
                newsSub.addUser(telegramUser);
            }
        } else {
            newsSub = new NewsSub();
            newsSub.addUser(telegramUser);
            newsSub.setLastNewsId(openSPbNewsClient.findLastNewsId());
            newsSub.setId(news.getNewsId());
            newsSub.setTitle(news.getTitle());
        }
        return newsSubRepository.save(newsSub);
    }

    @Override
    public NewsSub save(NewsSub newsSub) {
        return newsSubRepository.save(newsSub);
    }

    @Override
    public Optional<NewsSub> findById(Long id) {
        return newsSubRepository.findById(id);
    }

    @Override
    public List<NewsSub> findAll() {
        return newsSubRepository.findAll();
    }
}
