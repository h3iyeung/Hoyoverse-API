package com.h3iyeung.hoyoapi;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.h3iyeung.hoyoapi.annotation.OnlyAPI;

@Builder(access = AccessLevel.PACKAGE)
@NotNull
@Value
public class AccountAPIConstant {
    String userGameRoleEndpoint;
    String userAccountInfoEndpoint;

    String forumFullUserInfoEndpoint;

    @Nullable
    @OnlyAPI(env = APIEnvironment.OVERSEA)
    String gamesDetailsEndpoint;

    @Nullable
    @OnlyAPI(env = APIEnvironment.CHINA)
    String navigatorsEndpoint;
}
