package com.a1w0n.funbeta.Jsoup;

import android.util.Log;

import com.a1w0n.funbeta.Bean.AllInfoSectionBean;
import com.a1w0n.funbeta.Bean.ArticleTitleBean;
import com.a1w0n.funbeta.Bean.CBBean;
import com.a1w0n.funbeta.Bean.RealtimeSectionBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Aiwan on 2014/5/25 0025.
 *
 */
public class JsoupWorker {

    public static final String CNBETA_URL = "http://www.cnbeta.com";

    public static final String REALTIMELIST_CLASSNAME = "realtime_list";

    public static final String KEY_SID_ATTRIBUTE = "data-sid";

    public static CBBean getCBBean() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(CNBETA_URL), 5000);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return getCBBean(doc);
    }

    private static CBBean getCBBean(Document doc) {
        if (doc == null) return null;

        CBBean cbBean = new CBBean();
        cbBean.realtimeSectionBean = getRealTimeSectionBean(doc);
        cbBean.allInfoSectionBean = getAllInfoSectionBean(doc);
        return cbBean;
    }

    private static RealtimeSectionBean getRealTimeSectionBean(Document doc) {
        RealtimeSectionBean realtimeSectionBean = new RealtimeSectionBean();

        ArrayList<ArticleTitleBean> articles = new ArrayList<ArticleTitleBean>();

        Element realTimeDivUL = doc.select("div." + REALTIMELIST_CLASSNAME + " > ul").first();

        Elements realTimeLICollection = realTimeDivUL.select("li");

        ArticleTitleBean articleBean = null;
        for (int i = 0; i < realTimeLICollection.size(); i++) {
            articles.add(getArticleTitleBean(realTimeLICollection.get(i)));
        }

        realtimeSectionBean.articles = articles;

        return realtimeSectionBean;
    }

    private static AllInfoSectionBean getAllInfoSectionBean(Document doc) {


        return null;
    }

    private static ArticleTitleBean getArticleTitleBean(Element element) {
        ArticleTitleBean bean = new ArticleTitleBean();
        String sid = element.attr(KEY_SID_ATTRIBUTE);
        String postTime = element.select("span.update_time").text();
        Element temp = element.select("div.title > a").first();
        String href = temp.attr("href");
        String title = temp.text();
        String imageTitleURL = element.select("div.image > img").attr("original");
        String desription = element.select("p.excerpt").text();
        Log.d("====", "sid : " + sid + "\n" + "posttime : " + postTime
                + "\n" + "href : " + href
                + "\n" + "title : " + title
                + "\n" + "desription : " + desription
                + "\n" + "imageTitleURL : " + imageTitleURL);
        return bean;
    }

}
