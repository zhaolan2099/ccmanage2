package com.cc.manage.controller.sys;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.Dict;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.DictQuery;
import com.cc.manage.service.sys.DictService;
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
@Api(tags = "字典表管理")
@Slf4j
@RestController
@RequestMapping("dict")
public class DictController {
    @Resource
    DictService dictService;
    @GetMapping(value = "selectList")
    @ApiOperation("码表列表，带分页")
    public Result<PageVo<Dict>> selectList(DictQuery query){
        log.info("码表列表，请求参数:{}",query.toString());
        query.setPageHiden(false);
        Result<PageVo<Dict>> result = new Result();
        try {
            PageVo<Dict> pageVo = dictService.selectListForPage(query);
            result = result.success(pageVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("码表列表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("码表列表，响应参数:{}",result.toString());
        return result;
    }
    @GetMapping(value = "save")
    @ApiOperation("保存码表,新增/修改")
    public Result save(Dict dict){
        log.info("保存码表，请求参数:{}",dict.toString());
        Result result = new Result();
        try {
            dictService.save(dict);
            result = result.success();
        } catch (Exception e){
            e.printStackTrace();
            log.error("保存码表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("保存码表，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "delete")
    @ApiOperation("删除码表")
    public Result delete(@RequestParam(value = "ids") List<Long> ids){
        log.info("删除码表，请求参数:{}",ids.toString());
        Result result = new Result();
        try {
            dictService.delete(ids);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除码表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("删除码表，响应参数:{}",result.toString());
        return result;
    }


    @GetMapping(value = "getByType")
    @ApiOperation("根据类型查询码表:测试板类型type=board_type")
    public Result<List<Dict>> getByType(String type){
        log.info("根据类型查询码表，请求参数:{}",type);
        Result result = new Result();
        try {
            List<Dict> list = dictService.selectByType(type);
            result = result.success(list);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据类型查询码表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("根据类型查询码表，响应参数:{}",result.toString());
        return result;
    }
}
