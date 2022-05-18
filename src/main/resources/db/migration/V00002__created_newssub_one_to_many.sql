-- add PRIMARY KEY FOR tg_user
ALTER TABLE tg_user ADD PRIMARY KEY (chat_id);
-- ensure that the tables with these names are removed before creating a new one.
DROP TABLE IF EXISTS news_sub;

CREATE TABLE news_sub (
   id VARCHAR(100),
   title VARCHAR(100),
   last_news_id VARCHAR(100),
   CONSTRAINT PRIMARY KEY (id)
);
-- add FOREIGN KEY FOR tg_user REFERENCES news_sub
ALTER TABLE tg_user ADD COLUMN news_id VARCHAR(100);
ALTER TABLE tg_user ADD FOREIGN KEY (news_id) REFERENCES news_sub(id);
