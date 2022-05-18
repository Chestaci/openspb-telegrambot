package com.github.Chestaci.openspbtb.openspbclient;

import com.github.Chestaci.openspbtb.openspbclient.dto.News;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

@DisplayName("Integration-level testing for OpenSPbNewsClient")
public class OpenSPbNewsClientTest {

    private static final String URL = "https://xn--c1acndtdamdoc1ib.xn--p1ai";


    private final OpenSPbNewsClient newsClient = new OpenSPbNewsClientImpl(URL);

    @Test
    public void shouldProperlyGetNewNews() {
        //when
        try {
            List<News> listNewNews = newsClient.findNewNews(3218110189193508L);
        //then
            Assertions.assertEquals(16, listNewNews.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
