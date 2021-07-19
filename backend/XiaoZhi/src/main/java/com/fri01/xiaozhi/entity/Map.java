package com.fri01.xiaozhi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fri01
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("map")
public class Map implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("Map_name")
    private String mapName;

    @TableField("Map_path")
    private String mapPath;

    @TableField("Index_path")
    private String indexPath;

    @TableField("Map_show_path")
    private String mapShowPath;


}
