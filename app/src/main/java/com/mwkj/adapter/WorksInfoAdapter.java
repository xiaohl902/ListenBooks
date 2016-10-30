package com.mwkj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mwkj.activity.R;
import com.mwkj.entity.ArtWorksEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/10/28.
 */
public class WorksInfoAdapter extends RecyclerView.Adapter<WorksInfoAdapter.WorksHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<ArtWorksEntity.ChaptersBean> worklist;
//    private Map<Integer, ArtWorksEntity.ChaptersBean> cacheMap;//缓存当前checkbox的选中状态

    //是否显示单选框,默认false
    private boolean isshowBox = false;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    //接口实例
    private RecyclerViewOnItemClickListener onItemClickListener;
    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WorksInfoAdapter(Context context) {
        this.context = context;
        this.worklist = new ArrayList<>();
//        this.cacheMap = new HashMap<>();
        initMap();
    }
    //初始化map集合,默认为不选中
    private void initMap() {
        for (int i = 0; i < worklist.size(); i++) {
            map.put(i, false);
        }
    }

    public void setDatas(List<ArtWorksEntity.ChaptersBean> worklist){
        this.worklist = worklist;
        this.notifyDataSetChanged();

    }

//    private OnWorksItemClickListener onWorksItemClickListener;
//
//    public void setOnWorksItemClickListener(OnWorksItemClickListener onWorksItemClickListener) {
//        this.onWorksItemClickListener = onWorksItemClickListener;
//    }

    @Override
    public WorksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_worksinfo_rv, parent, false);
        WorksHolder wh = new WorksHolder(inflate);
        inflate.setOnClickListener(this);
        inflate.setOnLongClickListener(this);
        return wh;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClickListener(v, (Integer) v.getTag());
        }
    }



    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }

    @Override
    public boolean onLongClick(View v) {
        //不管显示隐藏，清空状态
        initMap();
        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(v, (Integer) v.getTag());
    }

    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }


    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }


    @Override
    public void onBindViewHolder(WorksHolder holder, final int position) {
        holder.work_name_title.setText(worklist.get(position).getChapterName());
        //通知公式转换获得文件大小
        holder.work_chapter_size.setText(bytes2mb(worklist.get(position).getChapterSize()));
        holder.work_long_minute.setText(secToTime(worklist.get(position).getChapterLong()));

        //点击显示/隐藏方法
        if (isshowBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position, isChecked);
            }
        });
        // 设置CheckBox的状态
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.checkBox.setChecked(map.get(position));
    }



    // int类型秒数,转化为时间 分秒格式
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00分00秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second)+"秒";
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99时59分59秒";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second)+"秒";
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     * @param bytes
     * @return
     */
    public static String bytes2mb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        double returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .doubleValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .doubleValue();
        return (returnValue + "KB");
    }


    @Override
    public int getItemCount() {
        return worklist.size();
    }



    public class WorksHolder extends RecyclerView.ViewHolder{
        TextView work_name_title,work_chapter_size,work_long_minute;
        CheckBox checkBox;
        private View itemView;
        public WorksHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.work_name_title = (TextView) itemView.findViewById(R.id.work_name_title);
            this.work_chapter_size = (TextView) itemView.findViewById(R.id.chapter_size);
            this.work_long_minute = (TextView) itemView.findViewById(R.id.chapter_long_minute);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.work_checkbox);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            if (onWorksItemClickListener != null){
//                onWorksItemClickListener.worksitemclick(v,getAdapterPosition());
//            }
//        }
    }

//    public interface OnWorksItemClickListener{
//        void worksitemclick(View view, int position);
//    }
}
