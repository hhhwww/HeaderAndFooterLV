package com.xd.testlistview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RefreshListView mListView;
    private List<String> mDatas = new ArrayList<>();
    private MyArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDatas();
    }

    private void initViews() {
        mListView = (RefreshListView) findViewById(R.id.listView);
    }

    private void initDatas() {
        for (int i = 0; i < 100; i++) {
            mDatas.add("数据源" + i + "号");
        }
        mAdapter = new MyArrayAdapter(this, R.layout.item_main);
        mListView.setAdapter(mAdapter);
    }

    class MyArrayAdapter extends ArrayAdapter<String> {

        private int resource;
        private final LayoutInflater layoutInflater;


        public MyArrayAdapter(Context context, int resource) {
            super(context, resource);
            this.resource = resource;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder = null;

            if (null == convertView) {
                convertView = layoutInflater.inflate(resource, parent, false);
                myViewHolder = new MyViewHolder();

                myViewHolder.tv = (TextView) convertView.findViewById(R.id.textView);

                convertView.setTag(myViewHolder);
            } else
                myViewHolder = (MyViewHolder) convertView.getTag();

            myViewHolder.tv.setText(mDatas.get(position));

            return convertView;
        }

        class MyViewHolder {
            public TextView tv;
        }
    }
}
