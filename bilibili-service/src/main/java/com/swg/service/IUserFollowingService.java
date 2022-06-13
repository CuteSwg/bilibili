package com.swg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swg.common.JsonResponse;
import com.swg.entity.FollowingGroup;
import com.swg.entity.UserFollowing;

import java.util.List;

/**
 * <p>
 * 用户关注表 服务类
 * </p>
 *
 * @author swg
 * @since 2022-06-13
 */
public interface IUserFollowingService extends IService<UserFollowing> {

    /**
     * 用户添加关注
     * @param userFollowing
     * @return
     */
    JsonResponse<String> addUserFollowings(UserFollowing userFollowing);

    /**
     * 获取用户关注
     * @param userId 用户id
     * @return 通用返回
     */
    JsonResponse<List<FollowingGroup>> getUserFollowings(Long userId);

    List<UserFollowing> getUserFans(Long userId);

    Long addUserFollowingGroups(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(Long userId);
}
