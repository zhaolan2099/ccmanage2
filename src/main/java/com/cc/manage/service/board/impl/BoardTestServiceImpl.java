package com.cc.manage.service.board.impl;

import com.cc.manage.common.Constant;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.board.BoardTestService;
import com.cc.manage.utils.DateUtil;
import com.cc.manage.utils.RedisUtil;
import com.cc.manage.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：
 * @time ：Created in 2020/10/29 11:35
 */
@Service
public class BoardTestServiceImpl implements BoardTestService {
    @Resource
    BoardMapper boardMapper;
    @Override
    public void cancel(List<Long> ids) {
        ids.forEach(o->{
            Board board = new Board();
            board.setId(o);
            board.setTestStatus(Constant.STATUS_1);
            boardMapper.update(board);
        });
    }

    @Override
    public Map<String, String> buildSn(Board board) throws BizException {
        Map<String,String> map = new HashMap<>();
        NumberFormat f = new DecimalFormat("00000");
        LoginUser user = UserUtil.getCurrentUser();
        String orgNum = user.getOrgNum();
        String date = DateUtil.formatDate(new Date(),"yyMM");
        String key = Constant.REDIS_KEY_SN+date+board.getBoardType();
        Long value = Long.parseLong(StringUtils.isEmpty(RedisUtil.get(key))?"0":RedisUtil.get(key));
        value = value + 1;
        String sn = board.getBoardType()+orgNum+date+ f.format(value);
        board.setSn(sn);
        map.put("key",key);
        map.put("value",String.valueOf(value));
        return map;
    }


}
