package com.imooc.hilibrary.log;

/**
 * 堆栈信息格式化器
 */
public class HiStackTraceFormatter implements HiLogFormatter<StackTraceElement[]> {

    @Override
    public String format(StackTraceElement[] stackTrace) {
        //将StringBuilder的capacity属性设置为128，可以避免频繁申请内存。
        //capacity默认值为16，如果超出这个值，就会去申请内存，128为经验值
        StringBuilder sb = new StringBuilder(128);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t─ " + stackTrace[0].toString();
        } else {
            for (int i = 0; i < stackTrace.length; i++) {
                if (i == 0) {
                    sb.append("stackTrace:  \n");
                } else if (i != stackTrace.length - 1) {
                    sb.append("\t├ ");
                    sb.append(stackTrace[i].toString());
                    sb.append("\n");
                } else {
                    sb.append("\t└ ");
                    sb.append(stackTrace[i].toString());
                }
            }
        }
        return sb.toString();
    }
}
