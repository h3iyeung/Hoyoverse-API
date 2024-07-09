package com.zvyap.hoyoapi;

import com.zvyap.hoyoapi.annotation.CaseSensitive;
import com.zvyap.hoyoapi.util.Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

//Unimplemented yet
//https://api-account-os.hoyolab.com/account/binding/api/getAllRegions?game_biz=bh3_global
//https://api-takumi.mihoyo.com/account/binding/api/getAllRegions?game_biz=hkrpg_cn
public enum ServerRegion {
    ASIA(Map.of(
            GameType.GENSHIN_IMPACT, "os_asia",
            GameType.HONKAI_IMPACT_3RD, "overseas01",
            GameType.HONKAI_STAR_RAIL, "prod_official_asia",
            GameType.TEARS_OF_THEMIS, "glb_prod_wd01",
            GameType.ZENLESS_ZONE_ZERO, "prod_gf_jp"
    )),
    EUROPE(Map.of(
            GameType.GENSHIN_IMPACT, "os_eur",
            GameType.HONKAI_IMPACT_3RD, "eur01",
            GameType.HONKAI_STAR_RAIL, "prod_official_eur",
            GameType.TEARS_OF_THEMIS, "glb_prod_wd01",
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    )),
    AMERICA(Map.of(
            GameType.GENSHIN_IMPACT, "os_usa",
            GameType.HONKAI_IMPACT_3RD, "usa01",
            GameType.HONKAI_STAR_RAIL, "prod_official_usa",
            GameType.TEARS_OF_THEMIS, "glb_prod_wd01",
            GameType.ZENLESS_ZONE_ZERO, "prod_gf_us"
    )),
    TW_HK_MO(Map.of(
            GameType.GENSHIN_IMPACT, "os_cht",
            GameType.HONKAI_IMPACT_3RD, "asia01",
            GameType.HONKAI_STAR_RAIL, "prod_official_cht",
            GameType.TEARS_OF_THEMIS, "glb_prod_wd01",
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    )),
    KOREA(Map.of(
            GameType.GENSHIN_IMPACT, Utils.UNKNOWN,
            GameType.HONKAI_IMPACT_3RD, "kr01",
            GameType.HONKAI_STAR_RAIL, Utils.UNKNOWN,
            GameType.TEARS_OF_THEMIS, Utils.UNKNOWN,
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    )),
    JAPAN(Map.of(
            GameType.GENSHIN_IMPACT, Utils.UNKNOWN,
            GameType.HONKAI_IMPACT_3RD, "jp01",
            GameType.HONKAI_STAR_RAIL, Utils.UNKNOWN,
            GameType.TEARS_OF_THEMIS, Utils.UNKNOWN,
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    )),
    CHINA_OFFICIAL(Map.of(
            GameType.GENSHIN_IMPACT, "cn_gf01", //SKY_ISLAND
            GameType.HONKAI_IMPACT_3RD, "ios01,android01,pc01",
            GameType.HONKAI_STAR_RAIL, "prod_gf_cn", //pre_090_cn,pre_100_cn - what is these server
            GameType.TEARS_OF_THEMIS, "cn_prod_gf01",
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    )),
    CHINA_MIX(Map.of(
            GameType.GENSHIN_IMPACT, "cn_qd01", //SKY_TREE
            GameType.HONKAI_IMPACT_3RD, "hun01,hun02,yyb01",
            GameType.HONKAI_STAR_RAIL, "prod_qd_cn",
            GameType.TEARS_OF_THEMIS, "cn_prod_mix01",
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    )),
    BILIBILI(Map.of(
            GameType.GENSHIN_IMPACT, "null",
            GameType.HONKAI_IMPACT_3RD, "bb01",
            GameType.HONKAI_STAR_RAIL, Utils.UNKNOWN,
            GameType.TEARS_OF_THEMIS, "cn_prod_bb01",
            GameType.ZENLESS_ZONE_ZERO, Utils.UNKNOWN
    ));

    private Map<GameType, String> region;

    ServerRegion(Map<GameType, String> region) {
        this.region = region;
    }

    @NotNull
    public Map<GameType, String> getRegionString() {
        return region;
    }

    @NotNull
    public String getRegion(GameType gameType) {
        return getRegionString().get(gameType);
    }

    public boolean match(String region) {
        for(String r : getRegionString().values()) {
            if(match(r, region)) {
                return true;
            }
        }
        return false;
    }

    private static boolean match(String currentRegion, String region) {
        if(currentRegion == null) {
            return false;
        }
        if(currentRegion.contains(",")) {
            String[] split = currentRegion.split(",");
            return ArrayUtils.contains(split, region);
        }
        if(currentRegion.equals(region)) {
            return true;
        }
        return false;
    }

    //Although the id will not repeat, it is better to use the gameType to validate and get the region
    @Nullable
    public static ServerRegion getRegionFromId(GameType gameType, @CaseSensitive String id) {
        for(ServerRegion serverRegion : values()) {
            if(serverRegion.getRegion(gameType).equals(id)) {
                return serverRegion;
            }
        }
        return null;
    }

    public boolean match(GameType gameType, String region) {
        return match(getRegion(gameType), region);
    }
}
