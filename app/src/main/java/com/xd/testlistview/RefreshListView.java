package com.xd.testlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by hhhhwei on 16/3/17.
 */
public class RefreshListView extends ListView {

    private View mHeaderView;
    private int mHeaderViewHeight;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.header, null);
        this.addHeaderView(mHeaderView);

        //隐藏headerview
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }


}
