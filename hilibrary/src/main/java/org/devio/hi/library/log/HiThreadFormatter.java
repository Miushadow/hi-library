package com.imooc.hilibrary.log;

public class HiThreadFormatter implements HiLogFormatter<Thread> {

    @Override
    public String format(Thread data) {
        //重写接口方法format，传入Thread线程，将线程名打印出来
        return "Thread" + data.getName();
    }
}


