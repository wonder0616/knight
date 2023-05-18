package com.knight.blog.business.service;


import com.knight.blog.framework.object.AbstractService;
import com.knight.blog.business.entity.Type;
import com.knight.blog.business.vo.TypeConditionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分类
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://docs.zhyd.me
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface BizTypeService extends AbstractService<Type, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Type> findPageBreakByCondition(TypeConditionVO vo);

    List<Type> listParent();

    List<Type> listTypeForMenu();

    List<Type> listTypeByPosition(String position);
}
