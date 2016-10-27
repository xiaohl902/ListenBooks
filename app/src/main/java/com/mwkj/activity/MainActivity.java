package com.mwkj.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mwkj.fragment.CommunityFragment;
import com.mwkj.fragment.home_fragment.HomeFragment;
import com.mwkj.fragment.ListeningFragment;
import com.mwkj.fragment.ShowFragment;
import com.qf.kenlibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.ac_fl)
    FrameLayout acFl;
    @Bind(R.id.ac_tab_img1)
    ImageView acTabImg1;
    @Bind(R.id.ac_tab_tv1)
    TextView acTabTv1;
    @Bind(R.id.ac_tab_ll1)
    LinearLayout tab_ll1;
    @Bind(R.id.ac_tab_img2)
    ImageView acTabImg2;
    @Bind(R.id.ac_tab_tv2)
    TextView acTabTv2;
    @Bind(R.id.ac_tab_ll2)
    LinearLayout tab_ll2;
    @Bind(R.id.ac_tab_ll3)
    LinearLayout tab_ll3;
    @Bind(R.id.ac_tab_img4)
    ImageView acTabImg4;
    @Bind(R.id.ac_tab_tv4)
    TextView acTabTv4;
    @Bind(R.id.ac_tab_ll4)
    LinearLayout tab_ll4;
    @Bind(R.id.ac_tab_img5)
    ImageView acTabImg5;
    @Bind(R.id.ac_tab_tv5)
    TextView acTabTv5;
    @Bind(R.id.ac_tab_l5)
    LinearLayout tab_ll5;
    @Bind(R.id.ac_tab_ll)
    LinearLayout acTabLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.ac_tab_ll1, R.id.ac_tab_ll2, R.id.ac_tab_ll3, R.id.ac_tab_ll4, R.id.ac_tab_l5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_tab_ll1:
                //我在听
                showFragment(R.id.ac_fl,new ListeningFragment());
                setTabImg(0);
                break;
            case R.id.ac_tab_ll2:
                //听书馆
                showFragment(R.id.ac_fl,new HomeFragment());
                setTabImg(1);
                break;
            case R.id.ac_tab_ll3:
                //跳转到音频播放页面
                Intent intent = new Intent(this,PlayActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_tab_ll4:
                //演出
                showFragment(R.id.ac_fl,new ShowFragment());
                setTabImg(3);
                break;
            case R.id.ac_tab_l5:
                //社区
                showFragment(R.id.ac_fl,new CommunityFragment());
                setTabImg(4);
                break;
        }
    }


    /**
     * 设置tab图片
     */
    private void setTabImg(int index) {
        LinearLayout tab_ll = (LinearLayout) acTabLl.getChildAt(index);
        ImageView imageView = (ImageView) tab_ll.getChildAt(0);
        TextView textView = (TextView) tab_ll.getChildAt(1);
        acTabImg1.setImageResource(R.mipmap.tab_listening);
        acTabImg2.setImageResource(R.mipmap.tab_home);
        acTabImg4.setImageResource(R.mipmap.tab_show);
        acTabImg5.setImageResource(R.mipmap.tab_community);

            acTabTv1.setTextColor(Color.GRAY);
            acTabTv2.setTextColor(Color.GRAY);
            acTabTv4.setTextColor(Color.GRAY);
            acTabTv5.setTextColor(Color.GRAY);
        switch (index) {
            case 0:
                textView.setTextColor(Color.GREEN);
                imageView.setImageResource(R.mipmap.tab_listening_on);
                break;
            case 1:
                textView.setTextColor(Color.GREEN);
                imageView.setImageResource(R.mipmap.tab_home_on);
                break;
            case 3:
                textView.setTextColor(Color.GREEN);
                imageView.setImageResource(R.mipmap.tab_show_on);
                break;
            case 4:
                textView.setTextColor(Color.GREEN);
                imageView.setImageResource(R.mipmap.tab_community_on);
                break;
        }
    }
}
