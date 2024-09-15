package com.h3iyeung.hoyoapi;

public enum GameType {
    GENSHIN_IMPACT(2),
    HONKAI_IMPACT_3RD(1),
    HONKAI_STAR_RAIL(6),
    TEARS_OF_THEMIS(4),
    ZENLESS_ZONE_ZERO(8); //unimplemented yet

    private final int gameId;

    GameType(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
