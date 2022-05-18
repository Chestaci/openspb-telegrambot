package com.github.Chestaci.openspbtb.openspbclient;

import com.github.Chestaci.openspbtb.openspbclient.dto.News;

import java.io.IOException;
import java.util.List;

/**
 * Client for News.
 */
public interface OpenSPbNewsClient{

    /**
     * Find new news since lastNewsId.
     *
     * @param lastNewsId provided last news ID.
     * @return the collection of the new {@link News}.
     */
    List<News> findNewNews(Long lastNewsId) throws IOException;

    Long findLastNewsId();
}