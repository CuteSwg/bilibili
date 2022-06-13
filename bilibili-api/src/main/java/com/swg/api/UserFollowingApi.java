package com.swg.api;

import com.swg.common.JsonResponse;
import com.swg.entity.FollowingGroup;
import com.swg.entity.UserFollowing;
import com.swg.service.IUserFollowingService;
import com.swg.support.UserSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:用户关注api
 * @author: swg
 * @create: 2022-06-13 14:47
 **/
public class UserFollowingApi {

    @Resource
    private IUserFollowingService userFollowingService;

    @Resource
    private UserSupport userSupport;

    /**
     * 添加关注
     * @param userFollowing 添加实体
     * @return 通用返回
     */
    @PostMapping("user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        return userFollowingService.addUserFollowings(userFollowing);
    }

    /**
     * 获取用户关注
     * @return
     */
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings(){
        Long userId = userSupport.getCurrentUserId();
        return userFollowingService.getUserFollowings(userId);
    }

    /**
     * 获得用户粉丝
     * @return
     */
    public JsonResponse<List<UserFollowing>> getUserFans(){
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> result = userFollowingService.getUserFans(userId);
        return new JsonResponse<>(result);
    }

    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }

    @GetMapping("/user-following-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroups(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> list = userFollowingService.getUserFollowingGroups(userId);
        return new JsonResponse<>(list);
    }
}
