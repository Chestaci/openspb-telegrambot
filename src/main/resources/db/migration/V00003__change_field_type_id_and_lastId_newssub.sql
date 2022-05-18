-- change field type for id and lastID
ALTER TABLE tg_user DROP FOREIGN KEY tg_user_ibfk_1;
ALTER TABLE news_sub DROP PRIMARY KEY;
ALTER TABLE news_sub MODIFY id BIGINT NOT NULL;
ALTER TABLE news_sub MODIFY last_news_id BIGINT;
ALTER TABLE tg_user MODIFY news_id BIGINT;
ALTER TABLE news_sub ADD PRIMARY KEY (id);
ALTER TABLE tg_user ADD FOREIGN KEY (news_id) REFERENCES news_sub(id);