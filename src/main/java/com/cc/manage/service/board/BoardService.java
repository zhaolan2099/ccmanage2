package com.cc.manage.service.board;

import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardForOutExport;
import com.cc.manage.domain.board.BoardOuting;
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





    Board getBySn(String sn);




    /**
     * 厂家编码+年（后两位）+月+批次（两位）
     * @param orgNum
     * @return
     * @throws BizException
     */
    Map<String,String> buildOutNum(String orgNum);

    void updateBySn(Board board);




}
