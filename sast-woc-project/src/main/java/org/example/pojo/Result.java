package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Boolean success;
    private Integer errCode;
    private String errMsg;
    private Object data;
    public static Result success(Object data) {
        return new Result(true, 0, "success", data);
    }
    public static Result error(Integer errCode, String errMsg) {
        return new Result(false, errCode, errMsg, null);
    }
}
