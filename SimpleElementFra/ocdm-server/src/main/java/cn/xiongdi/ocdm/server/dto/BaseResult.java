package cn.xiongdi.ocdm.server.dto;

import cn.xiongdi.ocdm.common.constants.Constant;


/**
 * @创建人 lzy
 * @创建时间 2018-09-11
 * @描述 结果集
 */
public class BaseResult {

    /**
     * 状态码：0成功，-1为失败
     */
    public int code;

    /**
     * 成功为success，其他为失败原因
     */
    public String message;

    /**
     * 数据结果集
     */
    public Object data;

    public BaseResult() {

        this.code = Constant.CODE_SUCCESS;
        this.message = Constant.FLAG_SUCCESS;
        this.data = null;
    }

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isOk() {
        return (this.code == Constant.CODE_SUCCESS);
    }

    public void setFailure() {
        this.code=Constant.CODE_FAILURE;
    }

    public void setSuccess() {
        this.code=Constant.CODE_SUCCESS;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
