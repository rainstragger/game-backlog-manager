ALTER TABLE progress
    ADD CONSTRAINT progress_status_check
    CHECK (status IN ('BACKLOGGED', 'PLAYING', 'COMPLETED'));