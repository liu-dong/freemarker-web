package com.dong.freemarkerweb.model;

/**
 * @author LD
 * @date 2020/6/17 16:40
 */
public class Attribute {

    private String columnName;//列名称
    private String typeName;//类型名称
    private String remark;//注释

    public Attribute(String columnName, String typeName, String remark) {
        this.columnName = columnName;
        this.typeName = typeName;
        this.remark = remark;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
