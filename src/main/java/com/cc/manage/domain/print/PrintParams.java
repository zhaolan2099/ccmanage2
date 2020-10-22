package com.cc.manage.domain.print;

import lombok.Data;

import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/10/13 17:24
 */
@Data
public class PrintParams {

    private String barcodeMsg;
    private String qrcodeMsg;
    /** 设定标签宽度，单位 mm */
    private String setupWidth;
    /** 设定标签高度，单位 mm */
    private String setupHeight;
    List<BarcodeParams> barcodeParamsList;
    List<QrcodeParams> qrcodeParamsList;
}
