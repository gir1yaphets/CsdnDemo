package com.example.pengxiaolve.csdndemo.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.pengxiaolve.csdndemo.R;
import com.example.pengxiaolve.csdndemo.activity.NewsContentActivity;
import com.example.pengxiaolve.csdndemo.adapter.NewsItemAdapter;
import com.example.pengxiaolve.csdndemo.commonUtil.NetUtil;
import com.example.pengxiaolve.csdndemo.dao.NewsItemDao;
import com.example.pengxiaolve.csdndemo.httpUtils.HtmlParse;
import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsItemBean;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by pengxiaolve on 16/6/10.
 */
public class MainFragment extends android.support.v4.app.Fragment implements IXListViewRefreshListener, IXListViewLoadMore {

    /**
     * 扩展listview
     */
    private XListView mXListView;

    /**
     * 数据适配器
     */
    private NewsItemAdapter mAdapter;

    /**
     * 存储每一个NewsItemBean的list数据
     */
    private List<NewsItemBean> mDatas = new ArrayList<NewsItemBean>();

    /**
     * 数据库操作对象
     */
//    private NewsItemDao mNewsItemDao = new NewsItemDao(getContext()); //错误写法，此时getContext为null！！！
    private NewsItemDao mNewsItemDao;

    /**
     * 当前页数
     */
    private int mCurrentPage = 1;

    /**
     * 新闻类型
     */
    private int mNewsType;

    /**
     * 处理向上滑动 加载数据
     */
    private static final int LOAD_MORE = 1;

    /**
     * 处理向下滑动 刷新数据
     */
    private static final int START_REFRESH = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        Bundle args = getArguments();
//        TextView textView = (TextView) view.findViewById(R.id.textview);
//        textView.setText(PagerAdapter.TITLES[args.getInt("POSITION")]);
        mNewsType = args.getInt("NEWSTYPE");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new NewsItemAdapter(mDatas, getContext());
        mNewsItemDao = new NewsItemDao(getContext());
        mXListView = (XListView) getView().findViewById(R.id.id_xlistView);
        mXListView.setAdapter(mAdapter);
        mXListView.setPullLoadEnable(this);
        mXListView.setPullRefreshEnable(this);

        /**
         * 进来时直接刷新
         */
        mXListView.startRefresh();

        /**
         * 为mXListView设置item监听器
         */
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = mDatas.get(position).getLink();

                if (!TextUtils.isEmpty(link)) {
                    //启动内容activity
                    Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                    intent.putExtra("CONTENT_URL", link);
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    /**
     * 调用startLoadMore方法后被回调
     */
    @Override
    public void onLoadMore() {
        new LoadDataTask().execute(LOAD_MORE);
    }

    /**
     * 调用startRefresh方法后被回调
     */
    @Override
    public void onRefresh() {
        new LoadDataTask().execute(START_REFRESH);
    }

    /**
     * case LOAD_MORE封装函数
     */
    private void loadMoreData() {
        mCurrentPage += 1;

        //存在网络连接
        if (NetUtil.checkNetConn(getContext())) {
            //从网络加载数据
            mDatas = HtmlParse.getNewsItems(mNewsType, mCurrentPage);

            //将本次读取的数据存储到数据库中
            mNewsItemDao.insertList(mDatas);

        }else {
            //从数据库中加载数据
            mDatas = mNewsItemDao.queryNewsItemsTable(mNewsType, mCurrentPage);
        }
    }

    /**
     * case START_REFRESH封装函数
     */
    private void refreashData() {

        //存在网络连接
        if (NetUtil.checkNetConn(getContext())) {
            //从网络加载数据
            mDatas = HtmlParse.getNewsItems(mNewsType, mCurrentPage);

            //将现有的newstype类型的数据删除
            mNewsItemDao.deleteAll(mNewsType);

            //将本次读取的数据存储到数据库中
            mNewsItemDao.insertList(mDatas);

        }else {
            //从数据库中加载数据
            mDatas = mNewsItemDao.queryNewsItemsTable(mNewsType, mCurrentPage);
        }
    }

    class LoadDataTask extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            switch (params[0]) {
                case LOAD_MORE:
//                    mCurrentPage += 1;
//                    mDatas = HtmlParse.getNewsItems(mNewsType, mCurrentPage);
                    loadMoreData();
                    break;
                case START_REFRESH:
//                    mDatas = HtmlParse.getNewsItems(mNewsType, mCurrentPage);
                    refreashData();
                    break;
                default:
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            mAdapter.addAll(mDatas);
            mAdapter.notifyDataSetChanged();
            mXListView.stopRefresh();
        }
    }
}
