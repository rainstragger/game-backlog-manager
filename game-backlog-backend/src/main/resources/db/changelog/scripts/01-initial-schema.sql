CREATE TABLE "games"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "resume" TEXT NULL,
    "launch_date" DATE NULL,
    "developer" VARCHAR(255) NULL,
    "publisher" VARCHAR(255) NULL,
    "genre" VARCHAR(255) NULL,
    "about_game" TEXT NULL,
    "min_required" TEXT NULL,
    "recom_required" TEXT NULL,
    "cover_id" INTEGER NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL
);
ALTER TABLE
    "games" ADD PRIMARY KEY("id");
CREATE TABLE "library"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL
);
ALTER TABLE
    "library" ADD PRIMARY KEY("id");
CREATE TABLE "item_library"(
    "id" SERIAL NOT NULL,
    "game_id" INTEGER NOT NULL,
    "library_id" INTEGER NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    "completed_at" DATE NULL,
    "started_at" DATE NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL
);
ALTER TABLE
    "item_library" ADD PRIMARY KEY("id");
CREATE TABLE "cover_media"(
    "id" SERIAL NOT NULL,
    "storage_key" TEXT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    "checksum" VARCHAR(255) NOT NULL,
    "content_type" VARCHAR(255) NOT NULL,
    "size_bytes" BIGINT NOT NULL
);
ALTER TABLE
    "cover_media" ADD PRIMARY KEY("id");

    
ALTER TABLE
    "item_library" ADD CONSTRAINT "item_library_game_id_foreign" FOREIGN KEY("game_id") REFERENCES "games"("id");
ALTER TABLE
    "games" ADD CONSTRAINT "games_coverid_foreign" FOREIGN KEY("cover_id") REFERENCES "cover_media"("id");
ALTER TABLE
    "item_library" ADD CONSTRAINT "item_library_library_id_foreign" FOREIGN KEY("library_id") REFERENCES "library"("id");

INSERT INTO "library"(
    "name",
    "description"
)
VALUES(
    'Library',
    'Basic Library for games'
)
