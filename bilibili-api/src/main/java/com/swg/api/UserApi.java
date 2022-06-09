package com.swg.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swg.common.JsonResponse;
import com.swg.dao.UserInfoMapper;
import com.swg.entity.User;
import com.swg.entity.UserInfo;
import com.swg.service.UserInfoService;
import com.swg.service.UserService;
import com.swg.support.UserSupport;
import com.swg.utils.RSAUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;

/**
 * @author swg
 * @Date: 2022/6/9 10:33
 * @Description:
 */
@RestController
public class UserApi {

    @Resource
    private UserService userService;

    @Resource
    private UserSupport userSupport;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        return new JsonResponse<>(RSAUtil.getPublicKeyStr());
    }

    @GetMapping("/users")
    public JsonResponse<User> getUserInfo(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getById(userId);
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, user.getId()));
        user.setUserInfo(userInfo);
        return new JsonResponse<User>(user);
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody @Validated User user){
        return userService.addUser(user);
    }

    @PutMapping("/users")
    public JsonResponse<String> updateUser(@RequestBody User user){
        user.setId(userSupport.getCurrentUserId());
        return userService.updateUser(user);
    }

    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfo(@RequestBody UserInfo userInfo){
        userInfo.setUserId(userSupport.getCurrentUserId());
        return userInfoService.updateUserInfo(userInfo);
    }

    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception{
        return userService.login(user);
    }



}
