package com.yfsd.comtest.mvp.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yfsd.comtest.MyApp;
import com.yfsd.comtest.R;
import com.yfsd.comtest.adapter.ActionAdapter;
import com.yfsd.comtest.base.BaseActivity;
import com.yfsd.comtest.mvp.bean.ActionBean;
import com.yfsd.comtest.mvp.cantact.ActionContact;
import com.yfsd.comtest.mvp.fragment.DebugFragment;
import com.yfsd.comtest.mvp.fragment.ReleaseFragment;
import com.yfsd.comtest.mvp.impl.ActionPresenter;
import com.yfsd.comtest.utils.ByteUtil;
import com.yfsd.comtest.utils.LogUtils;
import com.yfsd.comtest.utils.MessageEvent;
import com.yfsd.comtest.utils.SerialPortUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActionActivity extends BaseActivity<ActionContact.presenter> implements ActionContact.view {

    @BindView(R.id.rec_action)
    RecyclerView recAction;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.switch_action)
    Switch switchAction;

    List<ActionBean> actionList = new ArrayList<>();
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.btn_clear)
    Button btnClear;

    private  static  final   int REFRESH = 0X11;
    private  static  final   int DEBUG_MODE =0X22;
    private  static  final   int RELEASE_MODE =0X33;
    private  static  final   String ANDROID_DEBUG_STM ="0X44";
    private  static  final   String ANDROID_DEBUG_STM_EXIT ="0X55";

    //    private ActionAdapterTest mAdapter;
    private ActionAdapter mAdapter;
    private ReleaseFragment releaseFragment;
    private DebugFragment debugFragment;
    private SerialPortUtils serialPortUtils;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case REFRESH:
                    upDateRecycle();
                    break;
                case DEBUG_MODE:
                    //实例 DebugFragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    if (null != releaseFragment && !releaseFragment.isHidden()) {
                        fragmentTransaction.hide(releaseFragment);
                        LogUtils.e(" releaseFragment 可见  =》 设为不可见 ");
                    }
                    if (null == debugFragment) {
                        debugFragment = new DebugFragment();
                        fragmentTransaction.add(R.id.rl_main, debugFragment);
                        LogUtils.e(" debugFragment 可见  =》 debugFragment 设为可见 ");
                    }
                    fragmentTransaction.show(debugFragment);

                    fragmentTransaction.commit();

                    presenter.setSendAction(serialPortUtils, ANDROID_DEBUG_STM); //发送 进入调试模式指令

                    break;

                case RELEASE_MODE:
                    //实例 ReleaseFragment
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    //初始化界面
                    if (null != debugFragment && !debugFragment.isHidden()) {
                        fragmentTransaction.hide(debugFragment);
                        LogUtils.e(" debug可见  =》 隐藏debug");
                    }

                    if (null == releaseFragment) {
                        releaseFragment = new ReleaseFragment();
                        fragmentTransaction.add(R.id.rl_main, releaseFragment);
                    }
                    LogUtils.e(" releaseFragment  =》 设为可见 ");
                    fragmentTransaction.show(releaseFragment);

                    fragmentTransaction.commit();

                    presenter.setSendAction(serialPortUtils, ANDROID_DEBUG_STM_EXIT); //发送 退出调试模式指令

                    break;
            }
        }
    };

    private void upDateRecycle() {
        mAdapter.notifyDataSetChanged();
        if (actionList != null) {
            recAction.scrollToPosition(mAdapter.getItemCount() - 1);
        }


    }

    private boolean openSerialPort;


    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        //注册监听 EventBus 消息
        EventBus.getDefault().register(this);

        //输出可视化log的操作
        recAction.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new ActionAdapterTest(this, actionList);
        mAdapter = new ActionAdapter(this, actionList);
        recAction.setAdapter(mAdapter);

        if (null == serialPortUtils) {
            serialPortUtils = MyApp.serialPortUtils;
        }


        openSerialPort = serialPortUtils.openSerialPort();

        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                String hexStr = ByteUtil.bytes2HexStr(buffer, 0, size);
//                LogUtils.e(hexStr + " " + size);
//
                presenter.setReceiveAction(hexStr.trim());
            }
        });


        //根据  Switch 切换 fragment (ReleaseFragment   DebugFragment )
        switchAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Message message = Message.obtain();
                    message.arg1 = RELEASE_MODE;
                    handler.sendMessage(message);
                    tvAction.setText("正式");

                } else {
                    tvAction.setText(R.string.Debug);
                    Message message = Message.obtain();
                    message.arg1 = DEBUG_MODE;
                    handler.sendMessage(message);
                    tvAction.setText("DEBUG");

                }
            }
        });


        if (switchAction.isChecked()) {
            LogUtils.e("正式功能");
        } else {
            LogUtils.e("Debug");
            tvAction.setText(R.string.Debug);
            Message message = Message.obtain();
            message.arg1 = DEBUG_MODE;
            handler.sendMessage(message);
        }


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_action;
    }

    @Override
    public ActionContact.presenter initPresenter() {
        return new ActionPresenter(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        if (!openSerialPort) {
            serialPortUtils.openSerialPort();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (openSerialPort && null != serialPortUtils) {
            serialPortUtils.closeSerialPort();
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (openSerialPort && null != serialPortUtils) {
            serialPortUtils.closeSerialPort();
        }

        super.onDestroy();
    }


    //加入 显示发送的消息
    @Override
    public void getSendAction(ActionBean sendAction) {
        actionList.add(sendAction);

        Message message = Message.obtain();
        message.arg1 = REFRESH;
        handler.sendMessage(message);
    }

    //加入  显示返回的数据
    @Override
    public void getReceiveAction(ActionBean receiveAction) {
        actionList.add(receiveAction);

        LogUtils.e("接收到信息 " + receiveAction.getAction());

        Message message = Message.obtain();
        message.arg1 = REFRESH;
        handler.sendMessage(message);
    }

    //fragment 传过来的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {


        ActionBean messageBean = messageEvent.getActionBean();
//
        if (messageBean.getType() == 0) {
            presenter.setSendAction(serialPortUtils, messageBean.getAction());
        } else if (messageBean.getType() == 1) {
            presenter.setReceiveAction(messageBean.getAction());
        }
    }

    @OnClick(R.id.btn_clear)
    public void onViewClicked() {
        if (null != actionList) {
            actionList.clear();
        }
        upDateRecycle();
    }
}
