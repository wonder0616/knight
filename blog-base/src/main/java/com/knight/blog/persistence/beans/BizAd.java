
package com.knight.blog.persistence.beans;

import com.knight.blog.framework.object.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *
 *
 * @author generate by HouTu Generator
 * @version 1.0
 * @date 2021/10/27 16:31
 * @since 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizAd extends AbstractDO{
	private String position;
	private String type;
	private String content;
	private String picture;
	private String link;
	private String title;
	private Date expiringDate;
	private Integer showNumber;
	private Integer clickNumber;
}
