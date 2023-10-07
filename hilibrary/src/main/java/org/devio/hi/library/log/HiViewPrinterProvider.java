package com.imooc.hilibrary.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.imooc.hilibrary.util.HiDisplayUtil;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 对HiViewPrinter打印控制台进行相关的控制和隐藏操作
 */
public class HiViewPrinterProvider {

    private FrameLayout rootView;
    private FrameLayout logView;
    private RecyclerView recyclerView;
    private View floatingButton;
    private boolean isOpen;

    public HiViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    private static final String TAG_FLOATING_BUTTON = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    /**
     * 展示控制logView开关的悬浮按钮
     */
    public void showFloatingButton() {
        //如果发现带有TAG_FLOATING_BUTTON的view已经在rootView中存在了，说明没必要创建了，直接返回
        if (rootView.findViewWithTag(TAG_FLOATING_BUTTON) != null) {
            return;
        }
        /* 1.LayoutParams是ViewGroup的一个静态内部类，是View用来告诉它的父控件如何放置自己的。一般用于动态布局时，
           通过代码来设置该布局的一些参数，比如宽高等
           2.FrameLayout是Android几大布局中最简单的一个布局，在该布局中，整个界面被当成一块空白备用区域，所有的子
           元素都统统位于该区域的左上角，并且后面的子元素直接覆盖在前面的子元素之上，将前面的子元素遮挡。相同层级布局中，
           FrameLayout的效率是最高的，占用内存相对也是最小的。
         */
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置其位置为右下角
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingButton = genFloatingButton();
        floatingButton.setTag(TAG_FLOATING_BUTTON);
        floatingButton.setBackgroundColor(Color.BLACK);
        //设置透明度
        floatingButton.setAlpha(0.8f);
        //按以上配置参数，将floatingButton这个View添加到rootView里面
        rootView.addView(floatingButton, params);
    }

    /**
     * 创建悬浮窗按钮，点击后会将LogView显示出来
     * @return
     */
    private View genFloatingButton() {
        if (floatingButton != null) {
            return floatingButton;
        }
        TextView textView = new TextView(rootView.getContext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    showLogView();
                }
            }
        });
        textView.setText("HiLog");
        floatingButton = textView;
        return floatingButton;
    }

    /**
     * 关闭悬浮窗按钮
     */
    public void closeFloatingButton() {
        if (floatingButton != null) {
            rootView.removeView(floatingButton);
        }
    }

    /**
     * 将展示可视化日志的LogView显示出来
     */
    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        /*
            设置LogView的宽为MATCH_PARENT，高为160dp，并将它放置在屏幕底部
         */
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(160, rootView.getResources()));
        params.gravity = Gravity.BOTTOM;
        View logView = genLogView();
        logView.setTag(TAG_LOG_VIEW);
        rootView.addView(logView, params);
        isOpen = true;
    }

    /**
     *  生成LogView，并对LogView里面显示的内容进行配置
     */
    private View genLogView() {
        if (logView != null) {
            return logView;
        }
        //将显示日志的recyclerView添加到logView里面去
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);

        /*
            这个LayoutParams是用来动态配置LogView右下角的close按钮，并将其添加到logView里面
         */
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(rootView.getContext());
        closeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                closeLogView();
            }
        });

        closeView.setText("Close");
        logView.addView(closeView, params);
        return this.logView = logView;
    }

    private void closeLogView() {
        if (logView != null) {
            isOpen = false;
            rootView.removeView(logView);
        }
    }


}
