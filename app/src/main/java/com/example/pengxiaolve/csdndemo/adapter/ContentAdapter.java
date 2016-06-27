package com.example.pengxiaolve.csdndemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengxiaolve.csdndemo.R;
import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsContentBean;

import java.util.List;

/**
 * Created by pengxiaolve on 16/6/23.
 */
public class ContentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    /**
     * 新闻标题
     */
    private TextView mTitle;

    /**
     * 新闻摘要
     */
    private TextView mSummary;

    /**
     * 新闻内容
     */
    private TextView mContent;

    /**
     * 新闻图片
     */
    private ImageView mImage;

    /**
     * 新闻内容的list
     */
    private List<NewsContentBean> mNewsList;

    public ContentAdapter(Context context, List<NewsContentBean> newsList) {
        this.mInflater = LayoutInflater.from(context);
        this.mNewsList = newsList;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsContentBean newsContentBean = mNewsList.get(position);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (newsContentBean.getNewsType()) {
                case NewsContentBean.NewsContentType.TITLE_TYPE:
                    convertView = mInflater.inflate(R.layout.item_title, parent, false);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(viewHolder);
                    break;
                case NewsContentBean.NewsContentType.SUMMARY_TYPE:
                    convertView = mInflater.inflate(R.layout.item_summary, parent, false);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(viewHolder);
                    break;
                case NewsContentBean.NewsContentType.CONTENT_TYPE:
                    convertView = mInflater.inflate(R.layout.item_content, parent, false);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(viewHolder);
                    break;
                case NewsContentBean.NewsContentType.IMAGE_TYPE:
                    convertView = mInflater.inflate(R.layout.item_image, parent, false);
                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    convertView.setTag(viewHolder);
                    break;
                default:
                    break;
            }
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //为布局设值
        switch (newsContentBean.getNewsType()) {
            case NewsContentBean.NewsContentType.TITLE_TYPE:
                viewHolder.textView.setText(newsContentBean.getTitle());
                break;
            case NewsContentBean.NewsContentType.SUMMARY_TYPE:
                viewHolder.textView.setText(newsContentBean.getSummary());
                break;
            case NewsContentBean.NewsContentType.CONTENT_TYPE:
                viewHolder.textView.setText(newsContentBean.getContent());
                break;
            case NewsContentBean.NewsContentType.IMAGE_TYPE:
                break;
            default:
                break;
        }

        return convertView;
    }

    class ViewHolder {
        private TextView textView;
        public ImageView imageView;
    }

    /**
     * 将list更新到adpater的list中
     * @param newsList
     */
    public void addAll(List<NewsContentBean> newsList) {
        mNewsList.addAll(newsList);
    }
}
