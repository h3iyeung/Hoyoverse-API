package com.h3iyeung.hoyoapi.http;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import com.h3iyeung.hoyoapi.util.CachedSupplier;
import com.h3iyeung.hoyoapi.util.JsonUtils;


public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<Supplier<T>> {
    @Getter
    private final Class<T> clazz;

    public JsonBodyHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> JsonBodyHandler<T> of(Class<T> clazz) {
        return new JsonBodyHandler<>(clazz);
    }

    @Override
    public HttpResponse.BodySubscriber<Supplier<T>> apply(HttpResponse.ResponseInfo responseInfo) {
        HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();
        return HttpResponse.BodySubscribers.mapping(
                upstream,
                inputStream -> toSupplierOfType(inputStream, clazz));
    }

    public static <W> Supplier<W> toSupplierOfType(InputStream inputStream, Class<W> targetType) {
        return new CachedSupplier<>(()->{
            try (InputStream stream = inputStream) {
                return JsonUtils.getObjectMapper().readValue(stream, targetType);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
