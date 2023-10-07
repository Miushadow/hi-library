package com.imooc.hilibrary.log;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * HiLog的门面，提供了不同日志级别的打印接口。
 * 通过对HiLogConfig进行配置，能够实现以下需求：
 * 1.能够打印堆栈/线程信息
 * 2.支持任何数据类型的打印（Android的Log系统只支持String类型的打印）
 * 3.支持日志可视化
 * 4.能够实现文件打印
 * 5.支持不同打印器的插拔
 */
public class HiLog {

    private static final String HI_LOG_IGNORE_PACKAGE;

    /*
    忽略掉HiLog系统本身的打印，因为查看打印时，需要关注的是调用方法的打印，无需关注Log系统自身打印
    public String substring(int beginIndex, int endIndex)
     */
    static {
        String className = HiLog.class.getName();
        HI_LOG_IGNORE_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    //传入可变参数
    //可变参数：方法的参数个数或类型未知时，称其为可变参数列表。
    //由于所有的类都是直接或间接继承于Object类，可以理解为Object的数组
    public static void v(Object... contents) {
        log(HiLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(HiLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(HiLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(HiLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(HiLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(HiLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(HiLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(HiLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(HiLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(HiLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(HiLogType.A, tag, contents);
    }


    public static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NonNull HiLogConfig config, @HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        //通过config来判断，打印是否添加线程信息
        if (config.includeThread()) {
            String threadInfo = HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        //通过config来判断，打印是否添加堆栈信息
        if (config.stackTraceDepth() > 0) {
            String stackTrace = HiLogConfig.HI_STACK_FORMATTER.format(HiStackTraceUtil.getCroppedRealStackTrace(new Throwable().getStackTrace(), HI_LOG_IGNORE_PACKAGE, config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }
        //这里传入内容以及config对象
        String body = parseBody(config, contents);
        sb.append(body);
        //从config中获取打印器，如果config中的打印器不为空，则直接获取
        //如果等于空，则从manager中获取
        List<HiLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : HiLogManager.getInstance().getPrinters();
        //如果打印器为空，直接返回
        if (printers == null) {
            return;
        }
        //打印器不为空，则遍历打印器直接打印
        for (HiLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    //添加一个额外的入参，HiLogConfig
    private static String parseBody(@NonNull HiLogConfig config, @NonNull Object... contents) {
        //判断配置文件中，json注入是否为空
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


}
