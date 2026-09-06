package com.daejeongwang.uoscrazydaejeon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaturalSearchQueryRequest(
        String query,
        @JsonProperty("top_k")
        Integer topK
) {
}
