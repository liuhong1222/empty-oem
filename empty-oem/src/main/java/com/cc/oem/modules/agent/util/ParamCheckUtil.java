package com.cc.oem.modules.agent.util;

import com.cc.oem.common.exception.RRException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName ParamCheckUtil 参考Assert
 * author   zhangx
 * date     2018/10/10 14:27
 * description
 */

public class ParamCheckUtil {

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new RRException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new RRException(message);
        }
    }

    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }


    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new RRException(message);
        }
    }

    public static void isNotEquals(String object1, String object2, String message) {
        if (object1 == null && object2 == null) {

        } else {
            if (object1 != null) {
                if(!object1.equals(object2)){
                    throw new RRException(message);
                }
            }else {
                if(!object2.equals(object1)){
                    throw new RRException(message);
                }
            }
        }
    }


    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }


    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }


}

