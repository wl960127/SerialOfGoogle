package com.yfsd.comtest.mvp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yfsd.comtest.R;
import com.yfsd.comtest.base.BaseApplication;
import com.yfsd.comtest.base.BaseFragment;
import com.yfsd.comtest.mvp.bean.ActionBean;
import com.yfsd.comtest.mvp.cantact.DebugTestContact;
import com.yfsd.comtest.mvp.impl.DebugTestPresenter;
import com.yfsd.comtest.utils.MessageEvent;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wl
 * on 2018/6/15.
 * 作用:
 */

public class DebugTestFragment extends BaseFragment<DebugTestContact.presenter> implements DebugTestContact.view {
    @BindView(R.id.debug_test_edt)
    EditText debugTestEdt;
    @BindView(R.id.debug_test_btn)
    Button debugTestBtn;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public DebugTestContact.presenter initPresenter() {
        return new DebugTestPresenter(this);
    }

    @Override
    protected void init(View mRootView, Bundle savedInstanceState) {

//        EventBus.getDefault().post(new MessageEvent());

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_debug_test;
    }

    @Override
    public void setupActivityComponent(BaseApplication appComponent) {

    }

    @OnClick(R.id.debug_test_btn)
    public void onViewClicked() {
        String s = debugTestEdt.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            EventBus.getDefault().post(new MessageEvent(new ActionBean(0, s)));
        } else {
            debugTestEdt.setText("输入不能为空");
        }

    }
}
