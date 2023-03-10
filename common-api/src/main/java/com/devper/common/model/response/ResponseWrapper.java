package com.devper.common.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseWrapper {
    private Object content;
    private boolean hasErrors;
    private List<String> errors;
    private long timestamp;
    private int status;
}
