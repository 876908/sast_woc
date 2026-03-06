package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private final int errCode;
    private final String errMsg;
    @Override
    public String getMessage() {
        return errMsg;
    }
}
