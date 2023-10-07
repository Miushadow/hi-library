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
     *
     * 如果要通过Gson来实现Json的序列化，则需要通过以下方式注入Gson
     */
    public JsonParser injectJsonParser() {
        /*return new JsonParser() {
            @Override
            public String toJson(Object src) {
                Gson gson = new Gson();
                return gson.toJson(src);
            }
        }*/
        return null;
    }

    /**
     * 序列化通用接口，让使用者的Json序列化工具（如fastjson/gson）可以做到与服务提供方进行解耦
     *
     * 内存中的数据对象只有转换成二进制流才可以进行数据持久化和网络传输，将Java对象转换成二进制流的过程称为对象的序列化，
     * 将二进制流恢复为数据对象的过程称为反序列化。
     *
     * 常见的序列化方式有以下两种：
     * #1.Java原生序列化：特点是兼容性好，但不支持跨语言，性能一版
     * #2.Json序列化：特点是可读性好，但是有安全性风险
     */
    public interface JsonParser {
        String toJson(Object src);
    }
}
