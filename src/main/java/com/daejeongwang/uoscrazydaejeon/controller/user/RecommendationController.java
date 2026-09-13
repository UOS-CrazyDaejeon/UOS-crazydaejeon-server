package com.daejeongwang.uoscrazydaejeon.controller.user;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.daejeongwang.uoscrazydaejeon.dto.ResultDto;
import com.daejeongwang.uoscrazydaejeon.config.SwaggerExamples;
import com.daejeongwang.uoscrazydaejeon.dto.request.NaturalSearchQueryRequest;
import com.daejeongwang.uoscrazydaejeon.dto.response.AiNextPlacesRecommendationResponse;
import com.daejeongwang.uoscrazydaejeon.dto.response.AiSimilarRecommendationResponse;
import com.daejeongwang.uoscrazydaejeon.exception.AuthenticationFailedException;
import com.daejeongwang.uoscrazydaejeon.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
@Tag(name = "Recommendation", description = "AI 추천 장소 API")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/similar-places")
    @Operation(summary = "선택 장소 기반 유사 장소 추천", description = "선택한 장소와 1km 이내 장소 정보를 AI 서버에 전달하여 유사 장소를 추천받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유사 장소 추천 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.UNAUTHORIZED))),
            @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)))
    })
    public ResponseEntity<AiSimilarRecommendationResponse> recommendSimilarPlaces(
            Authentication authentication,
            @RequestParam Long placeId
    ) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationFailedException("로그인이 필요한 요청입니다.");
        }

        Long memberId = Long.valueOf(authentication.getName());
        AiSimilarRecommendationResponse response = recommendationService.recommendSimilarPlaces(memberId, placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/next-places")
    @Operation(summary = "선택 장소와 방문 이력 기반 다음 장소 추천", description = "선택한 장소, 1km 이내 장소, 내 방문 장소 정보를 AI 서버에 전달하여 다음 장소를 추천받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "다음 장소 추천 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.UNAUTHORIZED))),
            @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)))
    })
    public ResponseEntity<AiNextPlacesRecommendationResponse> recommendNextPlaces(
            Authentication authentication,
            @RequestParam Long placeId
    ) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationFailedException("로그인이 필요한 요청입니다.");
        }

        Long memberId = Long.valueOf(authentication.getName());
        AiNextPlacesRecommendationResponse response = recommendationService.recommendNextPlaces(memberId, placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/natural-search")
    @Operation(summary = "자연어 기반 장소 추천", description = "선택한 장소의 1km 이내 장소 정보와 검색어를 AI 서버에 전달하여 장소를 추천받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자연어 검색 추천 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.UNAUTHORIZED))),
            @ApiResponse(responseCode = "404", description = "데이터를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)))
    })
    public ResponseEntity<Object> recommendNaturalSearch(
            Authentication authentication,
            @RequestParam Long placeId,
            @RequestBody NaturalSearchQueryRequest request
    ) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationFailedException("로그인이 필요한 요청입니다.");
        }

        Object response = recommendationService.recommendNaturalSearch(
                placeId,
                request.query(),
                request.topK()
        );

        return ResponseEntity.ok(response);
    }
}
