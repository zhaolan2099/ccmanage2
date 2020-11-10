package com.cc.manage.service.board;

import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardOuting;
import com.cc.manage.exception.BizException;

import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/10/29 16:32
 */
public interface BoardOutSerice{

    /**
     * 出库中
     * @param putinNum
     */
    BoardOuting outIng(String putinNum)throws BizException;
    List<Board> outIng2(String putinNum)throws BizException;
    /**
     * 通过入库打包码出库
     * @param putinNums
     */
    void doOut(List<String> putinNums) throws BizException;



    /**
     * 出库中-取消出库
     * @param putinNums
     */
    void cancelOut(List<String> putinNums);


    void cancel(List<String> sns);

    List<BoardOuting> beginOut() throws BizException;

}
