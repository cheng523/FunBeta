package com.a1w0n.funbeta.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1w0n.funbeta.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Aiwan on 2014/5/25 0025.
 *
 * ReaderMainFragment的新闻列表要用到的Adapter
 */
public class ReaderMainListAdapter extends BaseAdapter {

    // 新闻数据
    private List<Map<String, String>> mData;
    // 用来从XML文件中获取View
    private LayoutInflater mLI;

    public ReaderMainListAdapter(Context context, List<Map<String, String>> paramResult) {
        mData = paramResult;
        mLI = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = null;
        if (convertView == null) {
            convertView = mLI.inflate(R.layout.item_newslist_readermain, null, false);
            tv = (TextView)convertView.findViewById(R.id.INLRM_title);
            convertView.setTag(tv);
        } else {
            tv = (TextView)convertView.getTag();
        }

        //tv.setText(mData.get(position).);

        return null;
    }
}
