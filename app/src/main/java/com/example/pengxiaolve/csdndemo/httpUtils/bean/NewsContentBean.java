package com.example.pengxiaolve.csdndemo.httpUtils.bean;

/**
 * Created by pengxiaolve on 16/6/25.
 */
public class NewsContentBean {

    public interface NewsContentType {

        public static final int TITLE_TYPE = 0;
        public static final int SUMMARY_TYPE = 1;
        public static final int CONTENT_TYPE = 2;
        public static final int IMAGE_TYPE = 3;
    }

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻摘要
     */
    private String summary;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 新闻图片链接
     */
    private String imageLink;

    /**
     * 新闻类型
     */
    private int newsType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setNewsType(NewsContentType.TITLE_TYPE);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        setNewsType(NewsContentType.SUMMARY_TYPE);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        setNewsType(NewsContentType.CONTENT_TYPE);
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
        setNewsType(NewsContentType.IMAGE_TYPE);
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }
}
