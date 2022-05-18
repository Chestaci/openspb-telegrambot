package com.github.Chestaci.openspbtb.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Telegram User entity.
 */
@Getter
@Setter
@Entity
@Table(name = "tg_user")
@EqualsAndHashCode(exclude = "newsSub")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id")
    private NewsSub newsSub;

}
