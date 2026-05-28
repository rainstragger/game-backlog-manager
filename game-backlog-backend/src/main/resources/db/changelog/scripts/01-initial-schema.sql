CREATE TABLE "Games"(
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
    "Games" ADD PRIMARY KEY("id");
CREATE TABLE "Library"(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL
);
ALTER TABLE
    "Library" ADD PRIMARY KEY("id");
CREATE TABLE "ItemLibrary"(
    "id" SERIAL NOT NULL,
    "game_id" INTEGER NOT NULL,
    "library_id" INTEGER NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    "completed_at" DATE NULL,
    "started_at" DATE NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL
);
ALTER TABLE
    "ItemLibrary" ADD PRIMARY KEY("id");
CREATE TABLE "CoverMedia"(
    "id" SERIAL NOT NULL,
    "storage_key" TEXT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    "checksum" VARCHAR(255) NOT NULL,
    "content_type" VARCHAR(255) NOT NULL,
    "size_bytes" BIGINT NOT NULL
);
ALTER TABLE
    "CoverMedia" ADD PRIMARY KEY("id");
ALTER TABLE
    "ItemLibrary" ADD CONSTRAINT "itemlibrary_gameid_foreign" FOREIGN KEY("game_id") REFERENCES "Games"("id");
ALTER TABLE
    "Games" ADD CONSTRAINT "games_coverid_foreign" FOREIGN KEY("cover_id") REFERENCES "CoverMedia"("id");
ALTER TABLE
    "ItemLibrary" ADD CONSTRAINT "itemlibrary_library_id_foreign" FOREIGN KEY("library_id") REFERENCES "Library"("id");
