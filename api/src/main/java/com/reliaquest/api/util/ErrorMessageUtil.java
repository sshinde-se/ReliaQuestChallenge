package com.reliaquest.api.util;

import com.reliaquest.api.enums.ErrorCode;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ErrorMessageUtil {
    private static final Map<ErrorCode, String> errorCodeStringMap = new HashMap<>();
    static {
        errorCodeStringMap.put(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, "Exception while calling external API service");
        errorCodeStringMap.put(ErrorCode.NO_RECORDS_FOUND, "No records found");
        errorCodeStringMap.put(ErrorCode.INVALID_INPUT, "Invalid input");
    }
    public static String getErrorMessage(ErrorCode errorCode) {
        return errorCodeStringMap.get(errorCode);
    }
}
