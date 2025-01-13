package com.reliaquest.api.util;

import com.reliaquest.api.enums.ErrorCode;
import com.reliaquest.api.exception.APIException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtil {
    public static APIException getAPIException(ErrorCode errorCode){
        String message = ErrorMessageUtil.getErrorMessage(errorCode);
        return new APIException(errorCode, message);
    }
}
