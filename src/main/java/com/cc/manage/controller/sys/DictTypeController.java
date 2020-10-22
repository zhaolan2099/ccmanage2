package com.cc.manage.controller.sys;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.Dict;
import com.cc.manage.domain.sys.DictType;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.BaseQuery;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.DictQuery;
import com.cc.manage.service.sys.DictService;
import com.cc.manage.service.sys.DictTypeService;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/9/7 11:25
 */
@Api(tags = "字典表类型管理")
@Slf4j
@RestController
@RequestMapping("dictType")
public class DictTypeController {
    @Resource
    DictTypeService typeService;
    @GetMapping(value = "selectList")
    @ApiOperation("码表类型列表，带分页")
    public Result<PageVo<DictType>> selectList(BaseQuery query){
        log.info("码表类型列表，请求参数:{}",query.toString());
        query.setPageHiden(false);
        Result<PageVo<DictType>> result = new Result();
        try {
            PageVo<DictType> pageVo = typeService.selectListForPage(query);
            result = result.success(pageVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("码表类型列表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("码表类型列表，响应参数:{}",result.toString());
        return result;
    }
    @GetMapping(value = "save")
    @ApiOperation("保存码表类型,新增/修改")
    public Result save(DictType dict){
        log.info("保存码表类型，请求参数:{}",dict.toString());
        Result result = new Result();
        try {
            typeService.save(dict);
            result = result.success();
        } catch (BizException e){
            e.printStackTrace();
            log.error("保存码表类型，业务异常:{}", e.getMessage(), e);
            result = result.fail(e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存码表类型，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("保存码表，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "delete")
    @ApiOperation("删除码表类型")
    public Result delete(@RequestParam(value = "ids") List<Long> ids){
        log.info("删除码表类型，请求参数:{}",ids.toString());
        Result result = new Result();
        try {
            typeService.delete(ids);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除码表类型，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("删除码表类型，响应参数:{}",result.toString());
        return result;
    }


    @GetMapping(value = "getAll")
    @ApiOperation("获取码表类型名称")
    public Result<List<DictType>> getByType(){
        log.info("获取码表类型名称");
        BaseQuery query = new BaseQuery();
        query.setPageHiden(true);
        Result result = new Result();
        try {
            List<DictType> list = typeService.selectList(query);
            result = result.success(list);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取码表类型名称，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("获取码表类型名称，响应参数:{}",result.toString());
        return result;
    }
}
