-- change field type for id
ALTER TABLE tg_user DROP PRIMARY KEY;
ALTER TABLE tg_user MODIFY chat_id BIGINT NOT NULL;
ALTER TABLE tg_user ADD PRIMARY KEY (chat_id);