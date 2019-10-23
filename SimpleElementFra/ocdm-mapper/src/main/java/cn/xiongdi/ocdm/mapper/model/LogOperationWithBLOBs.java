package cn.xiongdi.ocdm.mapper.model;

import java.io.Serializable;

public class LogOperationWithBLOBs extends LogOperation implements Serializable {
    private String czqsj;

    private String czhsj;

    private static final long serialVersionUID = 1L;

    public String getCzqsj() {
        return czqsj;
    }

    public void setCzqsj(String czqsj) {
        this.czqsj = czqsj == null ? null : czqsj.trim();
    }

    public String getCzhsj() {
        return czhsj;
    }

    public void setCzhsj(String czhsj) {
        this.czhsj = czhsj == null ? null : czhsj.trim();
    }
}