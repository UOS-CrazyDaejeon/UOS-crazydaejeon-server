package com.daejeongwang.uoscrazydaejeon.dto.request;

import com.daejeongwang.uoscrazydaejeon.entity.Place;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.stream.Stream;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NaturalSearchPlaceRequest(
        @JsonProperty("category_large")
        String categoryLarge,
        @JsonProperty("category_medium")
        String categoryMedium,
        @JsonProperty("category_small")
        String categorySmall,
        @JsonProperty("congestion_rate")
        Double congestionRate,
        @JsonProperty("description")
        String placeDescription,
        String dong,
        String gu,
        Double latitude,
        Double longitude,
        @JsonProperty("place_address")
        String placeAddress,
        @JsonProperty("place_id")
        Long placeId,
        @JsonProperty("place_name")
        String placeName,
        String tag,
        @JsonProperty("visitor_count")
        Long visitorCount
) {

    public static NaturalSearchPlaceRequest from(
            Place place,
            Double congestionRate,
            Long visitorCount
    ) {
        return NaturalSearchPlaceRequest.builder()
                .categoryLarge(defaultString(place.getCategoryLarge()))
                .categoryMedium(defaultString(place.getCategoryMedium()))
                .categorySmall(defaultString(place.getCategorySmall()))
                .congestionRate(congestionRate)
                .placeDescription(defaultString(place.getPlaceDescription()))
                .dong(defaultString(place.getDong()))
                .gu(defaultString(place.getGu()))
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .placeAddress(defaultString(place.getPlaceAddress()))
                .placeId(place.getId())
                .placeName(defaultString(place.getPlaceName()))
                .tag(createTag(place))
                .visitorCount(visitorCount)
                .build();
    }

    private static String createTag(Place place) {
        if (place.getTag() != null && !place.getTag().isBlank()) {
            return place.getTag();
        }

        return Stream.of(
                        place.getCategoryLarge(),
                        place.getCategoryMedium(),
                        place.getCategorySmall(),
                        place.getGu(),
                        place.getDong()
                )
                .filter(value -> value != null && !value.isBlank())
                .reduce((left, right) -> left + "," + right)
                .orElse("");
    }

    private static String defaultString(String value) {
        return value == null ? "" : value;
    }
}
