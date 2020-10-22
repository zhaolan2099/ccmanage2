package com.cc.manage.domain.board;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_sn_log
 * @author 
 */
@Data
public class SnLog implements Serializable {
    private Long id;

    private String mac;

    private String mcu;

    private String sn;

    /**
     * 1成功0失败
     */
    private Integer status;

    private Date ct;

    private static final long serialVersionUID = 1L;
}