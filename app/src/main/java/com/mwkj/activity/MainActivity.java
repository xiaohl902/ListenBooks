package com.mwkj.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mwkj.fragment.CommunityFragment;
import com.mwkj.fragment.ListeningFragment;
import com.mwkj.fragment.ShowFragment;
import com.mwkj.fragment.home_fragment.HomeFragment;
import com.qf.kenlibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

//主页面
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
    ImageView tab_ll3;
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
        tab_ll2.performClick();

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
                setTabImg(R.id.ac_tab_ll1);
                break;
            case R.id.ac_tab_ll2:
                //听书馆
                showFragment(R.id.ac_fl,new HomeFragment());
                setTabImg(R.id.ac_tab_ll2);
                break;
            case R.id.ac_tab_ll3:
                //跳转到音频播放页面
                Intent intent = new Intent(this,PlayActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_tab_ll4:
                //演出
                showFragment(R.id.ac_fl,new ShowFragment());
                setTabImg(R.id.ac_tab_ll4);
                break;
            case R.id.ac_tab_l5:
                //社区
                showFragment(R.id.ac_fl,new CommunityFragment());
                setTabImg(R.id.ac_tab_l5);
                break;
        }
    }


    /**
     * 设置tab图片
     */
    private void setTabImg(int id) {
//        LinearLayout tab_ll = (LinearLayout) acTabLl.getChildAt(index);
//        ImageView imageView = (ImageView) tab_ll.getChildAt(0);
//        TextView textView = (TextView) tab_ll.getChildAt(1);
        acTabImg1.setImageResource(R.mipmap.tab_listening);
        acTabImg2.setImageResource(R.mipmap.tab_home);
        acTabImg4.setImageResource(R.mipmap.tab_show);
        acTabImg5.setImageResource(R.mipmap.tab_community);

            acTabTv1.setTextColor(Color.GRAY);
            acTabTv2.setTextColor(Color.GRAY);
            acTabTv4.setTextColor(Color.GRAY);
            acTabTv5.setTextColor(Color.GRAY);
        switch (id) {
            case R.id.ac_tab_ll1:

                acTabTv1.setTextColor(Color.GREEN);
                acTabImg1.setImageResource(R.mipmap.tab_listening_on);
                break;
            case R.id.ac_tab_ll2:
                acTabTv2.setTextColor(Color.GREEN);
                acTabImg2.setImageResource(R.mipmap.tab_home_on);
                break;
            case R.id.ac_tab_ll4:
                acTabTv4.setTextColor(Color.GREEN);
                acTabImg4.setImageResource(R.mipmap.tab_show_on);
                break;
            case R.id.ac_tab_l5:
                acTabTv5.setTextColor(Color.GREEN);
                acTabImg5.setImageResource(R.mipmap.tab_community_on);
                break;
        }
    }


    /**
     * 点击返回退出APP
     */
    private long end;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //按的是返回键
            //dialog弹出
//			dialog.show();

            if(System.currentTimeMillis() - end <= 2000){
                //关闭当前应用
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                end = System.currentTimeMillis();
            }
        }

        //表示拦截back事件
        return true;
    }
}
