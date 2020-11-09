package com.cc.manage.service.board;

import com.cc.manage.common.Result;
import com.cc.manage.domain.board.Board;
import com.cc.manage.exception.BizException;

import java.util.List;
import java.util.Map;

public interface BoardPutinService {
    /**
     * 确认入库将所有入库中的电路板打包成一个，并修改状态为已入库
     * @param sns))
     */
    void doPutin(List<String> sns) throws BizException;


    /**
     * 检查打包板类型是否一致，不一致不允许打包
     * @param sns
     * @return
     * @throws BizException
     */
    Result checkType(List<String> sns);

    /**
     *  通过扫描SN号，将测试完成的电路板修改为入库中
     * @param sn
     */
    Board getForPutIn(String sn)throws BizException;

    /**
     * 入库中取消
     * @param sns
     */
    void cancelPutin(List<String> sns);

    /**
     * 入库完成取消 同一批次全部回到SN写入成功状态那步
     * @param sns
     */
    void cancel(List<String> sns);

    List<Board> beginPutin() throws BizException;
}
