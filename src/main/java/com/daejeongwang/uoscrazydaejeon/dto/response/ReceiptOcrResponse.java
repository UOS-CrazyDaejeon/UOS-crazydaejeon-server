package com.daejeongwang.uoscrazydaejeon.dto.response;

public record ReceiptOcrResponse(
        Long receiptId,
        Long placeId,
        String placeName,
        boolean gachaAvailable
) {
    public static ReceiptOcrResponse from(ReceiptStatusResponse response) {
        return new ReceiptOcrResponse(
                response.getReceiptId(),
                response.getPlaceId(),
                response.getPlaceName(),
                response.isGachaAvailable()
        );
    }
}
