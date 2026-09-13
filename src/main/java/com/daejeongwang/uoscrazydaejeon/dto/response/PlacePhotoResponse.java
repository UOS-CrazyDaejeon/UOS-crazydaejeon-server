package com.daejeongwang.uoscrazydaejeon.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PlacePhotoResponse {
    private Long placePhotoId;
    private Long placeId;
    private String placeName;
    private String imageUrl;
    private Instant createdAt;
}
