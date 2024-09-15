package com.zvyap.hoyoapi.feature.code;

import com.zvyap.hoyoapi.APIEnvironment;
import com.zvyap.hoyoapi.GameAPIConstant;
import com.zvyap.hoyoapi.GameType;
import com.zvyap.hoyoapi.HoyoToken;
import com.zvyap.hoyoapi.HoyoverseAPI;
import com.zvyap.hoyoapi.exception.AlreadyCheckInException;
import com.zvyap.hoyoapi.exception.GameProfileNotFoundException;
import com.zvyap.hoyoapi.exception.HoyoverseHttpRequestException;
import com.zvyap.hoyoapi.feature.HoyoFeature;
import com.zvyap.hoyoapi.http.ContentType;
import com.zvyap.hoyoapi.http.HttpMethod;
import com.zvyap.hoyoapi.http.JsonBodyHandler;
import com.zvyap.hoyoapi.http.JsonPublisher;
import com.zvyap.hoyoapi.response.HoyoAPIResponse;
import com.zvyap.hoyoapi.response.HoyoDailyCheckInInfoResponse;
import com.zvyap.hoyoapi.response.HoyoDailyCheckInRewardResponse;
import com.zvyap.hoyoapi.response.HoyoDailyCheckInSignResponse;
import com.zvyap.hoyoapi.util.HttpUtils;
import com.zvyap.hoyoapi.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.function.Supplier;

public class RedeemCodeFeature extends HoyoFeature {

    public RedeemCodeFeature(HoyoverseAPI api) {
        super(api);
    }

    private <T> T fetchCodeEndpoint(HttpMethod method, GameType type, Class<T> responseClass,
            String cdKey) {
        GameAPIConstant constant = api.getEnvironment().getAPIConstant(type);
        Map<String, String> data = Map.of("cdkey", cdKey, "uid", constant.getDailyCheckInActId());
        HttpResponse<Supplier<T>> response = api.makeRequest(null,
                api.buildRequest(
                        HttpUtils.createURI(constant.getCodeRedeemApiEndpoint(), "", data),
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
            return new String[] { "Accept-Language", "en-US,en;q=0.5",
                    "Origin", "https://act.hoyolab.com",
                    "Referer",
                    "https://act.hoyolab.com/ys/event/signin-sea-v3/index.html?act_id=e202102251931481&lang=en-us",
                    "Cache-Control", "max-age=0" };
        } else {
            return new String[] {
                    "Origin", "https://webstatic.mihoyo.com",
                    "X_Requested_With", "com.mihoyo.hyperion",
                    "Sec_Fetch_Site", "same-site",
                    "Sec_Fetch_Mode", "cors",
                    "Sec_Fetch_Dest", "empty",
                    "Accept_Encoding", "gzip,deflate",

                    "x-rpc-client_type", "5",
                    "Referer",
                    "https://webstatic.mihoyo.com/bbs/event/signin-ys/index.html?bbs_auth_required=true&act_id=e202009291139501&utm_source=bbs&utm_medium=mys&utm_campaign=icon",
                    "x-rpc-app_version", Utils.MYS_VERSION,
                    "DS", Utils.getDS()
            };
        }
    }

    /**
     * Get daily information of the account, contains day checked in, day missing
     * etc
     * Check out - {@link HoyoDailyCheckInInfoResponse}
     *
     * @param type  GameType
     * @param token HoyoToken
     * @return The information of daily status
     */
    public HoyoDailyCheckInInfoResponse hsrCodeRedeem(@NotNull GameType type, @NotNull HoyoToken token,
            @NotNull String cdKey) {
        return fetchCodeEndpoint(HttpMethod.POST, type, HoyoDailyCheckInInfoResponse.class, cdKey);
    }

}
