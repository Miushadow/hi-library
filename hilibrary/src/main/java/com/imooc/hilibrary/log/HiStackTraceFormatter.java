package com.imooc.hilibrary.log;

/**
 * 堆栈信息格式化器
 *
 * StackTraceElement表示StackTrace中的一个元素，该元素中包含了方法调用者的类名、方法名、文件名以及调用行数等属性。
 *
 * 获取StackTraceElement的方法有两种，均返回StackTraceElement[]数组：
 * #1.Thread.currentThread().getStackTrace()
 * #2.new Throwable().getStackTrace()
 * StackTraceElement数组包含了StackTrace的内容，通过遍历它并使用toString()方式进行打印，可以得到方法之间的调用过程
 */
public class HiStackTraceFormatter implements HiLogFormatter<StackTraceElement[]> {

    @Override
    public String format(StackTraceElement[] stackTrace) {
        /*
        StringBuilder用于字符串的拼接
        日常的开发中，为了写法简便，我们经常会使用"+"来拼接字符串。当每一次使用"+"时，JVM都会隐式创建一个StringBuilder对象，
        并通过该对象来实现字符串的拼接。这样做的缺点是当存在大量字符串拼接的操作时，会在堆内存里创建大量的StringBuilder对象，
        从而造成效率的损失。这种情况下，建议在循环体之外创建一个StringBuilder对象，调用append方法进行手动拼接，相较循环使用
        "+"效率大大提升。

        StringBuilder的capacity默认值为16，如果超出这个值，就会去申请内存，所以如果拼接操作较为复杂，可以将该属性值设置为128，
        避免频繁申请内存。
         */
        StringBuilder sb = new StringBuilder(128);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            /*
            如果StackTraceElement[]的长度为1，则直接在前面加一个横向制表符后打印
            横向制表符"\t"的意思是补全当前字符串长度到8的整数倍，补多少要看当前字符串的长度。比如当前字符串长度为
            10，那么加入"\t"后会将字符串长度补到16，也就是补6个空格；如果字符串长度是6，那么则是将字符串长度补到8，也
            就是补两个空格。
             */
            return "\t─ " + stackTrace[0].toString();
        } else {
            /*
            对StackTraceElement[]数组的打印进行处理：
            #1.对开头进行处理，打一句stackTrace:作为标题
            #2.对中间部分的具体StackTrace信息进行处理，每次以"├"开头，然后打印具体堆栈信息
            #3.对结尾进行处理，以符号"└"结尾
             */
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
