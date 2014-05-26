package com.a1w0n.funbeta.Jsoup;

import com.a1w0n.funbeta.Bean.AllInfoArticleBean;
import com.a1w0n.funbeta.Bean.AllInfoSectionBean;
import com.a1w0n.funbeta.Bean.CBBean;
import com.a1w0n.funbeta.Bean.RealTimeArticleBean;
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
 * Jsoup解析cnBeta,获取CBBean的接口的封装
 */
public class JsoupWorker {
    // 主站的域名
    public static final String CNBETA_URL = "http://www.cnbeta.com";
    // 用来定位"实时新闻"的Div的class名字
    public static final String REALTIMELIST_CLASSNAME = "realtime_list";
    // 用来定位"所有新闻"的Div的class名字
    public static final String ALLINFO_CLASSNAME = "items_area";
    // 某字段名字
    public static final String KEY_SID_ATTRIBUTE = "data-sid";

    /**
     * 获取新闻总封装CBBean的静态接口
     */
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

    /**
     * 获取新闻总封装CBBean的静态接口,用Jsoup解析出来的document作为参数
     */
    private static CBBean getCBBean(Document doc) {
        if (doc == null) return null;

        CBBean cbBean = new CBBean();
        cbBean.realtimeSectionBean = getRealTimeSectionBean(doc);
        cbBean.allInfoSectionBean = getAllInfoSectionBean(doc);
        return cbBean;
    }

    /**
     * 从document中解析出"实时新闻" RealtimeSectionBean
     */
    private static RealtimeSectionBean getRealTimeSectionBean(Document doc) {
        RealtimeSectionBean realtimeSectionBean = new RealtimeSectionBean();

        ArrayList<RealTimeArticleBean> articles = new ArrayList<RealTimeArticleBean>();

        Element realTimeDivUL = doc.select("div." + REALTIMELIST_CLASSNAME + " > ul").first();

        Elements realTimeLICollection = realTimeDivUL.select("li");

        for (int i = 0; i < realTimeLICollection.size(); i++) {
            articles.add(getRealTimeArticleBean(realTimeLICollection.get(i)));
        }

        realtimeSectionBean.articles = articles;

        return realtimeSectionBean;
    }

    /**
     * 从document中解析出"所有新闻" AllInfoSectionBean
     */
    private static AllInfoSectionBean getAllInfoSectionBean(Document doc) {
        AllInfoSectionBean bean = new AllInfoSectionBean();

        ArrayList<AllInfoArticleBean> articles = new ArrayList<AllInfoArticleBean>();

        Element allInfoDiv = doc.select("div." + ALLINFO_CLASSNAME).first();

        Elements allInfoDLCollection = allInfoDiv.select("dl");

        for (int i = 0; i < allInfoDLCollection.size(); i++) {
            articles.add(getAllInfoArticleBean(allInfoDLCollection.get(i)));
        }

        bean.articles = articles;

        return bean;
    }

    /**
     * 从Element节点中解析出"所有新闻"的某个新闻 AllInfoArticleBean
     */
    private static AllInfoArticleBean getAllInfoArticleBean(Element element) {
        AllInfoArticleBean bean = new AllInfoArticleBean();

        Element temp = element.select("a[href]").first();
        bean.href = temp.attr("href");
        bean.title = temp.text();
        Elements elements = element.select("span");
        temp = elements.get(0);
        bean.authorAndPostTime = temp.text();
        temp = elements.get(1);
        bean.readCount = temp.select("em").first().text();
        temp = elements.get(2);
        bean.recommengCount = temp.select("em").first().text();
        bean.imageTitleURL = element.select("img[src]").first().attr("src");
        bean.description = element.select("div.newsinfo > p").first().text();
        elements = element.select("ul.gray > li > em");
        temp = elements.get(0);
        bean.commentCount = temp.text();
        temp = elements.get(1);
        bean.voteCount = temp.text();
        temp = elements.get(2);
        bean.eventScore = temp.text();
        temp = elements.get(3);
        bean.qualityScore = temp.text();

        // 用来测试数据的正确性的
//        Log.e("===", "href == " + bean.href + "\n"
//                      + "title == " + bean.title + "\n"
//                + "authorAndPostTime == " + bean.authorAndPostTime + "\n"
//                + "readCount == " + bean.readCount + "\n"
//                + "recommengCount == " + bean.recommengCount + "\n"
//                + "imageTitleURL == " + bean.imageTitleURL + "\n"
//                + "description == " + bean.description + "\n"
//                + "commentCount == " + bean.commentCount + "\n"
//                + "voteCount == " + bean.voteCount + "\n"
//                + "eventScore == " + bean.eventScore + "\n"
//                + "qualityScore == " + bean.qualityScore + "\n");

        return bean;
    }

    /**
     * 从Element节点中解析出"实时新闻"的某个新闻 RealTimeArticleBean
     */
    private static RealTimeArticleBean getRealTimeArticleBean(Element element) {
        RealTimeArticleBean bean = new RealTimeArticleBean();
        String sid = element.attr(KEY_SID_ATTRIBUTE);
        String postTime = element.select("span.update_time").text();
        Element temp = element.select("div.title > a").first();
        String href = temp.attr("href");
        String title = temp.text();
        String imageTitleURL = element.select("div.image > img").attr("original");
        String desription = element.select("p.excerpt").text();

        bean.description = desription;
        bean.href = href;
        bean.imageTitleURL = imageTitleURL;
        bean.title = title;
        bean.postTime = postTime;
        bean.sid = sid;
        return bean;
    }

}
