package cn.xiongdi.ocdm.common.exception;

/**
 * @创建人 汪礼
 * @创建时间 2018-09-12
 * @描述
 */
public class UnLoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnLoginException() {
    }

    public UnLoginException(String message) {
        super(message);
    }

    public UnLoginException(Throwable cause) {
        super(cause);
    }

    public UnLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
