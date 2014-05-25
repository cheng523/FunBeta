package com.a1w0n.funbeta.AsyncTask;

import com.a1w0n.funbeta.Bean.CBBean;
import com.a1w0n.funbeta.Jsoup.JsoupWorker;

/**
 * Created by Aiwan on 2014/5/24 0024.
 *
 * 从网页获取新闻数据的AsyncTask
 */
public class GetCBBeanAsyncTask extends BaseAsyncTask<Integer, Void, CBBean> {

    @Override
    protected CBBean doInBackground(Integer... params) {
        return JsoupWorker.getCBBean();
    }
}
