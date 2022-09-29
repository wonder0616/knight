package org.jeecg.modules.tpm.fileRelation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import io.netty.util.internal.StringUtil;
import org.jeecg.modules.tpm.constant.TPMConstant;
import org.jeecg.modules.tpm.fileRelation.entity.FileRelation;
import org.jeecg.modules.tpm.fileRelation.mapper.FileRelationMapper;
import org.jeecg.modules.tpm.fileRelation.service.IFileRelationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 文件关联表
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
@Service
public class FileRelationServiceImpl extends ServiceImpl<FileRelationMapper, FileRelation> implements IFileRelationService {

    @Override
    public void saveFiles(List<FileRelation> fileRelations, String relationId,String type) {
        fileRelations.stream().forEach(files -> {
            files.setRelationId(relationId);
            files.setType(type);
            this.baseMapper.updateById(files);
        });
        //更新不存在列表里的文件,状态为9
        List<String> ids = fileRelations.stream().map(FileRelation::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<FileRelation> eq = new UpdateWrapper<FileRelation>().lambda().eq(FileRelation::getRelationId, relationId)
                .eq(FileRelation::getType, type);
        if(ids.size()>0){
            eq.notIn(FileRelation::getId, ids);
        }
        this.baseMapper.update(new FileRelation().setStatus(TPMConstant.FileRelationStatus.DEACTIVATE.getId()),
                eq)
        ;

    }

    @Override
    public List<FileRelation> queryListByRelationIds(List<String> relationIds) {
        // 处理为空报错问题
        if (relationIds == null || relationIds.size() == 0) {
            return Lists.newArrayList();
        }
        QueryWrapper<FileRelation> fileRelationQueryWrapper = new QueryWrapper<>();
        fileRelationQueryWrapper.in("relation_id",relationIds);
        fileRelationQueryWrapper.eq("status",TPMConstant.FileRelationStatus.NORMAL.getId());
        List<FileRelation> fileRelations = this.baseMapper.selectList(fileRelationQueryWrapper);
        return fileRelations;
    }



    @Override
    public void deleteByRelationIds(List<String> relationIds) {
        LambdaQueryWrapper<FileRelation> delFilesWrapper = new LambdaQueryWrapper<>();
        delFilesWrapper.in(FileRelation::getRelationId,relationIds);
        this.baseMapper.delete(delFilesWrapper);
    }
}
