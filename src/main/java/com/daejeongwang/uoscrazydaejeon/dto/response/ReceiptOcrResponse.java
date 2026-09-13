package com.daejeongwang.uoscrazydaejeon.dto.response;

import com.daejeongwang.uoscrazydaejeon.entity.Receipt;
import java.time.Instant;

public record ReceiptOcrResponse(
        Long receiptId,
        Long placeId,
        String placeName,
        boolean gachaAvailable,
        Receipt.ReceiptStatus verifyStatus,
        Receipt.OcrStatus ocrStatus,
        // TODO: AI OCR 원문 정보 임시 노출. 프론트 계약 확정 후 전용 DTO로 정리한다.
        String ocrPlaceAddress,
        Instant ocrPaidAt
) {
    public static ReceiptOcrResponse from(ReceiptStatusResponse response) {
        return new ReceiptOcrResponse(
                response.getReceiptId(),
                response.getPlaceId(),
                response.getPlaceName(),
                response.isGachaAvailable(),
                response.getVerifyStatus(),
                response.getOcrStatus(),
                response.getOcrPlaceAddress(),
                response.getOcrPaidAt()
        );
    }
}
