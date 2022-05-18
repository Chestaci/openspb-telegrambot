-- change foreign key
ALTER TABLE tg_user DROP FOREIGN KEY tg_user_ibfk_1;
ALTER TABLE tg_user ADD FOREIGN KEY (news_id) REFERENCES news_sub(id) ON DELETE CASCADE;