package com.a1w0n.funbeta.Bean;

/**
 * Created by Aiwan on 2014/5/24 0024.
 * 从Jsoup解析得到的总数据类，根据Html的结构，把Html中的section封装起来
 */
public class CBBean {

    public RealtimeSectionBean realtimeSectionBean;

    public AllInfoSectionBean allInfoSectionBean;

    public int size() {
        int size = 0;

        if (realtimeSectionBean != null && realtimeSectionBean.articles != null) {
            size += realtimeSectionBean.articles.size();
        }

//        if (allInfoSectionBean != null && allInfoSectionBean.articles != null) {
//            size += allInfoSectionBean.articles.size();
//        }

        return size;
    }

}
