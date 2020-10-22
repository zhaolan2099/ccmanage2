package com.cc.manage.dao.board;


import com.cc.manage.domain.board.SnLog;

public interface SnLogMappr {
    int deleteByPrimaryKey(Long id);

    int insert(SnLog record);

    int insertSelective(SnLog record);

    SnLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SnLog record);

    int updateByPrimaryKey(SnLog record);

    SnLog get(SnLog snLog);
}