package org.jeecg.modules.tpm.fileRelation.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 文件关联表
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
@Data
@TableName("tbl_file_relation")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tbl_file_relation对象", description="文件关联表")
public class FileRelation implements Serializable {

    private static final long serialVersionUID = 3229686164669025008L;

    /**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**文件地址*/
	@Excel(name = "文件地址", width = 15)
    @ApiModelProperty(value = "文件地址")
    private String fileUrl;
	/**文件名*/
	@Excel(name = "文件名", width = 15)
    @ApiModelProperty(value = "文件名")
    private String fileName;
	/**业务关联ID*/
	@Excel(name = "业务关联ID", width = 15)
    @ApiModelProperty(value = "业务关联ID")
    private String relationId;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String type;
	/**文件状态*/
	@ApiModelProperty(value = "文件状态")
    private Integer status;


}
