package com.cc.oem.common.utils;

import java.math.BigDecimal;

/**
 * Description:货币单位转换
 * User: liutao
 * Date: 2018-03-21
 * Time: 13:12
 */
public class MoneyUnitUtil {

    /**
     * 将厘转为元
     */
    public static BigDecimal convertLi2Yuan(Long value){
        if(value==null){
            return null;
        }
        return new BigDecimal(value).divide(new BigDecimal(1000)).setScale(3, BigDecimal.ROUND_HALF_UP);
    }
    
    
    public static BigDecimal convertLi2Yuan(BigDecimal value){
        if(value==null){
            return null;
        }
        return value.divide(new BigDecimal(1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
