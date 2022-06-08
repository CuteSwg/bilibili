package com.swg.handler;

import com.swg.common.ConditionException;
import com.swg.common.JsonResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @author swg
 * @Date: 2022/6/8 10:45
 * @Description:
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request,Exception e){
        String message = e.getMessage();
        if (e instanceof ConditionException){
            String code = ((ConditionException) e).getCode();
            return new JsonResponse<String>(message,code);
        }else {
            return new JsonResponse<String>("500",message);
        }
    }
}
