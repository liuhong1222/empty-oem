package com.cc.oem.modules.config.utils;

import java.math.BigDecimal;

public class PriceUtils {

    /**
     * 将厘转换为元，并带有2位小数，默认0.00，指定参数abs来决定是否求绝对值
     * @param priceLi   分
     * @return
     */
    public static String convertToYuan(Integer priceLi){
        String result="0.00";
        if(priceLi!=null){
            String temp=new BigDecimal(priceLi).divide(new BigDecimal(1000)).doubleValue()+"";
            if(temp.endsWith(".0")){
                temp=temp.substring(0,temp.indexOf("."));
            }
            result=temp;
        }
        return result;
    }
}
