package com.imooc.hilibrary.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Mo是Model的缩写，是MVC/MVP/MVVM等设计模式中的数据层，用于向视图提供数据。
 * 一般用于网络数据操作、file文件操作、本地数据库操作等。
 */
class HiViewPrinterMo {

    /**
     * 1.通过static修饰的类变量通常有以下三种使用场景：
     * #1.作为共享变量使用
     * #2.减少对象的创建
     * #3.保留唯一副本
     * 此处由于HiViewPrinterMo这个类会被多次实例化，而里面的simpleDateFormat变量又只有一个作用，就是进行日期格式化
     * 所以这里用static来修饰它，减少该对象的创建，避免造成资源浪费
     *
     * 2.SimpleDateFormat:该类用来对日期字符串进行解析和格式化输出，如通过如下的这种方式，传入当前系统时间后，输出的一个
     * 时间格式为：2022-03-22 22:34:46
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public HiViewPrinterMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    /**
     * 组装可视化的日志
     * 在使用Log.d打印日志时，控制台会自动将时间戳，tag，log level等信息拼接到最终输出的字符串中，而在我们自定义的
     * 打印器中，如果也想输出类似格式的日志，就需要手动进行拼接
     */
    public String assembleVisualLog() {
        return simpleDateFormat.format(timeMillis) + '|' + "loglevel:" + level + '|' + tag + '|' + "\n" + log;
    }
}
