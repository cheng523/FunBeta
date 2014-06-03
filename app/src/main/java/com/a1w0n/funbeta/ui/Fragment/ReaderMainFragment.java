package com.a1w0n.funbeta.ui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a1w0n.funbeta.AsyncTask.GetCBBeanAsyncTask;
import com.a1w0n.funbeta.Bean.CBBean;
import com.a1w0n.funbeta.R;
import com.a1w0n.funbeta.libs.pulltorefresh.PullToRefreshBase;
import com.a1w0n.funbeta.libs.pulltorefresh.PullToRefreshListView;
import com.a1w0n.funbeta.ui.Adapter.ReaderMainListAdapter;

/**
 * Created by Aiwan on 2014/5/24 0024.
 *
 * 主页面的内嵌Fragment，负责新闻的获取和现实。主页面Activity就是个空壳。
 */
public class ReaderMainFragment extends BaseFragment {
    private static final String TAG = "ReaderMainFragment";

    // 根View,用来获取子View
    private View mRootView;
    // 提示正在加载数据的RelativeLayout
    private View mLoadingView;

    private View mNoDataView;
    // 显示新闻的下拉刷新ListView
    private PullToRefreshListView mNewsListView;

    private GetCBBeanAsyncTask task;

    /**
     * 想获取第几页的新闻数据，因为网页每次返回的新闻数量是一定的，所以想获取更多内容，就要用
     * 这个ID去构建新的URL来请求新的数据拼接在一起.
     * 规定第一页的ID是0.
     */
    private int mPageID = 0;

    public ReaderMainFragment() {
    }

    private class ReaderMainFragmentAsyncTask extends GetCBBeanAsyncTask{

        @Override
        protected void PostExecute(CBBean paramResult) {
            super.PostExecute(paramResult);
            onNewsDataReceived(paramResult);

                /* huangdong add  */
            if (null != mNewsListView){
                mNewsListView.onRefreshComplete();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        task = new GetCBBeanAsyncTask(){
//            @Override
//            protected void PostExecute(CBBean paramResult) {
//                super.PostExecute(paramResult);
//                onNewsDataReceived(paramResult);
//
//                /* huangdong add  */
//                mNewsListView.onRefreshComplete();
//            }
//        };
//        /* huangdong modify 执行放在pull监听器里面 已撤销 */
//        task.execute(mPageID);
        /* huangdong modify  */
        new ReaderMainFragmentAsyncTask().execute(mPageID);

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

        /* huangdong add 2014-06-02 设置顶部刷新模式 */
        mNewsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mNewsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("刷新标志位置");
                /* 同一个task对象，只能执行一次 */
//                task.execute(mPageID);
                new ReaderMainFragmentAsyncTask().execute(mPageID);

        }
        });


        mLoadingView = mRootView.findViewById(R.id.FRM_progressBar);
        mNoDataView = mRootView.findViewById(R.id.FRM_noDataText);
    }

    /**
     * AsyncTask获取到新闻数据了，用这个方法来显示出来
     */
    private void onNewsDataReceived(CBBean paramResult) {
        mLoadingView.setVisibility(View.GONE);
        if (paramResult == null) {
            mNoDataView.setVisibility(View.VISIBLE);
        } else {
            mNewsListView.setVisibility(View.VISIBLE);
            mNewsListView.getRefreshableView().setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "paramResult.size() = " + paramResult.size());
        ReaderMainListAdapter adapter = new ReaderMainListAdapter(getActivity(), paramResult);
        //mNewsListView.getRefreshableView().setAdapter(adapter);

        /* huangdong modify 不能直接设置适配器，需要使用内部的ListView设置  */
//        mNewsListView.setAdapter(adapter);

        /* huangdong add 设置适配器 */
        ListView listView = mNewsListView.getRefreshableView();
        listView.setAdapter(adapter);
    }
}
