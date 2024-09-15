package com.h3iyeung.hoyoapi.feature;

import com.h3iyeung.hoyoapi.HoyoverseAPI;

public class HoyoFeature {
    protected final HoyoverseAPI api;

    public HoyoFeature(HoyoverseAPI api) {
        this.api = api;
    }

    public HoyoverseAPI getAPI() {
        return api;
    }
}
