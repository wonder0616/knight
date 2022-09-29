package org.jeecg.modules.tpm.fileRelation.service;

import org.jeecg.modules.tpm.fileRelation.entity.FileRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 文件关联表
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
public interface IFileRelationService extends IService<FileRelation> {

    void saveFiles(List<FileRelation> fileRelations, String relationId, String type);

    List<FileRelation> queryListByRelationIds(List<String> relationIds);


    void deleteByRelationIds(List<String> relationIds);
}
