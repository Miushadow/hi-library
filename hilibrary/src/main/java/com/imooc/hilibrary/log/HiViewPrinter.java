package com.imooc.hilibrary.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.imooc.hilibrary.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * HiViewPrinter 将log显示在界面上
 */
public class HiViewPrinter implements HiLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private HiViewPrinterProvider viewProvider;
    private LayoutInflater inflater;

    public HiViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = initRecyclerView(activity);
        viewProvider = new HiViewPrinterProvider(rootView, recyclerView);
    }

    /**
     * RecyclerView进行初始化主要分为以下两个步骤：
     * 1.为RecyclerView设置布局管理器LayoutManager
     * 2.为RecyclerView设置自定义的适配器Adapter
     *
     * 备注1：RecyclerView提供了三种布局管理器：
     * #1.LinearLayoutManager:以垂直或水平列表的方式展示Item
     * #2.GridLayoutManager:以网格方式展示Item
     * #3.StaggeredGridLayoutManager:以瀑布流的方式展示Item
     *
     * 备注2：获取LayoutInflater实例有3种方式
     * #1.调用Activity的getLayoutInflater方法
     * #2.LayoutInflater inflater = LayoutInflater.from(context)
     * #3.LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
     */
    private RecyclerView initRecyclerView(Activity activity) {
        recyclerView = new RecyclerView(activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        inflater = activity.getLayoutInflater();
        adapter = new LogAdapter(inflater);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    /**
     * 获取ViewProvider，通过ViewProvider可以控制log视图的展示和隐藏
     */
    @NonNull
    public HiViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        //将log展示添加到recyclerView
        adapter.addItem(new HiViewPrinterMo(System.currentTimeMillis(), level, tag, printString));
        //滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    /**
     * ViewHolder不是Android标准API，是一种设计方式，通常搭配适配器使用。
     * 它的作用是在listview/recyclerview滚动的时候快速设置值，而不用每次都去执行findViewById的操作，从而提升了性能。
     */
    private static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tagView;
        TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }

    /**
     * 继承自RecyclerView.Adapter类，主要用来将数据和布局item进行绑定
     * 在Adapter中需要实现以下三个方法
     * #1.onCreateViewHolder
     * #2.onBindViewHolder
     * #3.getItemCount
     */
    private class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater inflater;
        //新建一个List，用于存储每个Item的数据层Mo对象
        private List<HiViewPrinterMo> logItemList = new ArrayList<>();

        void addItem(HiViewPrinterMo logItem) {
            logItemList.add(logItem);
            //插入指定位置的item，并刷新
            notifyItemInserted(logItemList.size() - 1);
        }

        /**
         * 构造方法中需要将LayoutInflater传进来
         */
        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        /**
         * LayoutInflater.inflate方法的作用是将一个用xml定义的布局文件转换成view对象。
         * onCreateViewHolder最终会创建一个我们自定义的ViewHolder对象，并将其关联的itemView对象传进去
         */
        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.hilog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        /**
         * 该方法的作用是将适配的数据渲染到View中
         */
        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiViewPrinterMo logItem = logItemList.get(position);
            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);
            holder.tagView.setText(logItem.assembleVisualLog());
            holder.messageView.setText(logItem.log);
        }

        /**
         * 返回Item的数量
         */
        @Override
        public int getItemCount() {
            return logItemList.size();
        }

        /**
         * 根据log级别获取不同的高亮颜色
         * @param logLevel log级别
         * @return 高亮的颜色
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case HiLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case HiLogType.D:
                    highlight = 0xffffffff;
                    break;
                case HiLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case HiLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case HiLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }
    }

}
