package com.yfsd.comtest.mvp.bean;

import com.yfsd.comtest.base.BaseBean;

/**
 * Created by wl
 * on 2018/6/14.
 * 作用:  0 表示发送    1 表示接收   -1 表示发送失败
 */

public class ActionBean extends BaseBean {
    private int type;
    private String action;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ActionBean(int type, String action) {
        this.type = type;
        this.action = action;
    }

    @Override
    public String toString() {
        return
                "type=" + type +
                        " action=" + action;
    }
}
