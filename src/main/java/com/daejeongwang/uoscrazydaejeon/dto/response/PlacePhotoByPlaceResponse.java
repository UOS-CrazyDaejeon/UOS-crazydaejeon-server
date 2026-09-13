package com.daejeongwang.uoscrazydaejeon.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PlacePhotoByPlaceResponse {
    private Long placePhotoId;
    private String imageUrl;
    private Instant createdAt;
    private boolean me;
}
