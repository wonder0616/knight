package org.jeecg.modules.tpm.fileRelation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.tpm.fileRelation.entity.FileRelation;
import org.jeecg.modules.tpm.fileRelation.service.IFileRelationService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 文件关联表
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
@Api(tags="文件关联表")
@RestController
@RequestMapping("/fileRelation/fileRelation")
@Slf4j
public class FileRelationController extends JeecgController<FileRelation, IFileRelationService> {
	@Autowired
	private IFileRelationService fileRelationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fileRelation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文件关联表-分页列表查询")
	@ApiOperation(value="文件关联表-分页列表查询", notes="文件关联表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FileRelation fileRelation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FileRelation> queryWrapper = QueryGenerator.initQueryWrapper(fileRelation, req.getParameterMap());
		Page<FileRelation> page = new Page<FileRelation>(pageNo, pageSize);
		IPage<FileRelation> pageList = fileRelationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fileRelation
	 * @return
	 */
	@AutoLog(value = "文件关联表-添加")
	@ApiOperation(value="文件关联表-添加", notes="文件关联表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FileRelation fileRelation) {
		fileRelationService.save(fileRelation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fileRelation
	 * @return
	 */
	@AutoLog(value = "文件关联表-编辑")
	@ApiOperation(value="文件关联表-编辑", notes="文件关联表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FileRelation fileRelation) {
		fileRelationService.updateById(fileRelation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文件关联表-通过id删除")
	@ApiOperation(value="文件关联表-通过id删除", notes="文件关联表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fileRelationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文件关联表-批量删除")
	@ApiOperation(value="文件关联表-批量删除", notes="文件关联表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fileRelationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文件关联表-通过id查询")
	@ApiOperation(value="文件关联表-通过id查询", notes="文件关联表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FileRelation fileRelation = fileRelationService.getById(id);
		if(fileRelation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(fileRelation);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fileRelation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FileRelation fileRelation) {
        return super.exportXls(request, fileRelation, FileRelation.class, "文件关联表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FileRelation.class);
    }

}
