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
 * 用户基本信息表
 * </p>
 *
 * @author swg
 * @since 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 签名
     */
    private String sign;

    /**
     * 性别：0男 1女 2未知
     */
    private String gender;

    /**
     * 生日
     */
    private String birth;

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


}
