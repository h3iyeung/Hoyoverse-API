package com.h3iyeung.hoyoapi.util;

import org.apache.commons.lang3.RandomStringUtils;

import com.h3iyeung.hoyoapi.APIEnvironment;
import com.h3iyeung.hoyoapi.GameType;
import com.h3iyeung.hoyoapi.HoyoToken;
import com.h3iyeung.hoyoapi.HoyoverseAPI;
import com.h3iyeung.hoyoapi.http.ContentType;
import com.h3iyeung.hoyoapi.http.HttpMethod;
import com.h3iyeung.hoyoapi.http.JsonBodyHandler;
import com.h3iyeung.hoyoapi.response.HoyoGetForumFullUserResponse;
import com.h3iyeung.hoyoapi.response.HoyoGetUserGameRolesResponse;

import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class Utils {
    public static final String UNKNOWN = "UNKNOWN";
    public static final Random RANDOM = new Random();
    public static final String MYS_SALT = "DG8lqMyc9gquwAUFc7zBS62ijQRX9XF7";
    public static final String MYS_VERSION = "2.49.1";

    public static HttpResponse<Supplier<HoyoGetUserGameRolesResponse>> getUserGameRoles(HoyoverseAPI api, HoyoToken token, GameType type, Map<String, String> additionalParams) { //params: lang, game_biz, sLangKey, region
        if (additionalParams == null) {
            additionalParams = new HashMap<>();
        }
        if (type != null) {
            additionalParams.put("game_biz", api.getEnvironment().getAPIConstant(type).getGameBiz());
        }
        additionalParams.put("lang", api.getLang());
        additionalParams.put("sLangKey", api.getLang()); //IDK what this doing
        return api.makeRequest(token, api.buildRequest(HttpUtils.createURI(api.getEnvironment().getAccountAPIConstant().getUserGameRoleEndpoint(), null, additionalParams),
                HttpMethod.GET, ContentType.HOYO_API, null), JsonBodyHandler.of(HoyoGetUserGameRolesResponse.class));
    }

    public static HttpResponse<Supplier<HoyoGetForumFullUserResponse>> getForumUser(HoyoverseAPI api, String accountId) {
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("uid", accountId);
        additionalParams.put("gids", "2");

        String[] headers = null;
        if(api.getEnvironment() == APIEnvironment.CHINA) {
            headers = new String[] {"Referer","https://www.miyoushe.com/"};
        }

        return api.makeRequest(null,
                api.buildRequest(HttpUtils.createURI(api.getEnvironment().getAccountAPIConstant().getForumFullUserInfoEndpoint(), null, additionalParams),
                        HttpMethod.GET, ContentType.HOYO_API, headers),
                JsonBodyHandler.of(HoyoGetForumFullUserResponse.class));
    }

    public static <T> List<T> ifNullGetEmptyList(List<T> list) {
        if(list == null) {
            return new ArrayList<>(); //Not using Collections.emptyList() so user can edit the list;
        }
        return list;
    }

    public static <O, T> List<T> ifNullGetEmptyList(O preCheck, Function<O, List<T>> listSupplier) {
        if (preCheck == null || listSupplier.apply(preCheck) == null) {
            return new ArrayList<>(); //Not using Collections.emptyList() so user can edit the list;
        }
        return listSupplier.apply(preCheck);
    }

    public static String getDS() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String t = Integer.toString((int) ((ts.getTime()) / 1000));
        String r = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
        String c = bytesToHex(digest(("salt=" + MYS_SALT + "&t=" + t + "&r=" + r).getBytes()));
        return t + "," + r + "," + c;
    }

    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
