package com.yfsd.comtest.base;

import android.app.Application;

/**
 * Created by FTX on 2017/12/4.
 */

public class BaseApplication extends Application {

    private static BaseApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    /**
     * 取得Application单件
     *
     * @return
     */
    public  static BaseApplication getInstance() {
        if (myApplication == null) {
            myApplication = new BaseApplication();
        }
        return myApplication;
    }

}
