package com.mwkj.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qf.kenlibrary.base.BaseActivity;
import com.qf.kenlibrary.util.SettingUp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设置页面
public class SetUpActivity extends BaseActivity {
    @Bind(R.id.ac_setup_finish)
    ImageView acSetupFinish;
    @Bind(R.id.ac_play)
    ImageView acPlay;
    @Bind(R.id.iv_zh)
    ImageView ivZh;
    @Bind(R.id.iv_jt)
    ImageView ivJt;
    @Bind(R.id.ac_accountSet)
    RelativeLayout acAccountSet;
    @Bind(R.id.iv_wt)
    ImageView ivWt;
    @Bind(R.id.swithbutton1)
    SettingUp swithbutton1;
    @Bind(R.id.iv_ts)
    ImageView ivTs;
    @Bind(R.id.swithbutton2)
    SettingUp swithbutton2;
    @Bind(R.id.iv_qc)
    ImageView ivQc;
    @Bind(R.id.hc)
    ImageView hc;
    @Bind(R.id.ac_eliminate)
    RelativeLayout acEliminate;
    @Bind(R.id.iv_gy)
    ImageView ivGy;
    @Bind(R.id.ac_about)
    RelativeLayout acAbout;
    @Bind(R.id.iv_gx)
    ImageView ivGx;
    @Bind(R.id.hc2)
    ImageView hc2;
    @Bind(R.id.ac_update)
    RelativeLayout acUpdate;
    @Bind(R.id.ac_btn_log)
    Button acBtnLog;
    private Intent intent;

    @Override
    protected int getContentId() {
        return R.layout.activity_setup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private AlertDialog dialog;
    @Override
    protected void init() {
        initDiaLog();//初始化DiaLog
    }



    @OnClick({R.id.ac_setup_finish, R.id.ac_play, R.id.ac_accountSet, R.id.ac_eliminate, R.id.ac_about, R.id.ac_update, R.id.ac_btn_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_setup_finish:
                this.finish();
                break;
            case R.id.ac_play:
                Toast.makeText(SetUpActivity.this, "跳转到音乐播放页面", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,PlayActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_accountSet:
                Toast.makeText(SetUpActivity.this, "账号设置", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,UserSetActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_eliminate:
                Toast.makeText(SetUpActivity.this, "清除缓存", Toast.LENGTH_SHORT).show();
                dialog.show();//显示对话框
                break;
            case R.id.ac_about:
                try {
                    PackageInfo packageInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                    String appVersion = packageInfo.versionName;
                    Toast.makeText(SetUpActivity.this, "关于魔王听书--"+appVersion, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                intent  = new Intent(this,SetAboutActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_update:
                Toast.makeText(SetUpActivity.this, "检查更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ac_btn_log:
                Toast.makeText(SetUpActivity.this, "登陆，退出登陆", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * 初始化DiaLog
     */
    private void initDiaLog() {
        // 1、创建AlertDialog构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2、通过构建起创建AlertDialog的对象
        dialog = builder.setIcon(R.drawable.ic_launcher)
                // 设置图标
                .setTitle("提示")
                .setMessage("清除缓存会删除下载专辑，是否继续")
                .setNegativeButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SetUpActivity.this, "清除了缓存", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SetUpActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }
}
