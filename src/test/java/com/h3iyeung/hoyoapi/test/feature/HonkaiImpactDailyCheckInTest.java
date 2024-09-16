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
@EnabledIf("com.zvyap.hoyoapi.test.TestConstant#isHonkaiEnabled")
public class HonkaiImpactDailyCheckInTest {
    private HoyoverseAPI osAPI = new HoyoverseAPI(APIEnvironment.OVERSEA);
    private HoyoToken token = HoyoToken.of(TestConstant.HONKAI_TOKEN_ID, TestConstant.HONKAI_TOKEN); //04

    @Test
    public void osHonkaiImpactDailyCheckInGetRewardTest() {
        var response = new DailyCheckInFeature(osAPI).getAllReward(GameType.HONKAI_IMPACT_3RD);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    public void osHonkaiImpactDailyCheckInGetDailyInfoTest() {
        var response = new DailyCheckInFeature(osAPI).getDailyInfo(GameType.HONKAI_IMPACT_3RD, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    @Disabled
    public void osHonkaiImpactDailyCheckInSignDailyTest() {
        var response = new DailyCheckInFeature(osAPI).signDaily(GameType.HONKAI_IMPACT_3RD, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }
}
