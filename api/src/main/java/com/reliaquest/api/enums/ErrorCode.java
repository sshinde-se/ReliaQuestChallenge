package com.reliaquest.api.enums;

public enum ErrorCode {
    EXCEPTION_WHILE_CALLING_EXTERNAL_API("412"),
    NO_RECORDS_FOUND("404"),
    INVALID_INPUT("400"),
    INTERNAL_SERVER_ERROR("500");

    private final String code;
    ErrorCode(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }
}
