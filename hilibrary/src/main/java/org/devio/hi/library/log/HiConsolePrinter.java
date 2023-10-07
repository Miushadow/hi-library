package com.imooc.hilibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;


/**
 * 控制台的打印器
 */
public class HiConsolePrinter implements HiLogPrinter {

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        //获取要打印的printString的长度
        int len = printString.length();
        //获取打印行数
        int countOfSub = len/HiLogConfig.MAX_LEN;
        if (countOfSub > 0) {
            StringBuilder log = new StringBuilder();
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + HiLogConfig.MAX_LEN));
                index += HiLogConfig.MAX_LEN;
            }
            //处理行数无法直接整除，则将剩余部分打印出来
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }
        } else {
            //Log.println：将日志输出到控制台的方法
            Log.println(level, tag, printString);
        }
    }
}
