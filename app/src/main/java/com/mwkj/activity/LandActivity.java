package com.mwkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.qf.kenlibrary.base.BaseActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * 登陆界面
 */
public class LandActivity extends BaseActivity {
    @Bind(R.id.ac_land_nsername)
    EditText acLandNsername;//输入的用户名
    @Bind(R.id.ac_land_password)
    EditText acLandPassword;//输入的密码
    @Bind(R.id.ac_land_checkbox)
    CheckBox acLandCheckbox;//是否同意协议，不同意协议不让点击下一步

    private EventHandler eventHandler;//短信验证相关

    @Override
    protected int getContentId() {
        return R.layout.activity_land;
    }

    @Override
    protected void init() {
        initSMS();//初始化短信验证相关
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ac_setup_finish, R.id.ac_play, R.id.ac_land_register, R.id.ac_land_lands, R.id.ac_land_landss})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_setup_finish:
                //关闭当前页面
                this.finish();
                break;
            case R.id.ac_play:
                //跳转到播放页面
                startActivity(new Intent(this,PlayActivity.class));
                break;
            case R.id.ac_land_register:
                //点击注册
                Toast.makeText(LandActivity.this, "跳转到短信注册页面", Toast.LENGTH_SHORT).show();
                register();
                break;
            case R.id.ac_land_lands:
                //点击登陆
                if (acLandCheckbox.isClickable()){
                    Toast.makeText(LandActivity.this, "同意了协议，可以点击下一步登陆", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LandActivity.this, "没有同意协议", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ac_land_landss:
                //点击第三方登陆
                Toast.makeText(LandActivity.this, "弹出Dialog，实现第三方登陆", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    /**
     * 初始化代码验证相关
     */
    private void initSMS() {
        SMSSDK.initSDK(this, "1887318905e1a", "cb9c201e2f3bf1d528ee22d7d8c3517d");
        //                        if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                            if (i1 == SMSSDK.RESULT_COMPLETE) {
//                                boolean smart = (Boolean) o;
//                                Log.d("print", "afterEvent: " + smart);
//                                if (smart) {
//                                    //通过智能验证
//                                } else {
//                                    //依然走短信验证
//                                }
//                            }
//                        }
//#if def{lang} == cn
// 根据服务器返回的网络错误，给toast提示
//#elif def{lang} == en
// show toast according to the error code
//#endif
//错误描述
//错误代码
//do something
        eventHandler = new EventHandler() {
            @Override
            public void onRegister() {
                super.onRegister();
            }

            @Override
            public void beforeEvent(int i, Object o) {
                super.beforeEvent(i, o);
            }

            @Override
            public void afterEvent(int i, int i1, Object o) {
                super.afterEvent(i, i1, o);

                switch (i1) {
                    case SMSSDK.RESULT_COMPLETE:
                        Log.d("print", "afterEvent: 操作成功");

//                        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
//                        startActivity(intent);

//                        if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                            if (i1 == SMSSDK.RESULT_COMPLETE) {
//                                boolean smart = (Boolean) o;
//                                Log.d("print", "afterEvent: " + smart);
//                                if (smart) {
//                                    //通过智能验证
//                                } else {
//                                    //依然走短信验证
//                                }
//                            }
//                        }
                        break;
                    case SMSSDK.RESULT_ERROR:
                        Log.d("print", "afterEvent: 操作失败");
                        //#if def{lang} == cn
                        // 根据服务器返回的网络错误，给toast提示
                        //#elif def{lang} == en
                        // show toast according to the error code
                        //#endif
                        try {
                            Throwable throwable = (Throwable) o;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(LandActivity.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            //do something
                        }
                        break;
                }
            }

            @Override
            public void onUnregister() {
                super.onUnregister();
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }


    /**
     * 注册 -- 短信验证相关
     */
    private void register() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息（此方法可以不调用）
                    //registerUser(country, phone);
                }
            }
        });
        registerPage.show(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);//短信验证相关
    }

}
