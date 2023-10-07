package com.imooc.hilibrary.log;

public interface HiLogFormatter<T> {

    String format(T data);
}
