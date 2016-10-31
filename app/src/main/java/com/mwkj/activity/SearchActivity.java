package com.mwkj.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.adapter.BookEntityAdapter;
import com.mwkj.entity.BookEntity;
import com.mwkj.util.MySqliteHelper;
import com.mwkj.widget.XEditText;
import com.qf.kenlibrary.base.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${WU} on 2016/10/31.
 */
public class SearchActivity extends BaseActivity implements XEditText.DrawableLeftListener, TagFlowLayout.OnTagClickListener {
    @Bind(R.id.et)
    XEditText et;
    @Bind(R.id.line)
    LinearLayout line;
    @Bind(R.id.cancel)
    TextView cancel;
    private ArrayAdapter<String> arrayAdapter;
    @Bind(R.id.flowLayout)
    TagFlowLayout flowLayout;
    String[] arr = {"盗墓笔记", "郭德纲", "斗破苍穹", "西游记", "岳云鹏", "坏蛋是怎样炼成的", "凡人修仙传", "三国", "白眉大侠", "三体", "睡眠"};
    int[] background = {R.drawable.tv_shape_blue, R.drawable.tv_shape_gray, R.drawable.tv_shape_green, R.drawable.tv_shape_orange};
    //数据库
    @Bind(R.id.clean)
    TextView clean;

    long id = -1;
    @Bind(R.id.myListView)
    ListView myListView;

    @Bind(R.id.downListView)
    ListView downListView;
    private MySqliteHelper mySqlite;
    private SQLiteDatabase sqLiteDatabase;
    List<String> datas;
    //dialog
    private ProgressDialog dialog;
    //接口拼接相关
    int pageNumber = 1;
    private BookEntityAdapter bookAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        //初始化数据库
        mySqlite = new MySqliteHelper(this);
        sqLiteDatabase = mySqlite.getReadableDatabase();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        bookAdapter = new BookEntityAdapter(SearchActivity.this);
        downListView.setAdapter(bookAdapter);
        myListView.setAdapter(arrayAdapter);
        initDatas();
        //-----------
        et.setDrawableLeftListener(this);
        flowLayout.setAdapter(new TagAdapter<String>(arr) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.tv, flowLayout, false);
                tv.setBackgroundResource(background[position % background.length]);

                tv.setText(s);
                return tv;
            }
        });

        flowLayout.setOnTagClickListener(this);
        //dialog
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置进度条的样式
        dialog.setMessage("正在加载...");

    }

    @Override
    public void onDrawableLeftClick(View view) {
        //发现按钮的监听事件
        dialog.show();
        String str = et.getText().toString();
        if (!TextUtils.isEmpty(str)) {
            boolean flag = false;
            Cursor cursor = sqLiteDatabase.query("search", new String[]{"name"}, null, null, null, null, null);
            if (cursor.getCount() == 0) {
                flag = true;
            } else {
                while (cursor.moveToNext()) {
                    if (cursor.getString(cursor.getColumnIndex("name")).equals(
                            str)) {
                        flag = false;
                        break;
                    } else {
                        flag = true;
                    }
                }
            }
            if (flag) {
                id = saveWord(str);
            }

            //保存进数据库
            if (id > 0) {
                //更新适配器
                arrayAdapter.add(str);
                arrayAdapter.notifyDataSetChanged();
                id = -1;
            } else {

//                Toast.makeText(SearchActivity.this, "已搜索过该数据", Toast.LENGTH_SHORT).show();
            }
//            String encode = URLEncoder.encode(str);
//            Log.d("log", "onDrawableLeftClick: " +encode);
            //开始搜索
            Call<BookEntity> call = AppStartContext.utils.getBookEntityBySearch(pageNumber,str);
            call.enqueue(new Callback<BookEntity>() {
                @Override
                public void onResponse(Call<BookEntity> call, Response<BookEntity> response) {
                    BookEntity bookEntity = response.body();
                    int pageCount = bookEntity.getPage().getPageCount();
                    Log.d("log", "onResponse: " +pageCount);
                    if(pageCount!=0){
                        line.setVisibility(View.GONE);
                        downListView.setVisibility(View.VISIBLE);
                        bookAdapter.setDatas(bookEntity.getAlbums());
                    }else {
//                        downListView.setVisibility(View.GONE);
//                        line.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchActivity.this, "没有匹配的数据!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<BookEntity> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(SearchActivity.this, "请输入搜索词", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @OnClick({R.id.cancel, R.id.clean})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.clean:    //清除数据库
                Log.d("log", "onClick: " +"清除");
                sqLiteDatabase.delete("search", "_id > ?", new String[]{"-1"});
               arrayAdapter.clear();
                break;

        }
    }


    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {

//        Toast.makeText(this, arr[position], Toast.LENGTH_SHORT).show();
        //view.setVisibility(View.GONE);
        dialog.show();
        String str = arr[position];
        if (!TextUtils.isEmpty(str)) {
            boolean flag = false;
            Cursor cursor = sqLiteDatabase.query("search", new String[]{"name"}, null, null, null, null, null);
            if (cursor.getCount() == 0) {
                flag = true;
            } else {
                while (cursor.moveToNext()) {
                    if (cursor.getString(cursor.getColumnIndex("name")).equals(
                            str)) {
                        flag = false;
                        break;
                    } else {
                        flag = true;
                    }
                }
            }
            if (flag) {
                id = saveWord(str);
            }

            //保存进数据库
            if (id > 0) {
                //更新适配器
                arrayAdapter.add(str);
                arrayAdapter.notifyDataSetChanged();
                id = -1;
            } else {

                //                Toast.makeText(SearchActivity.this, "已搜索过该数据", Toast.LENGTH_SHORT).show();
            }
            //            String encode = URLEncoder.encode(str);
            //            Log.d("log", "onDrawableLeftClick: " +encode);
            //开始搜索
            Call<BookEntity> call = AppStartContext.utils.getBookEntityBySearch(pageNumber,str);
            call.enqueue(new Callback<BookEntity>() {
                @Override
                public void onResponse(Call<BookEntity> call, Response<BookEntity> response) {
                    BookEntity bookEntity = response.body();
                    int pageCount = bookEntity.getPage().getPageCount();
                    Log.d("log", "onResponse: " +pageCount);
                    if(pageCount!=0){
                        line.setVisibility(View.GONE);
                        downListView.setVisibility(View.VISIBLE);
                        bookAdapter.setDatas(bookEntity.getAlbums());
                    }else {
                        //                        downListView.setVisibility(View.GONE);
                        //                        line.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchActivity.this, "没有匹配的数据!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<BookEntity> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(SearchActivity.this, "请输入搜索词", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        return true;
    }

    private long saveWord(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);

        return sqLiteDatabase.insert("search", null, values);
    }

    private void initDatas() {
        Cursor cursor = sqLiteDatabase.query("search",
                new String[]{"_id", "name"},
                null, null, null, null, null);

        while (cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex("name"));

            //更新适配器
            arrayAdapter.add(name);
        }
        arrayAdapter.notifyDataSetChanged();
        cursor.close();
    }
}