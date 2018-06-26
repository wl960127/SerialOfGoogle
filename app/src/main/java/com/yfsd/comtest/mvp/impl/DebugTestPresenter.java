package com.yfsd.comtest.mvp.impl;

import com.yfsd.comtest.base.BasePresenterImpl;
import com.yfsd.comtest.mvp.cantact.DebugTestContact;

/**
 * Created by wl
 * on 2018/6/21.
 * 作用:
 */

public class DebugTestPresenter extends BasePresenterImpl<DebugTestContact.view> implements DebugTestContact.presenter {


    public DebugTestPresenter(DebugTestContact.view view) {
        super(view);
    }


    @Override
    public void unDisposable() {

    }
}
