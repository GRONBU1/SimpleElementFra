package cn.xiongdi.ocdm.mapper.model;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 描述：
 *
 * @Author NEC  2018/11/8
 */
public class Chart implements Serializable {

    private String name;

    private String[] lineName;

    private String[] xdata;

    private JSONObject[] series;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getLineName() {
        return lineName;
    }

    public void setLineName(String[] lineName) {
        this.lineName = lineName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getXdata() {
        return xdata;
    }

    public void setXdata(String[] xdata) {
        this.xdata = xdata;
    }

    public JSONObject[] getSeries() {
        return series;
    }

    public void setSeries(JSONObject[] series) {
        this.series = series;
    }
}
