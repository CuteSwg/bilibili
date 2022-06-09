package com.swg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swg.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author swg
 * @since 2022-06-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据电话或者email查找用户
     * @param phone
     * @param email
     * @return
     */
    User getUserByPhoneOrEmail(@Param("phone") String phone, @Param("email") String email);

    /**
     * 按照条件修改用户
     * @param user
     */
    void updateUserById(User user);
}
