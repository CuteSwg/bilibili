package com.swg.dao;

import com.swg.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户基本信息表 Mapper 接口
 * </p>
 *
 * @author swg
 * @since 2022-06-09
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 修改用户详细信息
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 通过集合查询userInfo
     * @param followingIdSet 集合
     * @return
     */
    List<UserInfo> selectUserInfoByUserIds(@Param("userIdList") Set<Integer> followingIdSet);
}
