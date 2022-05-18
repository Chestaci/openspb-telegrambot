package com.github.Chestaci.openspbtb.openspbclient;

import com.github.Chestaci.openspbtb.openspbclient.dto.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenSPbNewsClientImpl implements OpenSPbNewsClient{

    private final String url;

    public OpenSPbNewsClientImpl(@Value("${openspb.url}") String openSpbNewsUrl) {
        this.url = openSpbNewsUrl;
    }

    @Override
    public List<News> findNewNews(Long lastNewsId) {

        List<News> listNews = findNews();

        List<News> newNews = new ArrayList<>();

        for (News news : listNews) {
            if (lastNewsId.equals(news.getNewsId())) {
                return newNews;
            }
            newNews.add(news);
        }
        return newNews;
    }

    public List<News> findNews() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url + "/o-nas/press-tsentr/novosti")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            //TODO add exception handling
            e.printStackTrace();
        }

        List<News> listNews = new ArrayList<>();
        if(doc != null) {
            Elements newsElements = doc.getElementsByClass("news-list__item");
            for (Element e : newsElements) {
                News news = new News();
                news.setNewsId(Long.parseLong(
                                e.attr("id")
                                        .replace("bx", "")
                                        .replace("_", "")
                        )
                );
                news.setTitle(e.getElementsByClass("title").first().text());
                news.setRubric(e.getElementsByClass("rubric").first().text());
                news.setDate(e.getElementsByClass("date").first().text());
                news.setLink(url + e.getElementsByClass("info").first().child(2).attr("href"));
                listNews.add(news);
            }
        }
        return listNews;
    }

    @Override
    public Long findLastNewsId() {
        List<News> news = null;
        news = findNews();
        if(news != null){
            return news.get(0).getNewsId();
        }else {
            return 0L;
        }
    }
}
