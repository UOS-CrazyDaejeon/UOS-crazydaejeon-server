package com.daejeongwang.uoscrazydaejeon.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class VisitVerificationResponse {
    private Long visitedPlaceId;
    private Long placeId;
    private Instant visitedAt;
}
