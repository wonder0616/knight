
package com.knight.blog.persistence.mapper;

import com.knight.blog.business.vo.BizAdConditionVO;
import com.knight.blog.persistence.beans.BizAd;
import com.knight.blog.plugin.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 *
 * @author generate by HouTu Generator
 * @version 1.0
 * @date 2021/10/27 16:31
 * @since 1.8
 */
@Repository
public interface BizAdMapper extends BaseMapper<BizAd>{

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<BizAd> findPageBreakByCondition(BizAdConditionVO vo);
}
