package com.mwkj.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.mwkj.entity.ArtWorksEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ${WU} on 2016/11/1.
 */
public class MyService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;

    private int index = -1;//播放的歌曲下标
    List<String> playList ;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        //全局广播
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "action.qf.notification.play":
                        //暂停/播放
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                        } else {
                            if(index == -1){
                                playPosition(0);
                            } else {
                                mediaPlayer.start();
                            }
                        }
                        break;
                    case "action.qf.notification.above":
                        above();
                        break;
                    case "action.qf.notification.next":
                        next();
                        break;
                    case "action.qf.notification.remove":
                        //移除通知后，停止播放服务
                        mediaPlayer.stop();
                        stopSelf();
                        break;
                    case "action.qf.intent.touch":
                        int prgress = intent.getIntExtra("touchProgress", -1);
                        mediaPlayer.seekTo(prgress);
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.qf.intent.touch");
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playList = new ArrayList<>();
        Bundle bundle = intent.getBundleExtra("playList");
        List<ArtWorksEntity.ChaptersBean> chapters = (List<ArtWorksEntity.ChaptersBean>) bundle.getSerializable("chapters");
        for (ArtWorksEntity.ChaptersBean chapter : chapters) {
            playList.add(chapter.getChapterLocation());
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

       @Override    //播放完成的监听
     public void onCompletion(MediaPlayer mediaPlayer) {

    }

    //播放歌曲
    public void playPosition(int position){
        if(index != position){
            index = position;
            play();
        }else {
            mediaPlayer.start();
            timer();
        }
    }
    //暂停
    public void pausePlayer(){
        mediaPlayer.pause();
    }

    //开始播放歌曲
    public void play(){
        mediaPlayer.reset();//重置播放器
        try {
            mediaPlayer.setDataSource(playList.get(index));
            mediaPlayer.prepareAsync();
//            mediaPlayer.prepare();//进入就绪状态
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    timer();
                }
            });

            //更新通知
           /* musicPic = musicList.get(index).getMusicImage();
            musicName = musicList.get(index).getMusicName();
            musicSinger = musicList.get(index).getMusicSinger();
            createNotification();
            notificationManager.notify(0x001, notification);*/

            //开启定时器

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 定时器
     */
    private Timer timer = null;
    public void timer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer.isPlaying()){
                    Intent intent = new Intent("action.qf.intent.progress");
                    intent.putExtra("progress", mediaPlayer.getCurrentPosition());
                    intent.putExtra("max", mediaPlayer.getDuration());
                    localBroadcastManager.sendBroadcast(intent);
                } else {
                    timer.cancel();
                    timer = null;
                }
            }
        }, 0, 1000);
    }
    public class MyBind extends Binder {
        /**
         * 返回Service对象
         * @return
         */
        public MyService getService(){
            return MyService.this;
        }
    }

    //上一曲
    public void above(){
        if(index == -1){
            return;
        }
        if(--index == -1){
            index = playList.size() - 1;
        }
        play();

    }

    //下一曲
    public void next(){
        if(index == -1){
            return;
        }
        if(++index == playList.size()){
            index = 0;
        }
        play();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        mediaPlayer.stop();
    }
}
