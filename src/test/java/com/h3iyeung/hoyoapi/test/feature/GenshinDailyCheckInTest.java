package com.h3iyeung.hoyoapi.test.feature;

import com.h3iyeung.hoyoapi.APIEnvironment;
import com.h3iyeung.hoyoapi.GameType;
import com.h3iyeung.hoyoapi.HoyoToken;
import com.h3iyeung.hoyoapi.HoyoverseAPI;
import com.h3iyeung.hoyoapi.feature.daily.DailyCheckInFeature;
import com.h3iyeung.hoyoapi.test.TestConstant;
import com.h3iyeung.hoyoapi.test.TestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIf;

import java.util.Locale;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIf("com.zvyap.hoyoapi.test.TestConstant#isGenshinEnabled")
public class GenshinDailyCheckInTest {

    private HoyoverseAPI osAPI = new HoyoverseAPI(APIEnvironment.OVERSEA);
    private HoyoToken token = HoyoToken.of(TestConstant.GENSHIN_TOKEN_ID, TestConstant.GENSHIN_TOKEN);

    @Test
    public void osGenshinDailyCheckInGetRewardTest() {
        var response = new DailyCheckInFeature(osAPI).getAllReward(GameType.GENSHIN_IMPACT);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    public void osGenshinDailyCheckInGetDailyInfoTest() {
        var response = new DailyCheckInFeature(osAPI).getDailyInfo(GameType.GENSHIN_IMPACT, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    @Disabled
    public void osGenshinDailyCheckInSignDailyTest() {
        var response = new DailyCheckInFeature(osAPI).signDaily(GameType.GENSHIN_IMPACT, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }
}
