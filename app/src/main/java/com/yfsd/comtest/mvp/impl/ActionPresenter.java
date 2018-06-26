package com.yfsd.comtest.mvp.impl;


import com.yfsd.comtest.base.BasePresenterImpl;
import com.yfsd.comtest.mvp.bean.ActionBean;
import com.yfsd.comtest.mvp.cantact.ActionContact;
import com.yfsd.comtest.utils.ByteUtil;
import com.yfsd.comtest.utils.LogUtils;
import com.yfsd.comtest.utils.SerialPortUtils;

/**
 * Created by wl
 * on 2018/5/21.
 * 作用:
 */

public class ActionPresenter extends BasePresenterImpl<ActionContact.view> implements ActionContact.presenter {


    private String substring;

    public ActionPresenter(ActionContact.view view) {
        super(view);
    }


    @Override
    public void unDisposable() {

    }

    @Override
    public void setSendAction(SerialPortUtils serialPortManager, String s) {

        boolean b = serialPortManager.sendSerialPort(s);

        LogUtils.e("ActionPresenter  setSendAction "+ s);

//        if (b) {
//            substring = s.substring(s.indexOf("5") + 2); // 获取AA55 之后的数据
//            switch (substring) {
//                case "10":
//                    s = "A-S  request debug";
//                    break;
//                case "11":
//                    s = "A-S quit debug";
//                    break;
//            }
//        }
        view.getSendAction(new ActionBean(0, s + " --  result -> " + b));
//        } else {
//            view.getSendAction(new ActionBean(-1, s));
//            view.getSendFail();
//        }
    }

    @Override
    public void setReceiveAction(String s) {


        LogUtils.e("ActionPresenter  setReceiveAction  "+ s);

//        if (s.startsWith(SYNC_CODE)) {
//
//
////            String substring = s.substring(s.indexOf("5") + 2); // 获取AA55 之后的数据
//            String sub = s.substring(s.indexOf("5") + 2, s.indexOf("5") + 3); //获取第 5 位数字
//            substring = s.substring(s.indexOf("5") + 2); // 获取AA55 之后的数据
//            switch (sub) {
//                case "0":
//                    view.getReceiveAction(new ActionBean(1, "----- S-A 反馈  -----"));
//                    switch (substring) {  //获取操作码
//                        case "01":
//                            s = "S-A 反馈 启动自检";
//                            break;
//                        case "02":
//                            s = "S-A 反馈 自检完成";
//                            break;
//                        case "03":
//                            s = "S-A 反馈 启动复位";
//                            break;
//                        case "04":
//                            s = "S-A 反馈 复位完成";
//                            break;
////                case "05":
////                    s = "S-A 反馈 温度值为";
////                    break;
////                case "06":
////                    s = "S-A 反馈 单个红外线状态";
////                    break;
//                        case "07":
//                            s = "S-A 反馈 启动制面";
//                            break;
//                        case "08":
//                            s = "S-A 反馈 启动加热";
//                            break;
//                        case "09":
//                            s = "S-A 反馈 启动恒温";
//                            break;
//                        case "010":
//                            s = "S-A 反馈 机械手接面就绪";
//                            break;
//                        case "012":
//                            s = "S-A 反馈 机械手抖面成功 (2次)";
//                            break;
//                        case "013":
//                            s = "S-A 反馈 机械手准备落面就绪";
//                            break;
//                        case "014":
//                            s = "S-A 反馈 机械手落面成功";
//                            break;
//                        case "015":
//                            s = "S-A 反馈 移位系统接面就绪";
//                            break;
//                        case "016":
//                            s = "S-A 反馈 落碗成功";
//                            break;
//                        case "017":
//                            s = "S-A 反馈 移动系统封口就绪";
//                            break;
//                        case "018":
//                            s = "S-A 反馈 封口成功";
//                            break;
//                        case "019":
//                            s = "S-A 反馈 移位系统推出就绪";
//                            break;
//                        case "020":
//                            s = "S-A 反馈 碗顶出就绪";
//                            break;
//                        case "021":
//                            s = "S-A 反馈 推送到出口成功";
//                            break;
//                        case "022":
//                            s = "S-A 反馈 开启外侧们成功";
//                            break;
//                        case "023":
//                            s = "S-A 反馈 面已经被取走";
//                            break;
//                        case "024":
//                            s = "S-A 反馈 关闭外侧们成功";
//                            break;
//                    }
//                    break;
//                case "2":
//                    // 获取AA55 之后的数据
//                    substring = s.substring(s.indexOf("5") + 2);
//                    view.getReceiveAction(new ActionBean(1, "----- S-A反馈调试模式  -----"));
//                    if (Integer.parseInt(substring) == 0) {
//                        s = "S-A反馈调试模式  ok   ";
//                        view.getRequestDebugResult(true);
//                    } else {
//                        s = " S-A反馈调试模式 失败 返回 ->  " + substring;
//                        view.getRequestDebugResult(false);
//                    }
//
//                    break;
//            }
//        }

        view.getReceiveAction(new ActionBean(1, s));
    }
}
