package com.swg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户关注表
 * </p>
 *
 * @author swg
 * @since 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_following")
public class UserFollowing implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 关注用户id
     */
    @TableField("followingId")
    private Integer followingId;

    /**
     * 关注分组id
     */
    @TableField("groupId")
    private Integer groupId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;


}
