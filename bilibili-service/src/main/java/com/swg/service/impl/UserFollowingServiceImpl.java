package com.swg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swg.common.ConditionException;
import com.swg.common.JsonResponse;
import com.swg.constant.UserConstant;
import com.swg.dao.UserFollowingMapper;
import com.swg.entity.FollowingGroup;
import com.swg.entity.User;
import com.swg.entity.UserFollowing;
import com.swg.entity.UserInfo;
import com.swg.service.IFollowingGroupService;
import com.swg.service.IUserFollowingService;
import com.swg.service.UserInfoService;
import com.swg.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户关注表 服务实现类
 * </p>
 *
 * @author swg
 * @since 2022-06-13
 */
@Service
public class UserFollowingServiceImpl extends ServiceImpl<UserFollowingMapper, UserFollowing> implements IUserFollowingService {

    @Resource
    private IFollowingGroupService followingGroupService;

    @Resource
    private UserService userService;

    @Resource
    private UserFollowingMapper userFollowingMapper;

    @Resource
    private UserInfoService userInfoService;

    @Override
    public JsonResponse<String> addUserFollowings(UserFollowing userFollowing) {
        Integer groupId = userFollowing.getGroupId();
        if (groupId == null){
            //添加默认分组
            FollowingGroup followingGroup = followingGroupService.getOne(new QueryWrapper<FollowingGroup>().lambda().eq(FollowingGroup::getType, UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT));
            userFollowing.setGroupId(followingGroup.getId().intValue());
        }else {
            FollowingGroup followingGroup = followingGroupService.getOne(new QueryWrapper<FollowingGroup>().lambda().eq(FollowingGroup::getId, groupId));
            if (followingGroup == null){
                throw new ConditionException("关注分组不存在！");
            }
        }
        Integer followingId = userFollowing.getFollowingId();
        User followUser = userService.getById(followingId);
        if (followUser == null){
            throw new ConditionException("关注的分组不存在");
        }
        userFollowingMapper.delete(new QueryWrapper<UserFollowing>().lambda().
                eq(UserFollowing::getUserId,userFollowing.getUserId()).
                eq(UserFollowing::getFollowingId,userFollowing.getFollowingId()));
        userFollowingMapper.insert(userFollowing);
        return JsonResponse.success();
    }

    @Override
    public JsonResponse<List<FollowingGroup>> getUserFollowings(Long userId) {
        List<UserFollowing> userFollowList = userFollowingMapper.selectList(new QueryWrapper<UserFollowing>().lambda().eq(UserFollowing::getUserId, userId));
        //获取到关注的用户列表
        Set<Integer> followingIdSet = userFollowList.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        //查询关注用户的基本信息
        List<UserInfo> userInfoList = new ArrayList<>();
        if (!followingIdSet.isEmpty()){
            userInfoList = userInfoService.selectUserInfoByUserIds(followingIdSet);
        }
        if (!userFollowList.isEmpty()){
            for (UserFollowing userFollowing : userFollowList) {
                for (UserInfo userInfo : userInfoList) {
                        userFollowing.setUserInfo(userInfo);
                }
            }
        }
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for(FollowingGroup group : groupList){
            List<UserInfo> infoList = new ArrayList<>();
            for(UserFollowing userFollowing : userFollowList){
                if(group.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            group.setFollowingUserInfoList(infoList);
            result.add(group);
        }

        return null;
    }

    @Override
    public List<UserFollowing> getUserFans(Long userId) {
        return null;
    }

    @Override
    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.save(followingGroup);
        return followingGroup.getId();
    }

    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getByUserId(userId);
    }
}
