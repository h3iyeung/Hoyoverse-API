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
@EnabledIf("com.zvyap.hoyoapi.test.TestConstant#isToTEnabled")
public class TearsOfThemisDailyCheckInTest {

    private HoyoverseAPI osAPI = new HoyoverseAPI(APIEnvironment.OVERSEA);
    private HoyoToken token = HoyoToken.of(TestConstant.TOT_TOKEN_ID, TestConstant.TOT_TOKEN); //ys

    @Test
    public void osTearsOfThemisDailyCheckInGetRewardTest() {
        var response = new DailyCheckInFeature(osAPI).getAllReward(GameType.TEARS_OF_THEMIS);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    public void osTearsOfThemisDailyCheckInGetDailyInfoTest() {
        var response = new DailyCheckInFeature(osAPI).getDailyInfo(GameType.TEARS_OF_THEMIS, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }

    @Test
    @Disabled
    public void osTearsOfThemisDailyCheckInSignDailyTest() {
        var response = new DailyCheckInFeature(osAPI).signDaily(GameType.TEARS_OF_THEMIS, token);
        Assertions.assertNotNull(response);

        TestUtils.notNullAndPrint(response);
    }
}
