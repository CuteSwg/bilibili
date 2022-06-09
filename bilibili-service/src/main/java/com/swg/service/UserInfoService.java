package com.swg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swg.common.JsonResponse;
import com.swg.entity.UserInfo;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-09 19:30
 **/
public interface UserInfoService extends IService<UserInfo> {

    JsonResponse<String> updateUserInfo(UserInfo userInfo);
}
