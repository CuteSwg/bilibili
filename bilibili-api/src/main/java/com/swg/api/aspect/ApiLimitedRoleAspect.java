package com.swg.api.aspect;

import com.swg.annotation.LimitedRole;
import com.swg.support.UserSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 19:40
 **/
@Order(1)
@Aspect
@Component
public class ApiLimitedRoleAspect {

    @Resource
    private UserSupport userSupport;

    @Pointcut("@annotation(com.swg.annotation.LimitedRole)")
    public void check(){
    }

    @Before("check() && @annotation(LimitedRole)")
    public void doBefore(JoinPoint joinPoint, LimitedRole LimitedRole){
//        Long userId = userSupport.getCurrentUserId();
//        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
//        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
//        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
//        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
//        roleCodeSet.retainAll(limitedRoleCodeSet);
//        if(roleCodeSet.size() > 0){
//            throw new ConditionException("权限不足！");
//        }
    }
}
