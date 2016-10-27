package com.qf.kenlibrary.widget.slidbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.qf.kenlibrary.R;


/**
 * Created by Ken on 2016/9/22.14:06
 * 侧边控件和标签控件的组合控件
 */
public class MyView extends FrameLayout implements SlidBarView.OnTouchSlidBarListener {

    private SlidBarView slidBarView;
    private LabelView lv;
    private OnSelectorListener onSelectorListener;

    public void setOnSelectorListener(OnSelectorListener onSelectorListener) {
        this.onSelectorListener = onSelectorListener;
    }

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         * 如果第二个参数不为空，但是第三个参数为false
         * 表示视图中的最外层布局的属性被保留
         *
         * 如果第二个参数不为空，但是第三个参数为true
         * 表示视图中最外层布局的属性被保留，并且该视图对象直接添加到第二个参数所表示的容器对象中
         *
         */
        LayoutInflater.from(getContext()).inflate(R.layout.custem_slidbar_labelview, this, true);
        slidBarView = (SlidBarView) this.findViewById(R.id.sbv);
        slidBarView.setOnTouchSlidBarListener(this);
        lv = (LabelView) this.findViewById(R.id.lv);
    }

    @Override
    public void selectWord(String word, int postition) {
        lv.setText(word);
        if(onSelectorListener != null){
            onSelectorListener.selectWord(word, postition);
        }
    }

    @Override
    public void unTouch() {
        lv.setText(null);
    }

    public interface OnSelectorListener{
        void selectWord(String word, int position);
    }
}
