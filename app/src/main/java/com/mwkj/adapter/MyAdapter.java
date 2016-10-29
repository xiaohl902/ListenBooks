package com.mwkj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mwkj.activity.ArtWorksActivity;
import com.mwkj.activity.ArtistInfoActivity;
import com.mwkj.activity.BookTypeActivity;
import com.mwkj.activity.R;
import com.mwkj.entity.CrosstalkEntity;
import com.mwkj.util.Constant;
import com.mwkj.widget.MyListView;
import com.mwkj.widget.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${WU} on 2016/10/27.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> datas;
    private ViewHolder viewHolder;
    private CrosstalkEntity crosstalkEntity;
    private NoScrollGridView hotGridView,artGridView;
    private MyListView mlv;
    private HotAdapter hotAdapter;
    private SpecialAdapter specialAdapter;
    private InArtistAdapter inArtistAdapter;
    public MyAdapter(Context context,CrosstalkEntity crosstalkEntity) {
        this.context = context;
        this.crosstalkEntity = crosstalkEntity;
        this.datas = new ArrayList<>();
        hotAdapter = new HotAdapter(context);
        specialAdapter = new SpecialAdapter(context);
        inArtistAdapter = new InArtistAdapter(context);
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            if(view!=null){
            viewHolder = (ViewHolder) view.getTag();
            }else {
                viewHolder = new ViewHolder();
                switch (getItemViewType(i)){
                    case 0:
                        view = LayoutInflater.from(context).inflate(R.layout.item_hot,null);
                            hotGridView = (NoScrollGridView) view.findViewById(R.id.hotGridView);
                            hotGridView.setAdapter(hotAdapter);
                            hotAdapter.setDatas(crosstalkEntity.getHot());
                            hotGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(context,ArtWorksActivity.class);
                                    intent.putExtra("albumid", crosstalkEntity.getHot().get(i).getAlbumId());
                                    intent.putExtra("worksname",crosstalkEntity.getHot().get(i).getAlbumName());
                                    intent.putExtra("chapternum",crosstalkEntity.getHot().get(i).getAlbumChapter());
                                    intent.putExtra("fansnum",crosstalkEntity.getHot().get(i).getPlayNumber());
                                    intent.putExtra("worksimg",crosstalkEntity.getHot().get(i).getAlbumCover());
                                    //                        intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
                                    context.startActivity(intent);
                                }
                            });
                        TextView tv_more = (TextView) view.findViewById(R.id.more);
                        tv_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, BookTypeActivity.class);
                                intent.putExtra("title","全部");
                                intent.putExtra("url", Constant.XALL );
                                context.startActivity(intent);
                            }
                        });
                        break;
                    case 1:
                        view = LayoutInflater.from(context).inflate(R.layout.item_specialty,null);
                            mlv = (MyListView) view.findViewById(R.id.specialLv);
                            mlv.setAdapter(specialAdapter);
                        specialAdapter.setDatas(crosstalkEntity.getSpecialty());
                        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(context,ArtWorksActivity.class);
                                intent.putExtra("albumid", crosstalkEntity.getSpecialty().get(i).getAlbumId());
                                intent.putExtra("worksname",crosstalkEntity.getSpecialty().get(i).getAlbumName());
                                intent.putExtra("chapternum",crosstalkEntity.getSpecialty().get(i).getAlbumChapter());
                                intent.putExtra("fansnum",crosstalkEntity.getSpecialty().get(i).getPlayNumber());
                                intent.putExtra("worksimg",crosstalkEntity.getSpecialty().get(i).getAlbumCover());
                                // intent.putExtra("worksimg","http://www.mow99.com/img/album/src_200023.jpg");
                                context.startActivity(intent);
                            }
                        });
                        TextView s_more = (TextView) view.findViewById(R.id.s_more);
                        s_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, BookTypeActivity.class);
                                intent.putExtra("title","全部");
                                intent.putExtra("url", Constant.XALL );
                                context.startActivity(intent);
                            }
                        });
                        break;
                    case 2:
                        view = LayoutInflater.from(context).inflate(R.layout.item_artist,null);
                            artGridView = (NoScrollGridView) view.findViewById(R.id.artistGridView);
                            artGridView.setAdapter(inArtistAdapter);
                        inArtistAdapter.setDatas(crosstalkEntity.getArtist());
                            artGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(context,ArtistInfoActivity.class);
                                    //用intent将艺术家id及头像传送至跳转后页面
                                    intent.putExtra("artistid",crosstalkEntity.getArtist().get(i).getArtistId());
                                    intent.putExtra("artistimg",crosstalkEntity.getArtist().get(i).getArtistImg());
                                    context.startActivity(intent);
                                }
                            });
                        TextView turn = (TextView) view.findViewById(R.id.more);
                        turn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction("action.qf.intent.turn");
                                context.sendBroadcast(intent);
                            }
                        });
                        break;

                }
                view.setTag(viewHolder);
            }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
        public int getViewTypeCount() {
            return 3;
    }

    class ViewHolder {
        TextView tv1,tv2;

    }

}
