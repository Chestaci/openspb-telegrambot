package com.github.Chestaci.openspbtb.job;

import com.github.Chestaci.openspbtb.service.FindNewNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Job for finding new news.
 */
@Slf4j
@Component
public class FindNewNewsJob {
    private final FindNewNewsService findNewNewsService;

    @Autowired
    public FindNewNewsJob(FindNewNewsService findNewNewsService) {
        this.findNewNewsService = findNewNewsService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewNewsFixedRate}")
    public void findNewNews() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Find new news job started.");

        findNewNewsService.findNewNews();

        LocalDateTime end = LocalDateTime.now();

        log.info("Find new news job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
