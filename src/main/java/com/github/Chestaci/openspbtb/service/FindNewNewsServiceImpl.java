package com.github.Chestaci.openspbtb.service;

import com.github.Chestaci.openspbtb.openspbclient.OpenSPbNewsClient;
import com.github.Chestaci.openspbtb.openspbclient.dto.News;
import com.github.Chestaci.openspbtb.repository.entity.NewsSub;
import com.github.Chestaci.openspbtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewNewsServiceImpl implements FindNewNewsService {

    private final NewsSubService newsSubService;
    private final OpenSPbNewsClient openSPbNewsClient;
    private final SendBotMessageService sendMessageService;

    @Autowired
    public FindNewNewsServiceImpl(NewsSubService newsSubService,
                                  OpenSPbNewsClient openSPbNewsClient,
                                  SendBotMessageService sendMessageService) {
        this.newsSubService = newsSubService;
        this.openSPbNewsClient = openSPbNewsClient;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewNews() {
        newsSubService.findAll().forEach(nSub -> {
            List<News> newNews = null;
            try {
                newNews = openSPbNewsClient.findNewNews(nSub.getLastNewsId());
                setNewLastNewsId(nSub, newNews);
                notifySubscribersAboutNewArticles(nSub, newNews);
            } catch (IOException e) {
                //TODO add exception handling
                e.printStackTrace();
            }
        });

    }

    private void notifySubscribersAboutNewArticles(NewsSub nSub, List<News> newNews) {
        Collections.reverse(newNews);
        List<String> messagesWithNewNews = newNews.stream()
                .map(news -> String.format("✨Вышла новая новость <b>%s</b>.✨\n\n" +
                                "<b>Ссылка:</b> %s\n",
                        news.getTitle(), news.getLink()))
                .collect(Collectors.toList());

        nSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendMessageService.sendMessage(it.getChatId(), messagesWithNewNews));
    }

    private void setNewLastNewsId(NewsSub nSub, List<News> newNews) {
        newNews.stream().mapToLong(News::getNewsId).max()
                .ifPresent(id -> {
                    nSub.setLastNewsId(id);
                    newsSubService.save(nSub);
                });
    }

}
