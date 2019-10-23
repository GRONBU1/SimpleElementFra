package cn.xiongdi.ocdm.common.exception;

/**
 * @创建人 汪礼
 * @创建时间 2018-09-12
 * @描述
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MSG = "系统异常，请稍后重试";

    public BizException() {
        super("系统异常，请稍后重试");
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

