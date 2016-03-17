package com.xd.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by hhhhwei on 16/3/17.
 */
public class CustomListView extends ListView implements AbsListView.OnScrollListener, View.OnTouchListener {

    private View footer;

    private boolean isLoading;
    private View headerView;

    public CustomListView(Context context) {
        this(context, null);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFootView(context);
        initHeaderView(context);
    }

    public void initFootView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        footer = layoutInflater.inflate(R.layout.footer, null);
        this.addFooterView(footer);
        footer.findViewById(R.id.ll_foot).setVisibility(INVISIBLE);

        this.setOnScrollListener(this);
        this.setOnTouchListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem == totalCount && scrollState == SCROLL_STATE_IDLE && !isLoading) {
            footer.findViewById(R.id.ll_foot).setVisibility(VISIBLE);
            loadingListener.load();
        }
    }

    //用于头布局
    private int lastVisibleItem = 0;
    private int totalCount = 0;

    //用于尾布局
    private int firstVisibleItem;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalCount = totalItemCount;
    }

    private LoadingListener loadingListener;

    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    interface LoadingListener {
        public void load();
    }

    public void compleLoading() {
        isLoading = false;
        footer.setVisibility(INVISIBLE);
    }

    //操作头布局
    public void initHeaderView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        headerView = layoutInflater.inflate(R.layout.header, null);
        this.addHeaderView(headerView);

        init();
    }

    private void init() {
        //让它强制调用measue,后面就可以使用getMeasuredHeight了。
        headerView.measure(0, 0);
        int measuredHeight = headerView.getMeasuredHeight();
        headerView.setPadding(headerView.getPaddingLeft(), -measuredHeight, getPaddingRight(),
                getPaddingBottom());
        headerView.postInvalidate();
    }

    private boolean isRemark;//标记当前是否是在listview的最顶端按下的
    private int startY;//按下时候的Y值

    //当按下时，并且根据firstVisibleItem发现它在最顶端，则需要添加标记并记录y值.
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }
        //可点击的都会返回true
        return super.onTouchEvent(event);
    }

    //判断一下移动过程中的操作
    public void onMove(MotionEvent event){

    }
}
