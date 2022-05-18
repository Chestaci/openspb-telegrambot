package com.github.Chestaci.openspbtb.repository;

import com.github.Chestaci.openspbtb.repository.entity.NewsSub;
import com.github.Chestaci.openspbtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Integration-level testing for {@link NewsSubRepository}.
 */
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class NewsSubRepositoryIT {

    @Autowired
    private NewsSubRepository newsSubRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/newsSubForFiveUsers.sql"})
    @Test
    public void shouldProperlyGetAllUsersForNewsSub() {
        //when
        Optional<NewsSub> newsSubFromDB = newsSubRepository.findById(2L);

        //then
        Assertions.assertTrue(newsSubFromDB.isPresent());
        Assertions.assertEquals(2L, newsSubFromDB.get().getId());
        List<TelegramUser> users = newsSubFromDB.get().getUsers();
        for(int i=0; i<users.size(); i++) {
            Assertions.assertEquals((i + 1) * 10L, users.get(i).getChatId());
            Assertions.assertTrue(users.get(i).isActive());
        }
    }
}