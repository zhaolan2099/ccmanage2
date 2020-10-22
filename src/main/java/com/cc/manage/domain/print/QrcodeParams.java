package com.cc.manage.domain.print;

import lombok.Data;

/**
 * @author ：
 * @time ：Created in 2020/10/13 17:43
 */
@Data
public class QrcodeParams {
    /** 条形码X 方向起始点，以点(point)表示。 (200 DPI，1 点=1/8 mm, 300 DPI，1 点=1/12 mm) */
    private String x;
    /** 条形码Y 方向起始点，以点(point)表示。 (200 DPI，1 点=1/8 mm, 300 DPI，1 点=1/12 mm) */
    private String y;
    /** 宽度 */
    private String cellWidth;
}
