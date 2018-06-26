package com.yfsd.comtest.base;


/**
 *  Created by FTX
 *
 *  on 2017/12/4.
 *
 *  is use for
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    public BasePresenterImpl(V view) {
        this.view = view;
        start();
    }

    protected V view;


    @Override
    public void detach() {
        this.view = null;
        unDisposable();
    }

    @Override
    public void start() {

    }



}
