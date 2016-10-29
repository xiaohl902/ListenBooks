package com.mwkj.fragment.home_fragment;

import android.view.View;

import com.mwkj.AppContext.AppStartContext;
import com.mwkj.activity.R;
import com.mwkj.adapter.ZspecialAdapter;
import com.mwkj.entity.SpecialEntity;
import com.qf.kenlibrary.base.BaseFragment;
import com.qf.kenlibrary.widget.pullRefreshListview.XupListView;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//专题
public class SpecialFragment extends BaseFragment{
    @Bind(R.id.lv)
   XupListView mListView;
    private int pageNumber=1;
    private ZspecialAdapter adapter;
    @Override
    protected int getContentId() {
        return R.layout.fragment_home_special;
    }

    @Override
    protected void init(View view) {
        adapter = new ZspecialAdapter(getContext());
        mListView.setAdapter(adapter);

    }

    @Override
    protected void loadDatas() {
        Call<SpecialEntity> call = AppStartContext.utils.downSpecialEntity(pageNumber);
        call.enqueue(new Callback<SpecialEntity>() {
            @Override
            public void onResponse(Call<SpecialEntity> call, Response<SpecialEntity> response) {
                SpecialEntity specialEntity = response.body();
                adapter.setDatas(specialEntity.getSubjects());
            }

            @Override
            public void onFailure(Call<SpecialEntity> call, Throwable t) {

            }
        });
    }
}
