package com.cc.manage.controller.sys;
import com.alibaba.fastjson.JSON;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.Org;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.OrgQuery;
import com.cc.manage.service.sys.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 16:17
 * @description：
 */
@Api(tags = "厂商管理")
@Slf4j
@RestController
@RequestMapping("org")
public class OrgController {

    @Resource
    OrgService orgService;

    @GetMapping(value = "selectList")
    @ApiOperation("查询厂商列表，带分页")
    public Result<PageVo<Org>> selectList(OrgQuery query){
        log.info("查询厂商列表，请求参数:{}",query.toString());
        query.setPageHiden(false);
        Result<PageVo<Org>> result = new Result();
        try {
            PageVo<Org> pageVo = orgService.selectForPage(query);
            result = result.success(pageVo);
        }catch (BizException e){
            log.error("查询厂商列表，业务异常:{}", e.getCodeMsg(), e);
            result = result.fail(e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询厂商列表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("查询厂商列表，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "selectAll")
    @ApiOperation("查询所有厂商用于搜索条件")
    public Result<List<Org>> selectAll(){
        log.info("查询所有厂商");
        OrgQuery query = new OrgQuery();
        query.setPageHiden(true);
        Result<List<Org>> result = new Result();
        try {
            List<Org>  list = orgService.selectList(query);
            result = result.success(list);
        }catch (BizException e){
            log.error("查询所有厂商，业务异常:{}", e.getCodeMsg(), e);
            result = result.fail(e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询所有厂商，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("查询所有厂商，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "save")
    @ApiOperation("保存厂商，新增/修改")
    public Result save(Org org){
        log.info("保存厂商，请求参数:{}",org.toString());
        Result result = new Result();
        try {
            orgService.save(org);
            result = result.success();
        }catch (BizException e){
            log.error("保存厂商，业务异常:{}", e.getCodeMsg(), e);
            result = result.fail(e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存厂商，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("保存厂商，响应参数:{}",result.toString());
        return result;
    }

//    @PostMapping(value = "save")
//    @ApiOperation("保存厂商，新增/修改")
//    public Result saveTest(@RequestBody String orgString){
//        log.info("保存厂商，请求参数:{}",orgString);
//        orgString = orgString.replace(" "," ");
//        Result result = new Result();
//        try {
//            orgService.save(JSON.toJavaObject(JSON.parseObject(orgString),Org.class));
//            result = result.success();
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("保存厂商，系统异常:{}", e.getMessage(), e);
//            result = result.fail(CodeMsg.SERVER_ERROR);
//        }
//        log.info("保存厂商，响应参数:{}",result.toString());
//        return result;
//    }


    @GetMapping(value = "delete")
    @ApiOperation("删除厂商")
    public Result delete(@RequestParam(value = "ids") List<Long> ids){
        log.info("删除厂商，请求参数:{}",ids.toString());
        Result result = new Result();
        try {
            orgService.delete(ids);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除厂商，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("删除厂商，响应参数:{}",result.toString());
        return result;
    }
}
