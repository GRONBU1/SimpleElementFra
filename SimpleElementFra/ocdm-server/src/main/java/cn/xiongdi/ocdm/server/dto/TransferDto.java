package cn.xiongdi.ocdm.server.dto;

/**
 * @创建人 WD
 * @创建时间 2018/11/7 15:09
 * @描述 穿梭框所需特定参数
 */
public class TransferDto {
    private String key;
    private String label;

    private String order;
    private String name;
    private String address;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
