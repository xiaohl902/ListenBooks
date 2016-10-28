package com.mwkj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mwkj.activity.R;
import com.mwkj.entity.CrosstalkEntity;
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
                        break;
                    case 1:
                        view = LayoutInflater.from(context).inflate(R.layout.item_specialty,null);
                            mlv = (MyListView) view.findViewById(R.id.specialLv);
                            mlv.setAdapter(specialAdapter);
                        specialAdapter.setDatas(crosstalkEntity.getSpecialty());
                        break;
                    case 2:
                        view = LayoutInflater.from(context).inflate(R.layout.item_artist,null);
                            artGridView = (NoScrollGridView) view.findViewById(R.id.artistGridView);
                            artGridView.setAdapter(inArtistAdapter);
                        inArtistAdapter.setDatas(crosstalkEntity.getArtist());
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
