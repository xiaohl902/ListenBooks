package com.mwkj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwkj.activity.ComSomethingActivity;
import com.mwkj.activity.R;
import com.qf.kenlibrary.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

//社区
public class CommunityFragment extends BaseFragment {
    @Override
    protected int getContentId() {
        return R.layout.fragment_community;
    }

    @OnClick({R.id.community_img1, R.id.rl1_community, R.id.rl2_community})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.community_img1:

                break;
            case R.id.rl1_community:
                Intent intent = new Intent(getActivity(), ComSomethingActivity.class);
                intent.putExtra("titlename","评书相声那些事儿");
                intent.putExtra("fourmId",2);
                startActivity(intent);
                break;
            case R.id.rl2_community:
                Intent in = new Intent(getActivity(), ComSomethingActivity.class);
                in.putExtra("fourmId",1);
                in.putExtra("titlename","问题反馈");
                startActivity(in);
                break;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
