package com.swg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户关注分组表
 * </p>
 *
 * @author swg
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_following_group")
public class FollowingGroup implements Serializable {

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
     * 关注分组名称
     */
    private String name;

    /**
     * 关注分组类型：0特别关注  1悄悄关注 2默认分组  3用户自定义分组
     */
    private String type;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<UserInfo> followingUserInfoList;

}
