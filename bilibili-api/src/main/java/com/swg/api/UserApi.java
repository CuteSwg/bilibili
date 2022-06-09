package com.swg.api;

import com.swg.common.JsonResponse;
import com.swg.entity.User;
import com.swg.service.UserService;
import com.swg.utils.RSAUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        return new JsonResponse<>(RSAUtil.getPublicKeyStr());
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        return null;
    }
}
