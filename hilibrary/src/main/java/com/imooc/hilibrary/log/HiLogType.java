package com.imooc.hilibrary.log;

import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * HiLog的日志类型，一共六种
 * 由于枚举消耗内存较多，此处出于性能优化考虑，使用@IntDef来替代枚举，是一种轻量级的枚举实现
 */
public class HiLogType {

    //步骤1：定义静态常量
    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
    public final static int A = Log.ASSERT;

    //步骤2：声明@IntDef注解，该注解修饰的整型表示一个逻辑上的类型，即括号中包含的这些数值被看成是一个类型，后续会为该类型声明名称
    @IntDef({V, D, I, W, E, A})
    //步骤3：定义注解保留时期，为源码级别
    @Retention(RetentionPolicy.SOURCE)
    //步骤4：声明之前定义的整数类型的名称
    public @interface TYPE{}
}
