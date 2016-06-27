package com.example.pengxiaolve.csdndemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pengxiaolve.csdndemo.httpUtils.bean.NewsItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengxiaolve on 16/6/19.
 */
public class NewsItemDao {

    private Context mContext;

//    private MyDbHelper mDbHelper = new MyDbHelper(mContext); //错误写法 会先调用次变量初始化，然后再调用构造函数，此时context＝null
    private MyDbHelper mDbHelper;

    private static final String TABLE_NAME = "newsitems";

    public NewsItemDao(Context context) {
        this.mContext = context;
        mDbHelper = new MyDbHelper(mContext);
    }

    /**
     * 插入一条item数据
     */
    public void insertItem(NewsItemBean item) {
        ContentValues values = new ContentValues();

        values.put("title", item.getTitle());
        values.put("link", item.getLink());
        values.put("date", item.getDate());
        values.put("imagelink", item.getImgLink());
        values.put("content", item.getContent());
        values.put("newstype", item.getNewsType());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    /**
     * 插入list
     * @param itemBeanList
     */
    public void insertList(List<NewsItemBean> itemBeanList) {
        for (NewsItemBean itemBean : itemBeanList) {
            insertItem(itemBean);
        }
    }

    /**
     *清空表中数据
     */
    public void deleteAll(int newstype) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "newstype = " + newstype, null);

        db.close();
    }

    /**
     * 删除一条item数据
     * @param item
     */
    public void deleteItem(NewsItemBean item) {

    }

    /**
     * 查找相应newstype的数据，根据当前页数，一次查找10条数据
     * @param newstype
     * @param currentpage
     * @return
     */
    public List<NewsItemBean> queryNewsItemsTable(int newstype, int currentpage){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<NewsItemBean> itemList = null;
        int offset = 10;
        int startPos = (currentpage - 1) * offset;
        String limit = startPos + "," + (startPos + 10);

        Cursor cursor = db.query(TABLE_NAME, new String[]{"title", "link", "date", "imagelink", "content", "newstype"},
                "newstype = " + newstype, null, null, null, limit);

        if (cursor != null && cursor.getCount() > 0) {
            itemList = new ArrayList<NewsItemBean>();

            while (cursor.moveToNext()) {
                String title = cursor.getString(0);
                String link = cursor.getString(1);
                String data = cursor.getString(2);
                String imageLink = cursor.getString(3);
                String content = cursor.getString(4);
                int newsType = cursor.getInt(5);

                NewsItemBean itemBean = new NewsItemBean(title, link, data, imageLink, content, newsType);
                itemList.add(itemBean);
            }
        }

        return itemList;
    }
}
