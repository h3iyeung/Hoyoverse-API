package com.zvyap.hoyoapi.test.feature;

import com.h3iyeung.hoyoapi.APIEnvironment;
import com.h3iyeung.hoyoapi.GameType;
import com.h3iyeung.hoyoapi.HoyoToken;
import com.h3iyeung.hoyoapi.HoyoverseAPI;
import com.h3iyeung.hoyoapi.feature.daily.DailyCheckInFeature;
import com.zvyap.hoyoapi.test.TestConstant;
import com.zvyap.hoyoapi.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIf;

import java.util.Locale;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIf("com.zvyap.hoyoapi.test.TestConstant#isStarRailEnabled")
public class HonkaiStarRailDailyCheckInTest {

    private HoyoverseAPI osAPI = new HoyoverseAPI(APIEnvironment.OVERSEA);
    private HoyoToken token = HoyoToken.of(TestConstant.HSR_TOKEN_ID, TestConstant.HSR_TOKEN);

    @Test
    public void osHSRDailyCheckInGetRewardTest() {
        var response = new DailyCheckInFeature(osAPI).getAllReward(GameType.HONKAI_STAR_RAIL);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    public void osHSRDailyCheckInGetDailyInfoTest() {
        var response = new DailyCheckInFeature(osAPI).getDailyInfo(GameType.HONKAI_STAR_RAIL, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    @Disabled
    public void osHSRDailyCheckInSignDailyTest() {
        var response = new DailyCheckInFeature(osAPI).signDaily(GameType.HONKAI_STAR_RAIL, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }
}
