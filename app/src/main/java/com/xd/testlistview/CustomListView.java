package com.xd.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by hhhhwei on 16/3/17.
 */
public class CustomListView extends ListView implements AbsListView.OnScrollListener {

    private View footer;

    private boolean isLoading;
//    private View headLayout;
//    private int headerViewHeight;
//    private View headerView;

    public CustomListView(Context context) {
        this(context, null);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFootView(context);
//        initHeaderView(context);
    }

    public void initFootView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        footer = layoutInflater.inflate(R.layout.footer, null);
        this.addFooterView(footer);
        footer.findViewById(R.id.ll_foot).setVisibility(INVISIBLE);

        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem == totalCount && scrollState == SCROLL_STATE_IDLE && !isLoading) {
            footer.findViewById(R.id.ll_foot).setVisibility(VISIBLE);
            loadingListener.load();
        }
    }

    //用于尾布局
    private int lastVisibleItem = 0;
    private int totalCount = 0;

    //用于头布局
//    private int firstVisibleItem;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        this.firstVisibleItem = firstVisibleItem;
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
//    public void initHeaderView(Context context) {
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        headLayout = layoutInflater.inflate(R.layout.header, null);
//        headerView = headLayout.findViewById(R.id.rl_header);
//        this.addHeaderView(headLayout);
//
//        init();
//    }
//
//    private void init() {
//        //让它强制调用measue,后面就可以使用getMeasuredHeight了。
//        headerView.measure(0, 0);
//        headerViewHeight = headerView.getMeasuredHeight();
//        headerView.setPadding(headerView.getPaddingLeft(), -headerViewHeight, headerView.getPaddingRight(),
//                headerView.getPaddingBottom());
//        headerView.postInvalidate();
//    }
//
//    private boolean isRemark;//标记当前是否是在listview的最顶端按下的
//    private int startY;//按下时候的Y值
//
//    //当按下时，并且根据firstVisibleItem发现它在最顶端，则需要添加标记并记录y值.
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (firstVisibleItem == 0)
//                    isRemark = true;
//                startY = (int) event.getY();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                onMove(event);
//                break;
//        }
//        //可点击的都会返回true
//        return super.onTouchEvent(event);
//    }
//
//    private int mState;
//    //定义四个状态
//    private static final int STATE_NORMAL = 0;
//    private static final int STATE_PULL = 1;
//    private static final int STATE_RELEASE = 2;
//    private static final int STATE_REFRESHING = 3;
//
//    //记录当前的滚动状态
//    private int scrollState;
//
//    //判断一下移动过程中的操作
//    public void onMove(MotionEvent event) {
//        if (!isRemark)
//            return;
//
//        int endY = (int) event.getY();
//        int dy = endY - startY;
//
//        //一直需要改变的高度
//        int topPadding = dy - headerViewHeight;
//
//        switch (mState) {
//            case STATE_NORMAL:
//                if (dy > 0) mState = STATE_PULL;
//                break;
//
//            case STATE_PULL:
//                if (dy > headerViewHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL)
//                    mState = STATE_RELEASE;
//                if (dy < 0) {
//                    mState = STATE_NORMAL;
//                    isRemark = false;
//                }
//                break;
//
//            case STATE_RELEASE:
//                if (dy < headerViewHeight + 30)
//                    mState = STATE_PULL;
//                break;
//
//            case STATE_REFRESHING:
//                break;
//        }
//        headerView.setPadding(headerView.getPaddingLeft(),
//                topPadding, headerView.getPaddingRight(), headerView.getPaddingBottom());
//    }
}
