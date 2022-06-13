package com.swg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swg.common.JsonResponse;
import com.swg.dao.UserInfoMapper;
import com.swg.entity.UserInfo;
import com.swg.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-09 19:31
 **/
@Service
public class IUserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public JsonResponse<String> updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateUserInfo(userInfo);
        return JsonResponse.success();
    }

    @Override
    public List<UserInfo> selectUserInfoByUserIds(Set<Integer> followingIdSet) {
        return userInfoMapper.selectUserInfoByUserIds(followingIdSet);
    }
}
