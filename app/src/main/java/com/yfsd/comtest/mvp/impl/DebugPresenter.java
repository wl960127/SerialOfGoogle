package com.yfsd.comtest.mvp.impl;


import com.yfsd.comtest.base.BasePresenterImpl;
import com.yfsd.comtest.mvp.cantact.DebugContact;

/**
 * Created by wl
 * on 2018/5/21.
 * 作用:
 */

public class DebugPresenter extends BasePresenterImpl<DebugContact.view> implements DebugContact.presenter {

    public DebugPresenter(DebugContact.view view) {
        super(view);
    }

    @Override
    public void unDisposable() {

    }
}
