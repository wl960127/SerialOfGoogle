package com.yfsd.comtest.mvp.cantact;


import com.yfsd.comtest.base.BasePresenter;
import com.yfsd.comtest.base.BaseView;
import com.yfsd.comtest.mvp.bean.ActionBean;
import com.yfsd.comtest.utils.SerialPortUtils;

/**
 * Created by wl
 * on 2018/5/21.
 * 作用:
 */

public interface ActionContact {
    interface view extends BaseView {
        void getSendAction(ActionBean bean);

        void getReceiveAction(ActionBean bean);


    }

    interface presenter extends BasePresenter {
        void setSendAction(SerialPortUtils serialPort, String s);

        void setReceiveAction(String s);


    }
}
