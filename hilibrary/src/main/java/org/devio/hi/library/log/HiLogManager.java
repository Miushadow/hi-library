package com.imooc.hilibrary.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * HiLog日志库的管理类，在整个HiLog库中以单例的形式存在，具有以下三个作用：
 * #1.对日志格式的配置进行管理（设置/获取config）
 * #2.管理日志打印终端的printer（添加/删除printer）
 * #3.日志服务的初始化工作
 */
public class HiLogManager {
    private HiLogConfig config;
    private static HiLogManager instance;
    //创建数组，来保存所有打印器
    private List<HiLogPrinter> printers = new ArrayList<>();

    private HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        //将传进来的printers数组，转为list，并添加到printers这个ArrayList中
        //ArrayList的实现就是基于数组，可以将它想象成可以动态扩容的数组
        //可通过Arrays.asList将数组转为ArrayList
        this.printers.addAll(Arrays.asList(printers));
    }

    public static HiLogManager getInstance() {
        return instance;
    }

    //添加可变类型参数
    public static void init(@NonNull HiLogConfig config, HiLogPrinter... printers) {
        instance = new HiLogManager(config, printers);
    }

    public HiLogConfig getConfig() {
        return config;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinters(HiLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }
}
