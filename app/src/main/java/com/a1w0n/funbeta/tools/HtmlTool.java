package com.a1w0n.funbeta.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlTool {

    // 电脑版网页地址
    public static final String URL_MAIN = "http://www.cnbeta.com";
    // 手机版网页地址，从手机版网页获取新闻内容快很多
    public static final String URL_MOBILE = "http://m.cnbeta.com";

    /**
     *
     */
    public static List<Map<String, String>> getNewsList(String urlString) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(urlString), 5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc == null) {
            return null;
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Elements es = doc.getElementsByClass("topic");
        for (Element e : es) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", e.getElementsByTag("a").text());
            map.put("href", urlString + e.getElementsByTag("a").attr("href"));
            list.add(map);
        }
        return list;
    }

    /**
     * �ӵ��Զ�ҳ���ȡ�������ģ������֣�
     *
     * @param href
     * @return
     */
    public static String getNewsContent(String href) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(href), 4000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) {
            return null;
        }
        String content = doc.body().getElementById("news_content").text();
        return content;
    }

    /**
     * �ӵ��԰�ҳ���ȡ��������html����
     *
     * @param href
     * @return
     */
    public static String getNewsContentHtml(String href) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(href), 4000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) {
            return null;
        }

        return doc.getElementById("news_content").html();
    }

    /**
     * 构造特定页数的Url,通过Jsoup库获取新闻标题和内容
     */
    public static List<Map<String, String>> getNewsListFromMobilePage(
            String urlString, int pageID) {
        Document doc = null;
        try {
            String string = urlString;
            if (pageID >= 1) {
                string = urlString + "/list_latest_" + pageID;
            }
            doc = Jsoup.parse(new URL(string), 5000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc == null) {
            return null;
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Elements es = doc.getElementsByTag("a");
        for (Element e : es) {
            String href = e.attr("href");
            if (href.contains("view_")) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", e.text());
                map.put("href", href);
                // Getting to this title means we have already added all news!
                if (e.text().contains("img src")) break;
                list.add(map);
            }
        }
        return list;
    }

    /**
     *
     */
    public static String getNewsContentHtmlFromMobilePage(String href) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(href), 4000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) {
            return null;
        }
        return doc.getElementsByTag("card").html();
    }

}
