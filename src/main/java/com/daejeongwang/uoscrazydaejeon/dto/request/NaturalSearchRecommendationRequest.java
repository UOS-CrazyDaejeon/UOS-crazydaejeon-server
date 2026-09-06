package com.daejeongwang.uoscrazydaejeon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NaturalSearchRecommendationRequest(
        List<NaturalSearchPlaceRequest> places,
        String query,
        @JsonProperty("top_k")
        Integer topK
) {
}
