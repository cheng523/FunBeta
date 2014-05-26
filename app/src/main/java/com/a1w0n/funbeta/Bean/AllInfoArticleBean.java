package com.a1w0n.funbeta.Bean;

/**
 * Created by Aiwan on 2014/5/26 0026.
 *
 * "所有新闻" 的文章的属性的封装
 * cnBeta的新闻分两种，一种是"实时新闻", 一种是"所有新闻"
 */
public class AllInfoArticleBean {

    // 在构造文章详情页URL时需要用到的文章ID
    public String sid;
    // 文章的URL后缀
    public String href;
    // 文章的标题
    public String title;
    // 投稿作者
    public String authorAndPostTime;
    // 被阅读的次数
    public String readCount;
    // 被推荐的次数
    public String recommengCount;
    // 标题图片
    public String imageTitleURL;
    // 文章的简述
    public String description;
    // 评论的个数
    public String commentCount;
    // 打分的个数
    public String voteCount;
    // 事件分
    public String eventScore;
    // 质量分
    public String qualityScore;
}
