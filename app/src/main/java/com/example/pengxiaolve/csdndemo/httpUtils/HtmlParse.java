package com.example.pengxiaolve.csdndemo.httpUtils;

import android.text.TextUtils;

import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsContentBean;
import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsItemBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengxiaolve on 16/6/11.
 */
public class HtmlParse {

    public static ArrayList<NewsItemBean> htmlParse(String htmlString) {
        ArrayList<NewsItemBean> Items = null;
        NewsItemBean ItemBean = null;

        if (htmlString != null) {
            System.out.println(htmlString);
            Items = new ArrayList<NewsItemBean>();

            Document doc = Jsoup.parse(htmlString);
            Elements units = doc.getElementsByClass("unit");

            for (int i = 0; i < units.size(); i++) {
                ItemBean = new NewsItemBean();

                Element unit = units.get(i);
                Element h1 = unit.getElementsByTag("h1").get(0);
                Element a = h1.child(0);

                String href = a.attr("href");
                String title = h1.text();

                Element h4 = unit.getElementsByTag("h4").get(0);
                Element span = h4.getElementsByClass("ago").get(0);

                String data = span.text();

                ItemBean.setLink(href);
                ItemBean.setTitle(title);
                ItemBean.setDate(data);

                Element dl = unit.getElementsByTag("dl").get(0); //dl
                Element dt = dl.child(0); //dt

                try {
                    Element imgEle = dt.child(0);
                    String imgLink = imgEle.child(0).attr("src");

                    ItemBean.setImgLink(imgLink);
                }catch (IndexOutOfBoundsException e) {

                }


                Element dd = dl.child(1); //dd
                String content = dd.text();

                ItemBean.setContent(content);

                Items.add(ItemBean);
            }
        }

        return Items;
    }

    public static ArrayList<NewsItemBean> getNewsItems(int newsType, int currentPage) {
        String urlString = UrlUtils.generateUrl(newsType, currentPage);
        String htmlString = HttpConnect.doGet(urlString);
        ArrayList<NewsItemBean> newsItems = HtmlParse.htmlParse(htmlString);

        return newsItems;
    }

    /**
     * 解析新闻内容
     */
    public static List<NewsContentBean> contentParser (String htmlString){
        List<NewsContentBean> newsList = null;

        if (!TextUtils.isEmpty(htmlString)) {
            newsList = new ArrayList<NewsContentBean>();
            Document document = Jsoup.parse(htmlString);

//            Element element = document.getElementsByClass("left").get(0);
//            Element ele_detail = element.getElementsByClass("detail").get(0);
            Element ele_detail = document.select(".left .detail").get(0);

            //解析标题
            Element ele_h1 = ele_detail.child(0);
            String title = ele_h1.text();
            NewsContentBean newsTitle = new NewsContentBean();
            newsTitle.setTitle(title);
            newsList.add(newsTitle);

            //解析摘要
            Element ele_summary = document.getElementsByClass("summary").get(0);
            Element ele_input = ele_summary.child(0);
            String summary = ele_input.attr("value");
            NewsContentBean newsSummary = new NewsContentBean();
            newsSummary.setSummary(summary);
            newsList.add(newsSummary);

            //解析内容
//            Element ele_content = document.getElementsByClass("con news_content").get(0); con news_content中的空格表示这个div继承了两个class
            Element ele_content = ele_detail.select("div.con.news_content").get(0);
            Elements ele_plist = ele_content.getElementsByTag("p");

            for (int i = 0; i < ele_plist.size(); i++) {
                Element ele_p = ele_plist.get(i);

                //没有style属性的p标签
                if (!ele_p.hasAttr("style")) {
                    //创建新闻内容对象
                    String content = ele_p.text();
                    NewsContentBean newsContent = new NewsContentBean();
                    newsContent.setContent(content);
                    newsList.add(newsContent);
                }
                //含有style属性的p标签
                else {
                    //新闻图片标签img
                    Elements ele_imgs = ele_p.getElementsByTag("img");
                    if (ele_imgs.size() > 0) {
                        Element ele_img = ele_imgs.get(0);
                        String imageLink = ele_img.attr("src");
                        NewsContentBean newsImage = new NewsContentBean();
                        newsImage.setImageLink(imageLink);
                        newsList.add(newsImage);
                    }
                }
            }
        }

        return newsList;
    }
}
