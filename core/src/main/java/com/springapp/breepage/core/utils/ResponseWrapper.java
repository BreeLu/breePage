package com.springapp.breepage.core.utils;

import com.google.common.collect.ImmutableMap;
import static com.springapp.breepage.core.utils.JsonUtils.toJson;

public class ResponseWrapper {
    public static String wrap(int code, String message, String contentKey, Object content) {
        return toJson(ImmutableMap.of("code", code, "msg", message, contentKey, content));
    }

    public static String wrap(ErrorCode errorCode, String contentKey, Object content) {
        return toJson(ImmutableMap.of("code", errorCode.getCode(), "msg", errorCode.getMessage(), contentKey, content));
    }

    public static String wrap(ErrorCode errorCode) {
        return toJson(ImmutableMap.of("code", errorCode.getCode(), "msg", errorCode.getMessage()));
    }

    public static String wrapSucceedResponse(String contentKey, Object content) {
        return wrap(ErrorCode.SUCCESS, contentKey, content);
    }

    public static String wrapFailedResponse(String contentKey, Object content) {
        return wrap(ErrorCode.FAIL, contentKey, content);
    }

    public static String wrapResponse(String contentKey, Object content) {
        ErrorCode errorCode = ErrorCode.SUCCESS;
        if (content == null) {
            errorCode = ErrorCode.FAIL;
            content = "";
        }
        return wrap(errorCode, contentKey, content);
    }

    public static String success() {
        return toJson(ImmutableMap.of("code", ErrorCode.SUCCESS.getCode(), "msg", ErrorCode.SUCCESS.getMessage()));
    }

    public static String fail() {
        return toJson(ImmutableMap.of("code", ErrorCode.FAIL.getCode(), "msg", ErrorCode.FAIL.getMessage()));
    }

    public static String wrapResponse(String key1, Object content1, String key2, Object content2) {
        return toJson(ImmutableMap.of("code", ErrorCode.SUCCESS.getCode(), "msg", ErrorCode.SUCCESS.getMessage(),
                key1, content1, key2, content2));
    }
}
