package com.cc.oem.common.utils.message;

/**
 * @author wangqiang
 * @since 2018/6/30
 */
public class MessageException extends Exception {
    private static final long serialVersionUID = -8606389771252040292L;


    public MessageException(final Throwable cause) {
        super(cause);
    }


    public MessageException(final String message) {
        super(message);
    }


    public MessageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
