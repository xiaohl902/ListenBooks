package com.mwkj.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mwkj.entity.ArtWorksEntity;
import com.mwkj.service.MyService;
import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.util.SharedUtil;

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
    private MyService mediaService;
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
    private MyService myService;
    private int position = -1;
    private Handler handler;
    private List<ArtWorksEntity.ChaptersBean> chapters1;

    @Override
    protected int getContentId() {
        return R.layout.activity_play;
    }

    @Override
    protected void init() {
        handler = new Handler();
        //动画的初始化
        show_img.setImageResource(R.drawable.animation2);
        animationDrawable = (AnimationDrawable) show_img.getDrawable();

        Intent intent = getIntent();
        showPlayTitle.setText(intent.getStringExtra("playtitle"));
        showPlayArtist.setText(intent.getStringExtra("playartist"));

        //吴进行修改
        position = intent.getIntExtra("position", -1);

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
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.qf.intent.progress");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        if (position != -1) {
            //Log.d("log", "init: 播放第" + position + "个");
            Bundle bundle = intent.getBundleExtra("bundle");
            chapters1 = (List<ArtWorksEntity.ChaptersBean>) bundle.getSerializable("chapters");
            //把数据传给Service
            Intent intent1 = new Intent(this, MyService.class);
            intent1.putExtra("playList", bundle);
            this.startService(intent1);
            bindService(intent1, serviceConnection, Service.BIND_AUTO_CREATE);
            //seekbar的监听
            seekBar.setOnSeekBarChangeListener(this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    player.performClick();
                }
            }, 100);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBind myBind = (MyService.MyBind) iBinder;
            myService = myBind.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private boolean isPlay = false;

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
                position--;
                if (position < 0) {
                    position = chapters1.size() - 1;
                }
                myService.playPosition(position);
                   SharedUtil.putInt("position",position);
                break;
            case R.id.play_player:
                if (position == -1) {
                    Toast.makeText(PlayActivity.this, "没有可播放的内容~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isPlay) {
                    isPlay = false;
                    player.setImageResource(R.drawable.play_player);
                    myService.pausePlayer();
                    animationDrawable.stop();
                } else {
                    isPlay = true;
                    player.setImageResource(R.drawable.zanting_player);
                    myService.playPosition(position);
                    animationDrawable.start();
                }
                SharedUtil.putInt("position",position);
                break;
            case R.id.next_player:
                position++;
                if (position > chapters1.size() - 1) {
                    position = 0;
                }
                myService.playPosition(position);
                SharedUtil.putInt("position",position);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedUtil.putInt("position",position);
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

    //    @Override
    //    public void onPrepared(MediaPlayer mp) {
    //        mediaPlayer.start();
    //    }
}
