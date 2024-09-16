package com.zvyap.hoyoapi.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.h3iyeung.hoyoapi.APIEnvironment;
import com.h3iyeung.hoyoapi.GameType;
import com.h3iyeung.hoyoapi.HoyoToken;
import com.h3iyeung.hoyoapi.HoyoverseAPI;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class APITest {
    private HoyoToken token = HoyoToken.of(TestConstant.GENSHIN_TOKEN_ID, TestConstant.GENSHIN_TOKEN);
    private HoyoverseAPI api = new HoyoverseAPI(APIEnvironment.OVERSEA);

    @Test
    public void getGameRolesTest() {
        TestUtils.notNullAndPrint(api.getGameRoles(token, GameType.GENSHIN_IMPACT));
        TestUtils.notNullAndPrint(api.getGameRoles(token, GameType.HONKAI_IMPACT_3RD));
        TestUtils.notNullAndPrint(api.getGameRoles(token, GameType.HONKAI_STAR_RAIL));
        TestUtils.notNullAndPrint(api.getGameRoles(token, GameType.TEARS_OF_THEMIS));
    }

    @Test
    public void getForumUserInfoTest() {
        TestUtils.notNullAndPrint(api.getForumUser(TestConstant.GENSHIN_TOKEN_ID));
    }

    @Test
    public void getAccountUserInfoTest() {
        TestUtils.notNullAndPrint(api.getAccountInfo(token));
    }
}
