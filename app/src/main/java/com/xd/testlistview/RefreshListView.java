package com.xd.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by hhhhwei on 16/3/17.
 */
public class RefreshListView extends ListView {

    private View mHeaderView;
    private int mHeaderViewHeight;

    private static final int STATE_PULL_REFRESH = 0;//下拉刷新
    private static final int STATE_RELEASE_REFRESH = 1;//松开刷新
    private static final int STATE_REFRESHING = 2;//正在刷新

    private int mCurrentState = 0;

    private TextView tvTitle;
    private TextView tvDate;
    private ImageView ivArrow;
    private ProgressBar pbProgress;
    private RotateAnimation rotateDownToUp;
    private RotateAnimation rotateUpToDown;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnim();
    }

    private void initHeaderView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.header, null);
        this.addHeaderView(mHeaderView);

        tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
        tvDate = (TextView) mHeaderView.findViewById(R.id.tv_date);
        ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
        pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_circle);

        //隐藏headerview
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

    private int startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;

            //在这里去确定是哪一个状态
            case MotionEvent.ACTION_MOVE:
                //异常情况处理,确保startY有效
                if (startY == -1)
                    startY = (int) ev.getRawY();
                int endY = (int) ev.getRawY();

                int dy = endY - startY;

                //正在刷新时就刷新，不要混淆
                if (mCurrentState == STATE_REFRESHING)
                    break;

                //只有在这种情况下，下拉刷新才有效
                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    int padding = dy - mHeaderViewHeight;
                    mHeaderView.setPadding(0, padding, 0, 0);

                    //将状态该为松开刷新
                    if (padding > 0 && mCurrentState != STATE_RELEASE_REFRESH) {
                        mCurrentState = STATE_RELEASE_REFRESH;
                        refreshState();
                    }
                    //将状态改为下拉刷新
                    else if (padding < 0 && mCurrentState != STATE_PULL_REFRESH) {
                        mCurrentState = STATE_PULL_REFRESH;
                        refreshState();
                    }

                    return true;
                }
                break;

            //在这里处理各个状态后的布局位置操作,收尾
            case MotionEvent.ACTION_UP:
                //重置操作
                startY = -1;

                if (mCurrentState == STATE_RELEASE_REFRESH) {
                    mCurrentState = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    refreshState();
                } else if (mCurrentState == STATE_PULL_REFRESH) {
                    //因为刚开始就是下拉刷新，所以不需要初始化了,只需要把半截子的mHeaderView隐藏就好
                    mHeaderView.setPadding(0, 0, -mHeaderViewHeight, 0);
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    //根据状态改变布局
    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(VISIBLE);
                pbProgress.setVisibility(INVISIBLE);
                ivArrow.startAnimation(rotateUpToDown);
                break;

            case STATE_RELEASE_REFRESH:
                tvTitle.setText("松开刷新");
                ivArrow.setVisibility(VISIBLE);
                pbProgress.setVisibility(INVISIBLE);
                ivArrow.startAnimation(rotateDownToUp);
                break;

            case STATE_REFRESHING:
                tvTitle.setText("正在刷新");
                ivArrow.setVisibility(INVISIBLE);
                pbProgress.setVisibility(VISIBLE);
                ivArrow.clearAnimation();
                break;
        }
    }

    public void initAnim() {
        rotateUpToDown = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateUpToDown.setDuration(200);
        rotateUpToDown.setFillAfter(true);

        rotateDownToUp = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateDownToUp.setDuration(200);
        rotateDownToUp.setFillAfter(true);
    }
}
