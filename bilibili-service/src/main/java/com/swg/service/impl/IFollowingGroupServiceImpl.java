package com.swg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swg.dao.FollowingGroupMapper;
import com.swg.dao.UserInfoMapper;
import com.swg.entity.FollowingGroup;
import com.swg.entity.UserInfo;
import com.swg.service.IFollowingGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-13 15:01
 **/
@Service
public class IFollowingGroupServiceImpl extends ServiceImpl<FollowingGroupMapper, FollowingGroup> implements IFollowingGroupService {

    @Resource
    private FollowingGroupMapper followingGroupMapper;

    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupMapper.selectList(new QueryWrapper<FollowingGroup>().lambda().eq(FollowingGroup::getUserId,userId));
    }
}
