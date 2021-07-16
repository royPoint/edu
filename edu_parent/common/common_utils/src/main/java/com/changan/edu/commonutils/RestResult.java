package com.changan.edu.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class RestResult {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private RestResult(){}

    public static RestResult ok(){
        RestResult restResult = new RestResult();
        restResult.setSuccess(true);
        restResult.setCode(ResultCode.SUCCESS);
        restResult.setMessage("成功");
        return restResult;
    }

    public static RestResult error(){
        RestResult restResult = new RestResult();
        restResult.setSuccess(false);
        restResult.setCode(ResultCode.ERROR);
        restResult.setMessage("失败");
        return restResult;
    }

    public RestResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public RestResult message(String message){
        this.setMessage(message);
        return this;
    }

    public RestResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public RestResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public RestResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
