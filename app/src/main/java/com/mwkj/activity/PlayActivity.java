package com.mwkj.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qf.kenlibrary.base.BaseActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//播放音频页面
public class PlayActivity extends BaseActivity implements MediaPlayer.OnPreparedListener {

    MediaPlayer mediaPlayer;

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
    SeekBar viewBottomSeek;

    @Override
    protected int getContentId() {
        return R.layout.activity_play;
    }

    /**
     *  intent.putExtra("playtitle",chapters.get(position).getChapterName());
     intent.putExtra("playartist",artistName);

     String chapterurl = chapters.get(position).getChapterLocation();
     String preurl = chapterurl.substring(0,chapterurl.lastIndexOf("/")+1);
     String laststring = chapterurl.substring(chapterurl.lastIndexOf("/")+1);
     //                    String urlnum = laststring.split(".")[0];
     String pointmp3 = laststring.substring(laststring.indexOf("."));
     String urlnum = laststring.substring(0,laststring.indexOf("."));

     intent.putExtra("urlpart1",preurl);
     intent.putExtra("urlpart2",urlnum);
     intent.putExtra("urlpart3",pointmp3);
     */
    @Override
    protected void init() {

        Intent intent = getIntent();
        showPlayTitle.setText(intent.getStringExtra("playtitle"));
        showPlayArtist.setText(intent.getStringExtra("playartist"));
        String urlpart1 = intent.getStringExtra("urlpart1");
        String urlpart2 = intent.getStringExtra("urlpart2");
        String urlpart3 = intent.getStringExtra("urlpart3");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setDataSource(urlpart1 + urlpart2 + urlpart3);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.show_back_select, R.id.show_play_share, R.id.show_play_img, R.id.show_history_player, R.id.show_download_player, R.id.show_collection_player, R.id.show_timming_player, R.id.show_playlist_player, R.id.pre_player, R.id.play_player, R.id.next_player})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_back_select:
                finish();
                break;
            case R.id.show_play_share:
                break;
            case R.id.show_play_img:
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
                break;
            case R.id.play_player:
                break;
            case R.id.next_player:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }
}
