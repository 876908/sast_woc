package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


public class BusinessException extends RuntimeException {
    private final int errCode;
    public BusinessException(int errCode,String message){
        super(message);
        this.errCode=errCode;
    }
    public int getErrCode(){
        return errCode;
    }
}
