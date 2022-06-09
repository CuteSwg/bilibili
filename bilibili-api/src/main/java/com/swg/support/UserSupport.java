package com.swg.support;

import com.swg.common.ConditionException;
import com.swg.service.UserService;
import com.swg.utils.TokenUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * @description: 用户支撑类
 * @author: swg
 * @create: 2022-06-09 18:56
 **/
@Component
public class UserSupport {

    @Resource
    private UserService userService;

    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if (userId == 0){
            throw new ConditionException("非法用户");
        }else {
            return userId;
        }
    }
}
