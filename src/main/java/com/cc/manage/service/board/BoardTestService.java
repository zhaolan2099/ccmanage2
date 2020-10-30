package com.cc.manage.service.board;

import com.cc.manage.domain.board.Board;
import com.cc.manage.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * @author ：
 * @time ：Created in 2020/10/29 11:34
 */
public interface BoardTestService {

    void cancel(List<Long> ids);

    /**
     * 每个月从1开始 每类板子序号连续
     * @param board
     * @return
     * @throws BizException
     */
    Map<String,String> buildSn(Board board) throws BizException;

}
