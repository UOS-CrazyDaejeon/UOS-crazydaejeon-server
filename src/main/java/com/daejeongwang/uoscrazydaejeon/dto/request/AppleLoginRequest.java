package com.daejeongwang.uoscrazydaejeon.dto.request;

public record AppleLoginRequest(
        String identityToken,
        String authorizationCode
) {
}
