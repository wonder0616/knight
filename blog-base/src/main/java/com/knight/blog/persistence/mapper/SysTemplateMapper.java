package com.knight.blog.persistence.mapper;

import com.knight.blog.business.vo.TemplateConditionVO;
import com.knight.blog.persistence.beans.SysTemplate;
import com.knight.blog.plugin.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @website https://docs.zhyd.me
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysTemplateMapper extends BaseMapper<SysTemplate>{

    /**
     * 分页查询
     * @param vo
     *
     * @return
     */
    List<SysTemplate> findPageBreakByCondition(TemplateConditionVO vo);
}
