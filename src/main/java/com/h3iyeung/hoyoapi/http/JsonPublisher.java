package com.h3iyeung.hoyoapi.http;


import org.jetbrains.annotations.NotNull;

import com.h3iyeung.hoyoapi.util.JsonUtils;

import java.net.http.HttpRequest;
import java.nio.charset.Charset;

public class JsonPublisher {
    public static HttpRequest.BodyPublisher of(@NotNull Object obj) {
        return of(obj, Charset.defaultCharset());
    }

    public static HttpRequest.BodyPublisher of(@NotNull Object obj, @NotNull Charset charset) {
        return HttpRequest.BodyPublishers.ofString(JsonUtils.toJsonSafe(obj), charset);
    }
}
