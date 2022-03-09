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
    private View floatingButton;
    private boolean isOpen;
    private FrameLayout logView;
    private RecyclerView recyclerView;

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
        //新建一个FrameLayout，为其设置属性，宽，高都是WRAP_CONTENT
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置其位置为右下角
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingButton = genFloatingButton();
        floatingButton.setTag(TAG_FLOATING_BUTTON);
        floatingButton.setBackgroundColor(Color.BLACK);
        //设置透明度
        floatingButton.setAlpha(0.8f);
        rootView.addView(floatingButton, params);
    }

    /**
     * 创建悬浮窗按钮
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
     * 展示显示Log的窗口
     */
    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(160, rootView.getResources()));
        params.gravity = Gravity.BOTTOM;
        View logView = genLogView();
        logView.setTag(TAG_LOG_VIEW);
        rootView.addView(logView, params);
        isOpen = true;
    }

    private View genLogView() {
        if (logView != null) {
            return logView;
        }
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);

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
