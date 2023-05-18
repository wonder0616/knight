
package com.knight.blog.business.service;


import com.knight.blog.business.enums.AdPositionEnum;
import com.knight.blog.framework.object.AbstractService;
import com.knight.blog.business.entity.BizAdBo;
import com.knight.blog.business.vo.BizAdConditionVO;
import com.github.pagehelper.PageInfo;

/**
 *
 *
 * @author generate by HouTu Generator
 * @version 1.0
 * @date 2021/10/27 16:43
 * @since 1.8
 */
public interface BizAdService extends AbstractService<BizAdBo, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<BizAdBo> findPageBreakByCondition(BizAdConditionVO vo);

    BizAdBo getByPosition(AdPositionEnum positionEnum);
}
