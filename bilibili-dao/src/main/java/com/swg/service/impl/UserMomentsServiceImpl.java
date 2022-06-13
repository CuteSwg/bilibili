package com.swg.service.impl;

import com.swg.entity.UserMoments;
import com.swg.dao.UserMomentsMapper;
import com.swg.service.IUserMomentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户动态表 服务实现类
 * </p>
 *
 * @author swg
 * @since 2022-06-13
 */
@Service
public class UserMomentsServiceImpl extends ServiceImpl<UserMomentsMapper, UserMoments> implements IUserMomentsService {

}
