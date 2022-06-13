package com.swg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swg.entity.FollowingGroup;

import java.util.List;

/**
 * <p>
 * 用户关注分组表 服务类
 * </p>
 *
 * @author swg
 * @since 2022-06-13
 */
public interface IFollowingGroupService extends IService<FollowingGroup> {

    List<FollowingGroup> getByUserId(Long userId);
}
