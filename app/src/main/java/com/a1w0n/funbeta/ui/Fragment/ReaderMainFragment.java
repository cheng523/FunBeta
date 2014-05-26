package com.a1w0n.funbeta.ui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.a1w0n.funbeta.AsyncTask.GetCBBeanAsyncTask;
import com.a1w0n.funbeta.Bean.ArticleTitleBean;
import com.a1w0n.funbeta.Bean.CBBean;
import com.a1w0n.funbeta.R;
import com.a1w0n.funbeta.libs.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



/**
 * Created by Aiwan on 2014/5/24 0024.
 *
 * 主页面的内嵌Fragment，负责新闻的获取和现实。主页面Activity就是个空壳。
 */
public class ReaderMainFragment extends BaseFragment{

    // 根View,用来获取子View
    private View mRootView;
    // 显示新闻的下拉刷新ListView
    private PullToRefreshListView mNewsListView;

    private List<Map<String, String>> mNewsListData;

      private ArrayAdapter<String> mAdapter;

    private LinkedList<String> mListItems;



    /**
     * 想获取第几页的新闻数据，因为网页每次返回的新闻数量是一定的，所以想获取更多内容，就要用
     * 这个ID去构建新的URL来请求新的数据拼接在一起.
     * 规定第一页的ID是0.
     */
    private int mPageID = 0;

    public ReaderMainFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        GetCBBeanAsyncTask task = new GetCBBeanAsyncTask(){
            @Override
            protected void PostExecute(CBBean paramResult) {
                super.PostExecute(paramResult);
                onNewsDataReceived(paramResult);
            }
        };
        task.execute(mPageID);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_readermain, null, false);
        initUIComponents();
        return mRootView;
    }

    /**
     * 初始化和配置UI组件
     */
    private void initUIComponents() {
        mNewsListView = (PullToRefreshListView)mRootView.findViewById(R.id.FRM_newsList);
    }

    /**
     * AsyncTask获取到新闻数据了，用这个方法来显示出来
     */
    private void onNewsDataReceived(CBBean paramResult) {

        //paramResult.realtimeSectionBean;
     //   List<String>  aa= paramResult.realtimeSectionBean;

        Log.d("====", "sid : onNewsDataReceived\n" );
        Log.d("====", "sid : onNewsDataReceived"+   paramResult.realtimeSectionBean.articles.size() );

        ArrayList<ArticleTitleBean>  aa= paramResult.realtimeSectionBean.articles;
        Log.d("====", "sid : onNewsDataReceived3"+   aa.size() );

        mListItems = new LinkedList<String>();

       // mListItems.addAll(aa);


        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mListItems);

        ListView actualListView =  mNewsListView.getRefreshableView();
        actualListView.setAdapter(mAdapter);

    }
}
