package com.zhp.db.base;

/**
 * Created by zhouhh2 on 2016/8/10.
 */
public class Constants {
    public static final String defaultTx = "defaultTxMgr";
    public static final String defaultDs = "dataSource";
    public static final String flywayDs = "flywayDs";
    public static final String quartzDs = "quartzDs";

    public static final String PREFIX_DB = "datasource";
    public static final String flag_multipleDs = "datasource.multipleDs";
    public static final String flag_initMigrate = "datasource.initMigrate";

    public static final String[] READ_CHECK =
            {"select", "find", "query", "get", "init",
                    "count", "list", "search", "load"};

    public static final String PREFIX_COMMON = "datasource.common";
    public static final String PREFIX_MASTER = "datasource.master";
    public static final String PREFIX_CUSTOM = "datasource.custom";
    public static final String PREFIX_OTHERS = "datasource.others";

    public static final String DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
}
