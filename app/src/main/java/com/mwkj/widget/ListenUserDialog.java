package com.mwkj.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mwkj.activity.R;

/**
 * 我在听页面设置头像的DiaLog
 */
public class ListenUserDialog extends Dialog {

    TextView diaPhotograph;
    TextView diaChoice;
    TextView diaBigPicture;
    Button acFinish;

    public ListenUserDialog(Context context,OnClickDialogs onClickDialog) {
        //����dialog����ʽ
        super(context, R.style.dialog_style);
        this.onClickDialog = onClickDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog, null);
//        LinearLayout linearLayouts = (LinearLayout) view.findViewById(R.id.dialog_ll);
//        Log.d("print", "onCreate: dialog控件的高度" + linearLayouts.getMeasuredHeight());
        setContentView(R.layout.layout_dialog);

        diaPhotograph  = (TextView) findViewById(R.id.dia_photograph);
        diaChoice = (TextView) findViewById(R.id.dia_choice);
        diaBigPicture = (TextView) findViewById(R.id.dia_bigPicture);
        acFinish = (Button)findViewById(R.id.ac_finish);
        onClickDialog.onMyClick(diaPhotograph,diaChoice,diaBigPicture,acFinish);


        //����dialog�Ĵ�С
        //�����Ļ�Ŀ��
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        int height = getContext().getResources().getDisplayMetrics().heightPixels;
        Log.d("print", width + "   " + height);

//		getContext().getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();//���dialog�Ĵ��ڶ���
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width / 2;
        lp.height = (int) (height * 0.35f);
//		lp.height =linearLayouts.getMeasuredHeight();
        lp.gravity = Gravity.CENTER;
//		lp.x = 500;
//		lp.y = -300;



    }

    private OnClickDialogs onClickDialog;
    public void setOnClickDialogs(OnClickDialogs onClickDialog) {
        this.onClickDialog = onClickDialog;
    }



    public interface OnClickDialogs {
        void onMyClick(View photograph, View choice, View bigPicture,View finishBtn);
    }
}
