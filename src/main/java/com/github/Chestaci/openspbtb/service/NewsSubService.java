package com.github.Chestaci.openspbtb.service;

import com.github.Chestaci.openspbtb.openspbclient.dto.News;
import com.github.Chestaci.openspbtb.repository.entity.NewsSub;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link NewsSub}.
 */
public interface NewsSubService {
    NewsSub save(Long chatId, News news);

    NewsSub save(NewsSub newsSub);

    Optional<NewsSub> findById(Long id);

    List<NewsSub> findAll();
}
