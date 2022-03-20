package com.imooc.hilibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Mo是Model的缩写，是MVC/MVP/MVVM等设计模式中的数据层，用于向视图提供数据。
 * 一般用于网络数据操作、file文件操作、本地数据库操作等。
 */
class HiLogMo {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public HiLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    /**
     * 将时间戳拼装到日志中
     */
    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    /**
     *
     */
    public String getFlattened() {
        return format(timeMillis) + '|' + level + '|' + tag + '|';
    }

    private String format(long timeMillis) {
        return simpleDateFormat.format(timeMillis);
    }
}
