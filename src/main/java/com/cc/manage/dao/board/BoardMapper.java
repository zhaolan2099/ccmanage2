package com.cc.manage.dao.board;


import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardForOutExport;
import com.cc.manage.domain.board.BoardOuting;
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
    int updateByPutinNum(Board record);
    int updateByOutNum(Board record);
    Board get(Board record);
    List<Board> selectBatchForSn(List<String> sns);
    int selectTotal(BoardQuery query);
    List<Board> selectList(BoardQuery query);

    /**
     * 修改出库状态为
     * @param board
     */
    void updateOut(Board board);

    /**
     * 入库
     * @param board
     */
    void putIn(Board board);


    void cancelPutin(String sn);
    Board getBySn(String sn);
    Board getBySnAndOrgId(@Param("sn") String sn,@Param("orgId")Long orgId);

    List<Board> getByPutinNum(String putinNum);
    List<Board> getByPutinNumAndOrgId(@Param("putinNum")String putinNum,@Param("orgId")Long orgId);

    /**
     * 出库中
     * @param putinNum
     */
    void outIng(@Param("putinNum") String putinNum);
    /**
     * 取消出库
     * @param putinNum
     */
    void cancelOut(@Param("putinNum") String putinNum);

    List<BoardForOutExport> selectListForOutExport(String outNum);

    List<BoardOuting> getOutingByOrgId(Long orgId);
    List<Board> getPutinByOrgId(Long orgId);

    /**
     * 已入库，回到上一步  SN号写入完成
     * @param sns
     * @param status
     */
    void  lastStepForPutin(@Param("sns") List<String> sns,@Param("status") String status);
    List<String>  lastStepForPutOut1(@Param("sns") List<String> sns);
    int  lastStepForPutOut2(@Param("sns") List<String> sns,@Param("status") String status);

}