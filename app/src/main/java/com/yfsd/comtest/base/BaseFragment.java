package com.yfsd.comtest.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yfsd.comtest.MyApp;

/**
 * Created by FTX
 * on 2017/12/4.
 * 作用:
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    protected P presenter;
    public Context context;
    private MyApp mApplication;
    private View mRootView;
    protected BaseActivity mActivity;


    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mApplication = (MyApp) getActivity().getApplication();  //和 BaseActivity 一样
        setupActivityComponent(MyApp.getInstances());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroyView();
    }

    public abstract P initPresenter();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView = inflater.inflate(setLayout(), container, false);//和 BaseActivity 一样，layout() 由子类实现，提供布局。
        }


        init(mRootView,savedInstanceState);

        return mRootView;
    }

    protected abstract void init(View mRootView, Bundle savedInstanceState);

    public abstract int setLayout();  //提供给子类实现的抽象类
    public abstract void setupActivityComponent(BaseApplication appComponent);

}
