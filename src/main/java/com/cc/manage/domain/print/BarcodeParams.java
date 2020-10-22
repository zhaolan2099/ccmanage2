package com.cc.manage.domain.print;

import lombok.Data;

/**
 * @author ：
 * @time ：Created in 2020/10/13 17:44
 */
@Data
public class BarcodeParams {
    /** 条形码X 方向起始点，以点(point)表示。 (200 DPI，1 点=1/8 mm, 300 DPI，1 点=1/12 mm) */
    private String x;
    /** 条形码Y 方向起始点，以点(point)表示。 (200 DPI，1 点=1/8 mm, 300 DPI，1 点=1/12 mm) */
    private String y;
    /** 条码高度 (dot) */
    private String height;
    /** 窄条码比例因子 (dot) */
    private String narrow;
    /** 宽条码比例因子 (dot) */
    private String wide;
}
