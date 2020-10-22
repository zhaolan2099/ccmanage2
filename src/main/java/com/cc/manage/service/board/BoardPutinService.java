package com.cc.manage.service.board;

import com.cc.manage.domain.board.Board;
import com.cc.manage.exception.BizException;

import java.util.List;
import java.util.Map;

public interface BoardPutinService {
    /**
     * 确认入库将所有入库中的电路板打包成一个，并修改状态为已入库
     * @param sns))
     */
    Map<String,String> doPutin(List<String> sns) throws BizException;

    /**
     *  通过扫描SN号，将测试完成的电路板修改为入库中
     * @param sn
     */
    Board getForPutIn(String sn)throws BizException;

    /**
     * 取消出库
     * @param sn
     */
    void cancelPutin(List<String> sn);
}
