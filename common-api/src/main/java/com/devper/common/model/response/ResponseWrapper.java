package com.devper.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper {
    private Object content;
    private boolean hasErrors;
    private List<String> errors;
    private long timestamp;
    private int status;
}
