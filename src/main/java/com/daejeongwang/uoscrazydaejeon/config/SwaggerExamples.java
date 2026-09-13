package com.daejeongwang.uoscrazydaejeon.config;

public final class SwaggerExamples {

    public static final String BAD_REQUEST = """
            {
              "success": false,
              "message": "IllegalArgumentException : 잘못된 요청입니다.",
              "code": 400
            }
            """;

    public static final String NOT_FOUND = """
            {
              "success": false,
              "message": "Resource not Found : 장소를 찾을 수 없습니다.",
              "code": 404
            }
            """;

    public static final String INTERNAL_SERVER_ERROR = """
            {
              "success": false,
              "message": "Internal server error",
              "code": 500
            }
            """;

    public static final String UNAUTHORIZED = """
            {
              "success": false,
              "message": "AuthenticationFailed : 인증이 필요합니다.",
              "code": 401
            }
            """;

    public static final String FORBIDDEN = """
            {
              "success": false,
              "message": "Forbidden : 접근 권한이 없습니다.",
              "code": 403
            }
            """;

    public static final String CONFLICT = """
            {
              "success": false,
              "message": "ConflictException : 현재 상태에서는 요청을 처리할 수 없습니다.",
              "code": 409
            }
            """;

    public static final String UNSUPPORTED_MEDIA_TYPE = """
            {
              "success": false,
              "message": "UnsupportedMediaType : 지원하지 않는 이미지 형식입니다.",
              "code": 415
            }
            """;

    private SwaggerExamples() {
    }
}
