package com.yfsd.comtest.utils;

import com.yfsd.comtest.mvp.bean.ActionBean;

/**
 * Created by wl
 * on 2018/6/15.
 * 作用:
 */

public class MessageEvent {
    private ActionBean actionBean;

    public MessageEvent(ActionBean actionBean) {
        this.actionBean = actionBean;
    }

    public ActionBean getActionBean() {
        return actionBean;
    }

    public void setActionBean(ActionBean actionBean) {
        this.actionBean = actionBean;
    }
}
