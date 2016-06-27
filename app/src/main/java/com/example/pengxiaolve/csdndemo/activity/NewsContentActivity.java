package com.example.pengxiaolve.csdndemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pengxiaolve.csdndemo.R;
import com.example.pengxiaolve.csdndemo.adapter.ContentAdapter;
import com.example.pengxiaolve.csdndemo.httpUtils.HtmlParse;
import com.example.pengxiaolve.csdndemo.httpUtils.HttpGetConnect;
import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsContentBean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by pengxiaolve on 16/6/23.
 */
public class NewsContentActivity extends Activity implements IXListViewLoadMore,IXListViewRefreshListener,Runnable {

    private static final String TAG = "NewsContentActivity";

    /**
     * 扩展的listview
     */
    private XListView mXListView;

    /**
     * 内容listview的适配器
     */
    private ContentAdapter mAdapter = null;

    /**
     * 主线程使用的Handler
     */
    private MainHandler mMainHandler;

    /**
     * 新线程使用的Handler
     */
    private Handler mHandler;

    /**
     * 新闻内容的list
     */
    private List<NewsContentBean> mNewsList = new ArrayList<NewsContentBean>();

    /**
     * 内容链接Url
     */
    private String mContentLink;

    /**
     * progressbar
     */
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        mAdapter = new ContentAdapter(this, mNewsList);
        mXListView = (XListView) findViewById(R.id.id_listview);
        mXListView.setPullLoadEnable(this);
        mXListView.setPullRefreshEnable(this);
        mXListView.setAdapter(mAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);

        //获取内容链接
        mContentLink = getIntent().getStringExtra("CONTENT_URL");

        //启动一个线程进行读取网页
        Log.d(TAG, "onCreate: " + Thread.currentThread().getId());
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
        mHandler.post(this);

        mMainHandler = new MainHandler();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void run() {
        Log.d(TAG, "run: " + Thread.currentThread().getId());
        //读取网页内容
        if (!TextUtils.isEmpty(mContentLink)) {
            InputStream inputStream = HttpGetConnect.httpDoGet(mContentLink);
            String htmlString = HttpGetConnect.getStringFromInputStream(inputStream);
            List<NewsContentBean> newsList = HtmlParse.contentParser(htmlString);

            //给Ui线程发送消息，更新界面
            Message msg = new Message();
            msg.what = 0x01;
            msg.obj = newsList;
            mMainHandler.sendMessage(msg);
        }
    }

    /**
     * 发送消息和处理消息的handler
     */
    class MainHandler extends Handler {

        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: " + Thread.currentThread().getId());
            if (msg != null) {
                if (msg.what == 0x01) {
                    mNewsList = (List<NewsContentBean>) msg.obj;
                    mProgressBar.setVisibility(View.GONE);
                    mAdapter.addAll(mNewsList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
