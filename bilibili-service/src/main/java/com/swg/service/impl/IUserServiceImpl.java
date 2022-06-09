package com.swg.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swg.dao.UserMapper;
import com.swg.entity.User;
import com.swg.service.UserService;

/**
 * @author swg
 * @Date: 2022/6/9 10:53
 * @Description:
 */
public class IUserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
