package com.fri01.xiaozhi.common.database;

/**
 * <p>
 *     Attribute enum of table `map`.
 * </p>
 *
 * @author forever518
 * @since 2021-05-04
 */
public enum TableMapColumn {
    NAME("Map_name"),
    PGM_PATH("Map_path"),
    PNG_PATH("Map_show_path"),
    INDEX_PATH("Index_path");

    private String columnName;

    TableMapColumn(String columnName) {
        this.columnName = columnName;
    }

    public String value() {
        return this.columnName;
    }
}
