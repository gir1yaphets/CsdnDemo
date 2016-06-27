package com.example.pengxiaolve.csdndemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengxiaolve.csdndemo.R;
import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by pengxiaolve on 16/6/18.
 */
public class NewsItemAdapter extends BaseAdapter {

    private List<NewsItemBean> mNewsItems;
    private LayoutInflater mInflater;

    /**
     * 使用了github开源的ImageLoad进行了数据加载
     */
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public NewsItemAdapter(List<NewsItemBean> mNewsItems, Context context) {
        this.mNewsItems = mNewsItems;
        mInflater = LayoutInflater.from(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.images)
                .showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    public int getCount() {
        return mNewsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_view, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.id_title);
            viewHolder.newsImg = (ImageView) convertView.findViewById(R.id.id_newsImg);
            viewHolder.content = (TextView) convertView.findViewById(R.id.id_content);
            viewHolder.date = (TextView) convertView.findViewById(R.id.id_date);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsItemBean newsItemBean = mNewsItems.get(position);

        viewHolder.title.setText(newsItemBean.getTitle());
        viewHolder.content.setText(newsItemBean.getContent());
        viewHolder.date.setText(newsItemBean.getDate());

        if (newsItemBean.getImgLink() != null) {
            viewHolder.newsImg.setVisibility(View.VISIBLE);
            imageLoader.displayImage(newsItemBean.getImgLink(), viewHolder.newsImg, options);
        }else {
            viewHolder.newsImg.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private ImageView newsImg;
        private TextView content;
        private TextView date;
    }

    public void addAll(List<NewsItemBean> items) {
        mNewsItems.addAll(items);
    }
}
