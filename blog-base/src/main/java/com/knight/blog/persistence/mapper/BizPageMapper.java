package com.knight.blog.persistence.mapper;

import com.knight.blog.business.vo.PageConditionVO;
import com.knight.blog.persistence.beans.BizPage;
import com.knight.blog.plugin.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface BizPageMapper extends BaseMapper<BizPage> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    List<BizPage> findPageBreakByCondition(PageConditionVO vo);
}
