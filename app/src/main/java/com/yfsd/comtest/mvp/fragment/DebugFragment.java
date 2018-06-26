package com.yfsd.comtest.mvp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yfsd.comtest.R;
import com.yfsd.comtest.adapter.FragAdapter;
import com.yfsd.comtest.base.BaseApplication;
import com.yfsd.comtest.base.BaseFragment;
import com.yfsd.comtest.mvp.cantact.DebugContact;
import com.yfsd.comtest.mvp.impl.DebugPresenter;
import com.yfsd.comtest.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by wl
 * on 2018/6/15.
 * 作用:
 */

public class DebugFragment extends BaseFragment<DebugContact.presenter> implements DebugContact.view {
    ViewPager debugMain;

    @Override
    public DebugContact.presenter initPresenter() {
        return new DebugPresenter(this);
    }

    @Override
    protected void init(View mRootView, Bundle savedInstanceState) {




        debugMain = mRootView.findViewById(R.id.debug_main);

        ArrayList<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new DebugTestFragment());


        FragAdapter fragAdapter = new FragAdapter(getFragmentManager(), fragmentList);
        debugMain.setAdapter(fragAdapter);

        LogUtils.e(fragmentList.size() + "  ");

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_debug;
    }

    @Override
    public void setupActivityComponent(BaseApplication appComponent) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
