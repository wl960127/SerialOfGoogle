package com.yfsd.comtest.mvp.fragment;

import android.os.Bundle;
import android.view.View;

import com.yfsd.comtest.R;
import com.yfsd.comtest.base.BaseApplication;
import com.yfsd.comtest.base.BaseFragment;
import com.yfsd.comtest.base.BasePresenter;

/**
 * Created by wl
 * on 2018/6/15.
 * 作用:
 */

public class ReleaseFragment extends BaseFragment {
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void init(View mRootView, Bundle savedInstanceState) {

//        EventBus.getDefault().post(new MessageEvent("欢迎大家浏览我写的博客"));

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_release;
    }

    @Override
    public void setupActivityComponent(BaseApplication appComponent) {

    }
}
