ALTER TABLE
    "item_library" ADD CONSTRAINT "item_library_game_unique" UNIQUE("game_id", "library_id");

ALTER TABLE
    "cover_media" ADD CONSTRAINT "cover_media_storage_key_unique" UNIQUE("storage_key");
