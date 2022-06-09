package com.swg.dao;

import com.swg.entity.UserFollowing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户关注表 Mapper 接口
 * </p>
 *
 * @author swg
 * @since 2022-06-09
 */
@Mapper
public interface UserFollowingMapper extends BaseMapper<UserFollowing> {

}
