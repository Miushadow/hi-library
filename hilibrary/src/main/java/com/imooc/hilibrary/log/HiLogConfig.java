package com.imooc.hilibrary.log;

/**
 * HiLog日志库的配置类，负责HiLog打印的相关配置，具有以下作用：
 * #1.是否启用配置
 * #2.对打印内容是否包含线程信息进行配置
 * #3.对打印内容是否包含堆栈信息，以及堆栈信息的深度进行配置（深度为0表示不包含堆栈信息）
 * #4.对全局的TAG进行配置
 * #5.序列化服务
 */
public abstract class HiLogConfig {

    /**
     * 日志格式化时每一行所显示的最大长度，由于两个日志格式化器会被多次使用，所以这里使用饿汉的方式来创建格式化器的单例
     */
    static int MAX_LEN = 512;
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();
    static HiStackTraceFormatter HI_STACK_FORMATTER = new HiStackTraceFormatter();

    /**
     * HiLog打印的开关，默认打开
     */
    public boolean enable() {
        return true;
    }

    /**
     * 日志中是否包含线程信息，默认为false。
     * 如果包含线程信息，则会在打印时通过调用HiLogConfig.HI_THREAD_FORMATTER.format进行打印
     */
    public boolean includeThread() {
        return false;
    }

    /**
       堆栈信息的深度，
       初始定位5，筛选出前几条最关键的堆栈信息
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 全局TAG的设置
     */
    public String getGlobalTag() {
        return "HiLog";
    }

    /**
     *  允许用户注册打印器
     */
    public HiLogPrinter[] printers() {
        return null;
    }

    /**
     * Json序列化器的注入
     */
    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * 提供对象序列化的接口
     */
    public interface JsonParser {
        String toJson(Object src);
    }
}
