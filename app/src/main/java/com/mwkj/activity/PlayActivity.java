package com.mwkj.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mwkj.entity.ArtWorksEntity;
import com.qf.kenlibrary.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//播放音频页面
public class PlayActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {


    @Bind(R.id.pre_player)
    ImageView prePlayer;
    @Bind(R.id.next_player)
    ImageView nextPlayer;

    @Bind(R.id.show_back_select)
    TextView showBackSelect;
    @Bind(R.id.show_play_share)
    TextView showPlayShare;
    @Bind(R.id.show_play_title)
    TextView showPlayTitle;
    @Bind(R.id.show_play_artist)
    TextView showPlayArtist;
    @Bind(R.id.show_start_time_tv)
    TextView showStartTimeTv;
    @Bind(R.id.show_end_time_tv)
    TextView showEndTimeTv;
    @Bind(R.id.view_bottom_seek)
    SeekBar seekBar;
    @Bind(R.id.play_player)
    ImageView player;
    @Bind(R.id.show_play_img)
    ImageView show_img;
    private List<ArtWorksEntity.ChaptersBean> chapters;
    private AnimationDrawable animationDrawable;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private boolean isPlay = false;

    private List<ArtWorksEntity.ChaptersBean> chapters1;
    private ArtWorksEntity.ChaptersBean chapterBean;

    @Override
    protected int getContentId() {
        return R.layout.activity_play;
    }

    @Override
    protected void init() {

        //动画的初始化
        show_img.setImageResource(R.drawable.animation2);
        animationDrawable = (AnimationDrawable) show_img.getDrawable();


        //广播的初始化
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "action.qf.intent.progress":
                        if (!istouch) {
                            int progress = intent.getIntExtra("progress", -1);
                            int max = intent.getIntExtra("max", -1);
                            animationDrawable.start();
                            //设置Seekbar
                            seekBar.setMax(max);
                            seekBar.setProgress(progress);
                            //设置时间
                            Date date1 = new Date(progress);
                            Date date2 = new Date(max);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

                            showStartTimeTv.setText(simpleDateFormat.format(date1));
                            showEndTimeTv.setText(simpleDateFormat.format(date2));
                        }
                        break;
                    case "action.qf.intent.playPosition":

                        player.setImageResource(R.drawable.zanting_player);
                        chapterBean = (ArtWorksEntity.ChaptersBean) intent.getSerializableExtra("ChapterBean");
                        showPlayTitle.setText(chapterBean.getChapterName());
                        animationDrawable.start();
                        break;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.qf.intent.progress");
        intentFilter.addAction("action.qf.intent.playPosition");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
            //seekbar的监听
            seekBar.setOnSeekBarChangeListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.show_back_select, R.id.show_play_share ,R.id.show_history_player, R.id.show_download_player, R.id.show_collection_player, R.id.show_timming_player, R.id.show_playlist_player, R.id.pre_player, R.id.play_player, R.id.next_player})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_back_select:
                finish();
                break;
            case R.id.show_play_share:
                break;

            case R.id.show_history_player:
                break;
            case R.id.show_download_player:
                break;
            case R.id.show_collection_player:
                break;
            case R.id.show_timming_player:
                break;
            case R.id.show_playlist_player:
                break;
            case R.id.pre_player:
                Intent above = new Intent("action.qf.intent.above");
                localBroadcastManager.sendBroadcast(above);
                break;
            case R.id.play_player:

                if (isPlay) {
                    isPlay = false;
                    player.setImageResource(R.drawable.zanting_player);

                    animationDrawable.start();
                } else {
                    isPlay = true;
                    player.setImageResource(R.drawable.play_player);
                    animationDrawable.stop();
                }
                Intent play = new Intent("action.qf.intent.btnPlayer");
                localBroadcastManager.sendBroadcast(play);
                break;
            case R.id.next_player:
               Intent next = new Intent("action.qf.intent.next");
                localBroadcastManager.sendBroadcast(next);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private boolean istouch = false;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        showStartTimeTv.setText(simpleDateFormat.format(new Date(progress)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //开始拖动时调用
        istouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //拖动结束时调用
        int touchProgress = seekBar.getProgress();//拖动的进度
        Intent intent = new Intent("action.qf.intent.touch");
        intent.putExtra("touchProgress", touchProgress);
        localBroadcastManager.sendBroadcast(intent);
        istouch = false;
    }
}
