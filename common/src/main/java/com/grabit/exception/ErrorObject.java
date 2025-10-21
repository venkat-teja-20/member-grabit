package com.grabit.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorObject {
    private Map<String,String> errorMsg;

    private int httpCode;

    private String service;
}
