package com.mwkj.util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * 音频播放的Acitivity
 */
public class PlayService extends Service implements MediaPlayer.OnPreparedListener {


    private MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化音频播放管理器
        mediaPlayer = new MediaPlayer();


    }

    private void init() {
        playWebMusic();
    }

    //----------------------------------------网络播放音乐--------------------------------------------------
    private String play_url;

    private void playWebMusic() {
        mediaPlayer.reset();//重置播放器
        if (play_url == null) return;
        try {
            mediaPlayer.setDataSource(play_url);
            mediaPlayer.prepare();//就绪状态
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();//开始播放
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        play_url = intent.getStringExtra("play_url");
        init();
//        return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MyBind myBind = new MyBind();
        return myBind;
    }


    /**
     * 绑定服务需要的类
     */
    public class MyBind extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    /**
     * MediaPlayer准备完成
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
