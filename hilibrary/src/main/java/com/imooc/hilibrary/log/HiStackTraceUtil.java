package com.imooc.hilibrary.log;

public class HiStackTraceUtil {

    /**
     * 向外提供一个public方法，获取裁剪行数，并忽略掉无效包名后的真实有效信息
     */
    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage, int maxDepth) {
        return cropStackTrace(getValidStackTrace(stackTrace, ignorePackage), maxDepth);
    }

    /**
     * 根据传入的堆栈信息最大打印长度maxDepth，对堆栈信息进行裁剪
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        //获取堆栈信息的实际长度
        int realDepth = callStack.length;
        if (maxDepth > 0) {
            //从堆栈信息的实际长度realDepth和定义的最大长度maxDepth中取出最小值
            realDepth = Math.min(maxDepth, realDepth);
        }
        //创建一个存放最终要返回的StackTrace的数组
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        /*
        System.arraycopy函数：
        public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);

        从原数组callStack的第srcPos个元素开始复制，拷贝到目标数组realStack中(从第destPos个元素开始粘贴)，
        一共拷贝realDepth个元素
         */
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 获取除忽略package以外的有效堆栈信息，传入堆栈信息中要忽略的包名
     */
    private static StackTraceElement[] getValidStackTrace(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }



}
