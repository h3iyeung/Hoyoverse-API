package com.h3iyeung.hoyoapi.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.h3iyeung.hoyoapi.APIEnvironment;
import com.h3iyeung.hoyoapi.GameType;
import com.h3iyeung.hoyoapi.HoyoverseAPI;
import com.h3iyeung.hoyoapi.response.MYSNavigatorsResponse;

public class DataAPITest {
    private HoyoverseAPI api = new HoyoverseAPI(APIEnvironment.OVERSEA);

    @Test
    public void getGameDetailsTest() {
        Assertions.assertEquals(api.getGameDetails(GameType.GENSHIN_IMPACT).getName(), "Genshin Impact");
        TestUtils.notNullAndPrint(api.getGameDetails());
    }

    @Test
    public void getNavigatorsTest() {
        TestUtils.notNullAndPrint(api.getNavigators(GameType.GENSHIN_IMPACT).getNavigator(MYSNavigatorsResponse.Type.WEB_GTOOL_PC).get(0));
        TestUtils.notNullAndPrint(api.getNavigators(GameType.HONKAI_IMPACT_3RD));
    }
}
