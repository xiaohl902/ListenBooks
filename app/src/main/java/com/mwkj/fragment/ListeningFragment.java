package com.mwkj.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwkj.activity.R;
import com.mwkj.activity.SetUpActivity;
import com.qf.kenlibrary.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

//我在听
public class ListeningFragment extends BaseFragment {
    @Override
    protected int getContentId() {
        return R.layout.fragment_listening;
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

    @OnClick(R.id.iv_sz)
    public void onClick() {
        Intent intent = new Intent(this.getActivity(), SetUpActivity.class);
        startActivity(intent);
    }
}
