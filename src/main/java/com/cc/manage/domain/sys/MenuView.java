package com.cc.manage.domain.sys;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/9/23 17:23
 */
@Data
public class MenuView  implements Serializable {
    private String path;
    private String component;
    private JSONObject meta;
    private String name;
    List<MenuView> children;
    private static final long serialVersionUID = 1L;
}
