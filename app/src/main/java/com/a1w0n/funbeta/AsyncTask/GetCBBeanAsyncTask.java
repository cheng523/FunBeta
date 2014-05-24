package com.a1w0n.funbeta.AsyncTask;

import com.a1w0n.funbeta.tools.HtmlTool;

import java.util.List;
import java.util.Map;

/**
 * Created by Aiwan on 2014/5/24 0024.
 *
 * 从网页获取新闻数据的AsyncTask
 */
public class GetCBBeanAsyncTask extends BaseAsyncTask<Integer, Void, List<Map<String, String>>> {

    @Override
    protected List<Map<String, String>> doInBackground(Integer... params) {
        return HtmlTool.getNewsListFromMobilePage(HtmlTool.URL_MOBILE,
                params[0]);
    }
}
