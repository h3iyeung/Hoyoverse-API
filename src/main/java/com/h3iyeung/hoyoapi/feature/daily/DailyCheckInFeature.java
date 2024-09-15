package com.h3iyeung.hoyoapi.feature.daily;

import org.jetbrains.annotations.NotNull;

import com.h3iyeung.hoyoapi.APIEnvironment;
import com.h3iyeung.hoyoapi.GameAPIConstant;
import com.h3iyeung.hoyoapi.GameType;
import com.h3iyeung.hoyoapi.HoyoToken;
import com.h3iyeung.hoyoapi.HoyoverseAPI;
import com.h3iyeung.hoyoapi.exception.AlreadyCheckInException;
import com.h3iyeung.hoyoapi.exception.GameProfileNotFoundException;
import com.h3iyeung.hoyoapi.exception.HoyoverseHttpRequestException;
import com.h3iyeung.hoyoapi.feature.HoyoFeature;
import com.h3iyeung.hoyoapi.http.ContentType;
import com.h3iyeung.hoyoapi.http.HttpMethod;
import com.h3iyeung.hoyoapi.http.JsonBodyHandler;
import com.h3iyeung.hoyoapi.http.JsonPublisher;
import com.h3iyeung.hoyoapi.response.HoyoAPIResponse;
import com.h3iyeung.hoyoapi.response.HoyoDailyCheckInInfoResponse;
import com.h3iyeung.hoyoapi.response.HoyoDailyCheckInRewardResponse;
import com.h3iyeung.hoyoapi.response.HoyoDailyCheckInSignResponse;
import com.h3iyeung.hoyoapi.util.HttpUtils;
import com.h3iyeung.hoyoapi.util.Utils;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.function.Supplier;

public class DailyCheckInFeature extends HoyoFeature {

    public DailyCheckInFeature(HoyoverseAPI api) {
        super(api);
    }

    private <T> T fetchDailyEndpoint(HoyoToken token, HttpMethod method, GameType type, String endpoint, Class<T> responseClass) {
        GameAPIConstant constant = api.getEnvironment().getAPIConstant(type);
        Map<String, String> data = Map.of("act_id", constant.getDailyCheckInActId(), "lang", api.getLang());
        HttpResponse<Supplier<T>> response = api.makeRequest(token,
                api.buildRequest(
                        HttpUtils.createURI(constant.getDailyCheckInApiEndpoint(), endpoint, data),
                        method,
                        ContentType.HOYO_API,
                        method == HttpMethod.POST ? JsonPublisher.of(data) : null,
                        headers()),
                new JsonBodyHandler<>(responseClass));

        if (response.statusCode() != 200) {
            throw new HoyoverseHttpRequestException(response.statusCode());
        }

        T body = response.body().get();
        if (body instanceof HoyoAPIResponse) {
            HoyoAPIResponse r = (HoyoAPIResponse) body;
            switch (r.getRetcode()) {
                case -5003:
                    throw new AlreadyCheckInException(r.getRetcode(), r.getMessage());
                case -10002:
                    throw new GameProfileNotFoundException(r.getRetcode(), r.getMessage());
            }
        }

        return body;
    }

    private String[] headers() {
        if (api.getEnvironment() == APIEnvironment.OVERSEA) {
            return new String[]{"Accept-Language", "en-US,en;q=0.5",
                    "Origin", "https://act.hoyolab.com",
                    "Referer", "https://act.hoyolab.com/ys/event/signin-sea-v3/index.html?act_id=e202102251931481&lang=en-us",
                    "Cache-Control", "max-age=0"};
        } else {
            return new String[]{
                    "Origin", "https://webstatic.mihoyo.com",
                    "X_Requested_With", "com.mihoyo.hyperion",
                    "Sec_Fetch_Site", "same-site",
                    "Sec_Fetch_Mode", "cors",
                    "Sec_Fetch_Dest", "empty",
                    "Accept_Encoding", "gzip,deflate",

                    "x-rpc-client_type", "5",
                    "Referer", "https://webstatic.mihoyo.com/bbs/event/signin-ys/index.html?bbs_auth_required=true&act_id=e202009291139501&utm_source=bbs&utm_medium=mys&utm_campaign=icon",
                    "x-rpc-app_version", Utils.MYS_VERSION,
                    "DS", Utils.getDS()
            };
        }
    }

    /**
     * Get daily information of the account, contains day checked in, day missing etc
     * Check out - {@link HoyoDailyCheckInInfoResponse}
     *
     * @param type  GameType
     * @param token HoyoToken
     * @return The information of daily status
     */
    public HoyoDailyCheckInInfoResponse getDailyInfo(@NotNull GameType type, @NotNull HoyoToken token) {
        return fetchDailyEndpoint(token, HttpMethod.GET, type, "info", HoyoDailyCheckInInfoResponse.class);
    }

    /**
     * Check in daily
     *
     * @param type  GameType
     * @param token HoyoToken
     * @return The response of official API
     */
    public HoyoDailyCheckInSignResponse signDaily(@NotNull GameType type, @NotNull HoyoToken token) {
        return fetchDailyEndpoint(token, HttpMethod.POST, type, "sign", HoyoDailyCheckInSignResponse.class);
    }

    /**
     * Get all reward of each day
     *
     * @param type GameType
     * @return A response object contains all the reward
     */
    public HoyoDailyCheckInRewardResponse getAllReward(@NotNull GameType type) {
        return fetchDailyEndpoint(null, HttpMethod.GET, type, "home", HoyoDailyCheckInRewardResponse.class);
    }
}
