package com.zhp.common.model;

/**
 * Created by zhouhh2 on 2016/7/21.
 */
public class CommonKvDTO {
    private String name;
    private Long value;
    private String strValue;

    public CommonKvDTO(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public CommonKvDTO() {
    }

    public CommonKvDTO(String name, String strValue) {
        this.name = name;
        this.strValue = strValue;
    }

    public CommonKvDTO(String name, Long value, String strValue) {
        this.name = name;
        this.value = value;
        this.strValue = strValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }
}
