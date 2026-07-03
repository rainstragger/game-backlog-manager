CREATE TABLE progress (
    id SERIAL NOT NULL,
    item_library_id INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    started_at DATE NULL,
    completed_at DATE NULL,
    created_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL
);

ALTER TABLE
    progress ADD PRIMARY KEY (id);

ALTER TABLE
    progress ADD CONSTRAINT progress_item_library_unique UNIQUE (item_library_id);

ALTER TABLE
    progress ADD CONSTRAINT progress_item_library_id_foreign
    FOREIGN KEY (item_library_id) REFERENCES item_library(id) ON DELETE CASCADE;

INSERT INTO progress (
    item_library_id,
    status,
    started_at,
    completed_at,
    created_at
)
SELECT
    id,
    status,
    started_at,
    completed_at,
    created_at
FROM item_library;

ALTER TABLE item_library
    DROP COLUMN status;

ALTER TABLE item_library
    DROP COLUMN started_at;

ALTER TABLE item_library
    DROP COLUMN completed_at;
