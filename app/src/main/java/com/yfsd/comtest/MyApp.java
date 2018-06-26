package com.yfsd.comtest;


import com.yfsd.comtest.base.BaseApplication;
import com.yfsd.comtest.utils.SerialPortUtils;

/**
 * 拿刀砍人手别抖
 * 2018/4/23
 */

public class MyApp extends BaseApplication {
    public static MyApp instances = null;
    public static SerialPortUtils serialPortUtils;


    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;

        serialPortUtils = new SerialPortUtils();


    }

    public synchronized static MyApp getInstances() {
        if (instances == null) {
            instances = new MyApp();
        }
        return instances;
    }


}
