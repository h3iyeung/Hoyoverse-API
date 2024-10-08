package com.h3iyeung.hoyoapi;

public class HoyoToken {
    protected final String ltoken;
    protected final String ltuid;

    public HoyoToken(String ltuid, String ltoken) {
        this.ltoken = ltoken;
        this.ltuid = ltuid;
    }

    public static HoyoToken of(String ltuid, String ltoken) {
        if(ltoken.startsWith("v2_")) {
            return new HoyoTokenV2(ltuid, ltoken);
        }
        return new HoyoToken(ltuid, ltoken);
    }

    public String getLtoken() {
        return ltoken;
    }

    public String getLtuid() {
        return ltuid;
    }

    String toCookieString() {
        return new StringBuilder("ltoken=").append(ltoken).append("; ltuid=").append(ltuid).toString();
//        return new StringBuilder("cookie_token=").append(ltoken).append("; account_id=").append(ltuid).toString();
    }
}
