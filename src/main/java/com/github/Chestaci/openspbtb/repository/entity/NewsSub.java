package com.github.Chestaci.openspbtb.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * News entity.
 */
@Getter
@Setter
@Entity
@Table(name = "news_sub")
@EqualsAndHashCode(exclude = "users")
public class NewsSub {
    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "last_news_id")
    private Long lastNewsId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "newsSub")
    private List<TelegramUser> users;

    public void addUser(TelegramUser telegramUser) {
        if (isNull(users)) {
            users = new ArrayList<>();
        }
        users.add(telegramUser);
    }
}
