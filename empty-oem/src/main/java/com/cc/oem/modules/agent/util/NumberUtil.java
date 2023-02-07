package com.cc.oem.modules.agent.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName NumberUtil
 * author   zhangx
 * date     2018/8/15 13:28
 * description
 */
public class NumberUtil {

    public static String splitAmountByComma(Object amount) {
        if (amount == null) {
            return "0";
        }
        NumberFormat nf = new DecimalFormat("#,###.####");
        String str = nf.format(amount);
        return str;
    }

//    public static void main(String[] args) {
//        System.out.println(splitAmountByComma(BigDecimal.valueOf(1234567890.1234789)));
//        System.out.println(splitAmountByComma(BigDecimal.valueOf(1234567890)));
//        System.out.println(splitAmountByComma(123445L));
//        System.out.println(splitAmountByComma(111111));
//        String[] strArray = {"A", "B"};
//        System.out.println(JSON.toJSONString(strArray));
//        System.out.println(JSON.toJSONString("AAAA"));
//        System.out.println(strArray.toString());

//    }
}
