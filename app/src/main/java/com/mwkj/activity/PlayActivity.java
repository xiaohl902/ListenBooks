package com.mwkj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qf.kenlibrary.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//播放音频页面
public class PlayActivity extends BaseActivity {

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
}
