package com.imooc.hilibrary.log;

import androidx.annotation.NonNull;

/**
 * HiLog的日志打印接口，基于该接口，可以自定义日志打印方式
 * 在打印日志时，必须要指定一个实现了该接口的打印器，并重写其中的print方法，来指定日志的打印输出方式。打印日志时，可以
 * 指定不止一种打印器。
 * 本项目中包含以下三种打印器：
 * #1.HiConsolePrinter:在terminal控制台打印，Application中配置的默认打印方式
 * #2.HiViewPrinter:在APP界面直接以视图的形式进行日志可视化打印
 * #3.HiFilePrinter:将日志打印保存在指定的文件中
 */
public interface HiLogPrinter {

    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString);
}
