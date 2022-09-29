package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("rzg_role_menu")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rzg_role_menu", description="菜单codeURL")
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    /**菜单code*/
    @ApiModelProperty(value = "菜单code")
    private String code;
    /**url*/
    @ApiModelProperty(value = "URL")
    private String url;

    /*备注*/
    @ApiModelProperty(value = "备注")
    private String remark;

}
