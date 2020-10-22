package com.cc.manage.dao.board;


import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardForOutExport;
import com.cc.manage.query.board.BoardQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    void  delAll();
    int deleteByPrimaryKey(Long id);

    int insert(Board record);

    int insertSelective(Board record);

    Board selectByPrimaryKey(Long id);

    int update(Board record);
    int updateBySn(Board record);

    Board get(Board record);

    int selectTotal(BoardQuery query);
    List<Board> selectList(BoardQuery query);

    /**
     * 修改出库状态为
     * @param board
     */
    void updateOut(Board board);
    void updatePutIn(Board board);

    /**
     * 取消出库
     * @param sn
     */
    void cancelOut(String sn);
    void cancelPutin(String sn);
    Board getBySn(String sn);


    List<BoardForOutExport> selectListForOutExport(String outNum);

    List<Board> getOutingByOrgId(Long orgId);
}