package pt.zenit.ztack.builder.excel.model;

import com.ebay.xcelite.annotations.Column;
import pt.zenit.ztack.builder.constants.Constants;

/**
 * Classe que representa uma lista de valores com valores em pt/en
 */
public class DefaultDropdown {

    public static final String GEN_MODE = Constants.DefaultValues.DEFAULT_GEN_MODE_LV.getValue();

    @Column(name= "tableName")
    private String tableName;

    @Column(name= "columnName")
    private String columnName;

    @Column(name= "ptColumnDescription")
    private String ptColumnDescription;

    @Column(name= "enColumnDescription")
    private String enColumnDescription;

    @Column(name= "startDate")
    private String startDate;

    @Column(name= "endDate")
    private String endDate;

    //region Getters and Setters
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPtColumnDescription() {
        return ptColumnDescription;
    }

    public void setPtColumnDescription(String ptColumnDescription) {
        this.ptColumnDescription = ptColumnDescription;
    }

    public String getEnColumnDescription() {
        return enColumnDescription;
    }

    public void setEnColumnDescription(String enColumnDescription) {
        this.enColumnDescription = enColumnDescription;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    //endregion

    @Override
    public String toString() {
        return "DefaultDropdown{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", ptColumnDescription='" + ptColumnDescription + '\'' +
                ", enColumnDescription='" + enColumnDescription + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
