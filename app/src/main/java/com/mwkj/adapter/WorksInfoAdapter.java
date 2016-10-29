package com.mwkj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwkj.activity.R;
import com.mwkj.entity.ArtWorksEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/10/28.
 */
public class WorksInfoAdapter extends RecyclerView.Adapter<WorksInfoAdapter.WorksHolder> {

    private Context context;
    private List<ArtWorksEntity.ChaptersBean> worklist;

    public WorksInfoAdapter(Context context) {
        this.context = context;
        this.worklist = new ArrayList<>();
    }

    public void setDatas(List<ArtWorksEntity.ChaptersBean> worklist){
        this.worklist = worklist;
        this.notifyDataSetChanged();

    }

    private OnWorksItemClickListener onWorksItemClickListener;

    public void setOnWorksItemClickListener(OnWorksItemClickListener onWorksItemClickListener) {
        this.onWorksItemClickListener = onWorksItemClickListener;
    }

    @Override
    public WorksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_worksinfo_rv, parent, false);
        return new WorksHolder(inflate);
    }

    @Override
    public void onBindViewHolder(WorksHolder holder, int position) {
        holder.work_name_title.setText(worklist.get(position).getChapterName());
        //通知公式转换获得文件大小
        holder.work_chapter_size.setText(bytes2mb(worklist.get(position).getChapterSize()));
        holder.work_long_minute.setText(secToTime(worklist.get(position).getChapterLong()));
//        holder.work_long_second.setText(worklist.get(position).getChapterLong()+"");
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

    public class WorksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView work_name_title,work_chapter_size,work_long_minute;
        public WorksHolder(View itemView) {
            super(itemView);
            this.work_name_title = (TextView) itemView.findViewById(R.id.work_name_title);
            this.work_chapter_size = (TextView) itemView.findViewById(R.id.chapter_size);
            this.work_long_minute = (TextView) itemView.findViewById(R.id.chapter_long_minute);
//            this.work_long_second = (TextView) itemView.findViewById(R.id.chapter_long_second);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onWorksItemClickListener != null){
                onWorksItemClickListener.worksitemclick(v,getAdapterPosition());
            }
        }
    }

    public interface OnWorksItemClickListener{
        void worksitemclick(View view, int position);
    }
}
