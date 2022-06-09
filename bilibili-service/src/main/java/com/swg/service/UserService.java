package com.swg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swg.common.JsonResponse;
import com.swg.entity.User;

/**
 * @author swg
 * @Date: 2022/6/9 10:55
 * @Description:
 */
public interface UserService extends IService<User> {

    /**
     * 添加用户
     * @param user 实体
     * @return 返回对象
     */
    JsonResponse<String> addUser(User user);

    /**
     * 登录
     * @param user 实体
     * @return 返回对象
     */
    JsonResponse<String> login(User user) throws Exception;

    /**
     * 修改用户信息
     * @param user 实体
     * @return 返回对象
     */
    JsonResponse<String> updateUser(User user);
}
