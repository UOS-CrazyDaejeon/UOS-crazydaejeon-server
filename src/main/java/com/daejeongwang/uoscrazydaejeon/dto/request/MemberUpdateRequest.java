package com.daejeongwang.uoscrazydaejeon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(
        @Schema(description = "닉네임", example = "대전왕")
        @NotBlank(message = "닉네임은 필수 항목입니다.")
        String nickname,

        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호는 필수 항목입니다.")
        String phone
) {
}
