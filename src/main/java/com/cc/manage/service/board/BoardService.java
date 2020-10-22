package com.cc.manage.service.board;

import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardForOutExport;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.board.BoardQuery;

import java.util.List;
import java.util.Map;

/**
 * @author ：
 * @time ：Created in 2020/9/3 11:16
 */
public interface BoardService {

    void  delAll();
    /**
     * 根据mac地址是否存在判断新增还是修改
     * @param board
     */
    void save(Board board) throws BizException;
    void updateByTest(Board board) throws BizException;
    int selectTotal(BoardQuery query) throws BizException;
    List<Board> selectList(BoardQuery query) throws BizException;
    List<BoardForOutExport> selectListForOutExport(String outNum);
    PageVo<Board> selectListForPage(BoardQuery query) throws BizException;
    /**
     * 修改出库状态为出库中
     * @param sn
     * @throws BizException
     */
    void outIng(String sn) throws BizException;
    /**
     * 根据SN号出库
     * @param sns
     * @throws BizException
     */
    Map<String,String>  doOut(List<String> sns) throws BizException;


    /**
     * 取消出库
     * @param sn
     */
    void cancelOut(List<String> sn);



    Board getBySn(String sn);

    /**
     * 根据SN号获取电路板对象，用于出库
     * @param sn
     * @return
     * @throws BizException
     */
    Board getBySnForOut(String sn)throws BizException;

    /**
     * 每个月从1开始 每类板子序号连续
     * @param board
     * @return
     * @throws BizException
     */
    Map<String,String> buildSn(Board board) throws BizException;

    /**
     * 厂家编码+年（后两位）+月+批次（两位）
     * @param orgNum
     * @return
     * @throws BizException
     */
    Map<String,String> buildOutNum(String orgNum);

    void updateBySn(Board board);


    List<Board> beginOut() throws BizException;

}
