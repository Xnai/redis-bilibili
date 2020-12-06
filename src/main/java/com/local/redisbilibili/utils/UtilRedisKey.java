package com.local.redisbilibili.utils;

/**
 * redis key设计
 */
public class UtilRedisKey {

    /**
     * redis的key
     * 形式为
     * 表名：主键名：主键值：列名
     * @param tableName
     * @param majorKey
     * @param majorKeyValue
     * @param colunm
     * @return
     */
    public static String getKeyWithColumns(String tableName, String majorKey, String majorKeyValue, String colunm) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tableName).append(":");
        buffer.append(majorKey).append(":");
        buffer.append(majorKeyValue).append(":");
        buffer.append(colunm);
        return buffer.toString();
    }

    /**
     * redis的key
     * 形式为
     * 表名：主键名：主键值
     * @param tableName
     * @param majorKey
     * @param majorKeyValue
     * @return
     */
    public static String getKey(String tableName, String majorKey, String majorKeyValue) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tableName).append(":");
        buffer.append(majorKey).append(":");
        buffer.append(majorKeyValue).append(":");
        return buffer.toString();
    }

}
