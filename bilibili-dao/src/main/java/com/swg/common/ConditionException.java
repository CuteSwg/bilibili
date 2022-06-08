package com.swg.common;

/**
 * @author swg
 * @Date: 2022/6/8 10:46
 * @Description: 根据条件抛出异常
 */
public class ConditionException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code,String name){
        super(name);
        this.code = code;
    }

    public ConditionException(String name){
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
