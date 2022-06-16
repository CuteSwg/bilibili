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
 * 文件表
 * </p>
 *
 * @author swg
 * @since 2022-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件存储路径
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件md5唯一标识串
     */
    private String md5;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;


}
