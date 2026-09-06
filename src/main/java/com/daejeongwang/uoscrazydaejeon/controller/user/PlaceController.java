package com.daejeongwang.uoscrazydaejeon.controller.user;

import com.daejeongwang.uoscrazydaejeon.config.SwaggerExamples;
import com.daejeongwang.uoscrazydaejeon.dto.ResultDto;
import com.daejeongwang.uoscrazydaejeon.dto.response.PlaceResponse;
import com.daejeongwang.uoscrazydaejeon.repository.PlaceRepository;
import com.daejeongwang.uoscrazydaejeon.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@Tag(name = "Place", description = "장소 조회 API")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    @Operation(summary = "전체 장소 조회", description = "대전의 모든 장소를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 장소 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST)
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)
                    ))
    })
    public ResponseEntity<List<PlaceResponse>> findAllPlaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PlaceResponse> response = placeService.findAllPlaces(page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{placeId}")
    @Operation(summary = "특정 장소 조회", description = "대전의 특정 장소를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "특정 장소 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 장소 ID",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST)
                    )),
            @ApiResponse(responseCode = "404", description = "장소를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND)
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)
                    ))
    })
    public ResponseEntity<PlaceResponse> getPlaceByPlaceId(@PathVariable Long placeId) {
        PlaceResponse response = placeService.getPlaceById(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}/nearby")
    @Operation(summary = "특정 장소의 근처 장소 조회", description = "특정 장소의 근처 장소를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "근처 장소 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST)
                    )),
            @ApiResponse(responseCode = "404", description = "장소를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.NOT_FOUND)
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)
                    ))
    })
    public ResponseEntity<Page<PlaceResponse>> getNearbyPlacesByPlaceId(
            @PathVariable Long placeId,
            @RequestParam(defaultValue = "1000") Double radius,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                placeService.getNearbyPlacesByPlaceId(placeId, radius, page, size)
        );
    }

    @GetMapping("/top-visitors")
    @Operation(
            summary = "내 주변 인기 장소 조회",
            description = "현재 좌표 기준 1km 이내 장소 중 각 장소의 최신 방문자 수를 기준으로 상위 5개를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주변 인기 장소 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 위도 또는 경도",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.BAD_REQUEST)
                    )),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(
                            schema = @Schema(implementation = ResultDto.class),
                            examples = @ExampleObject(value = SwaggerExamples.INTERNAL_SERVER_ERROR)
                    ))
    })
    public ResponseEntity<List<PlaceResponse>> getTopKPlacesByVisitors(
            @RequestParam(defaultValue = "36.3504") double latitude,
            @RequestParam(defaultValue = "127.3845") double longitude
    ) {
        return ResponseEntity.ok(
                placeService.getTopKPlacesByVisitors(latitude, longitude)
        );
    }
}
