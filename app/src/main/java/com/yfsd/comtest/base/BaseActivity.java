package com.yfsd.comtest.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;


import com.yfsd.comtest.utils.ActivityCollector;

import butterknife.ButterKnife;

/**
 *  拿刀砍人手别抖
 *  2017/12/4
 *
 *  use for :
 *
 *
 */

public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity
        implements BaseView {

    protected P presenter;

    public Context context;

    protected Bundle savedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        context = this;

        setContentView(getLayoutId());

        ButterKnife.bind(this);

        ActivityCollector.getInstance().addActivity(this);
        presenter = initPresenter();

        ButterKnife.bind(this);

        initView();


    }

    protected abstract void initView();

    public void Toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        ActivityCollector.getInstance().removeActivity(this);
        if (presenter != null) {
            presenter.detach();//在presenter中解绑释放view
            presenter = null;
        }
        super.onDestroy();
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();

}
