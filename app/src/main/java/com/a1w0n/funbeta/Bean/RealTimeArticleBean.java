package com.a1w0n.funbeta.Bean;

/**
 * Created by Aiwan on 2014/5/25 0025.
 *
 * 实时新闻的所有属性的封装
 * cnBeta的新闻分两种，一种是"实时新闻", 一种是"所有新闻"
 */
public class RealTimeArticleBean {
    // 在构造文章详情页URL时需要用到的文章ID
    public String sid;
    // 文章投稿时间
    public String postTime;
    // 文章的URL后缀
    public String href;
    // 文章的标题
    public String title;
    // 标题图片
    public String imageTitleURL;
    // 文章的简述
    public String description;
}
