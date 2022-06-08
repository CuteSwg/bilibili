package com.swg.common;

import lombok.Data;

/**
 * @author swg
 * @Date: 2022/6/8 10:16
 * @Description:
 */
@Data
public class JsonResponse<T> {
    private String code;
    private String msg;
    private T data;

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        msg = "成功";
        code = "200";
        this.data = data;
    }

    public static JsonResponse<String> success(){
        return new JsonResponse<>(null);
    }

    public static JsonResponse<String> success(String data){
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> fail(){
        return new JsonResponse<>("500","失败");
    }

    public static JsonResponse<String> fail(String code,String msg){
        return new JsonResponse<>(code,msg);
    }
}
