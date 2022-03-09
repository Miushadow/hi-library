package com.imooc.hilibrary.log;

import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class HiLogType {
    //定义注解可接受的类型
    @IntDef({V, D, I, W, E, A})
    //定义注解保留时期，为源码级别
    @Retention(RetentionPolicy.SOURCE)
    //定义注解
    public @interface TYPE{}

    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
    public final static int A = Log.ASSERT;


}
