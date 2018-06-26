package com.yfsd.comtest.utils;

import android.os.SystemClock;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * Created by wl
 * on 2018/6/22.
 * 作用:
 */

public class SerialPortUtils {
    private String path = "/dev/ttyS4";
    private int baudrate = 115200;
    public boolean serialPortStatus = false; //是否打开串口标志
    public String data_;
    public boolean threadStatus; //线程状态，为了安全终止线程

    public SerialPort serialPort = null;
    public InputStream inputStream = null;
    public OutputStream outputStream = null;


//    public  interface  OpenFail(boolean);
//    public  interface  OpenSucc();

    /**
     * 打开串口
     *
     * @return serialPort串口对象
     */
    public boolean openSerialPort() {
        try {
            serialPort = new SerialPort(new File(path), baudrate, 0);
            this.serialPortStatus = true;
            threadStatus = false; //线程状态

            //获取打开的串口中的输入输出流，以便于串口数据的收发
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            if (inputStream != null) {
                new ReadThread(inputStream).start(); //开始线程监控是否有数据要接收
            }

        } catch (IOException e) {
            serialPortStatus = false;
            LogUtils.e("openSerialPort: 打开串口异常：" + e.toString());
            return false;
        }
        LogUtils.e("openSerialPort: 打开串口： "+path+"  波特率： "+baudrate);
        serialPortStatus = true;
        return true;
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        try {

            new ReadThread(inputStream).close(); //停止接收
            inputStream.close();
            outputStream.close();

            this.serialPortStatus = false;
            this.threadStatus = true; //线程状态
            serialPort.close();
        } catch (IOException e) {
            LogUtils.e("closeSerialPort: 关闭串口异常：" + e.toString());
            return;
        }
        LogUtils.e("closeSerialPort: 关闭串口成功");
    }

    /**
     * 发送串口指令（字符串）
     *
     * @param data String数据指令
     */
    public boolean sendSerialPort(String data) {
        byte[] bytes = ByteUtil.hexStr2bytes(data);

        LogUtils.e("sendSerialPort: 发送数据");

        try {
            if (bytes.length > 0) {
                outputStream.write(bytes);
                outputStream.write('\n');
                //outputStream.write('\r'+'\n');
                outputStream.flush();
                LogUtils.e("sendSerialPort: 串口数据发送成功");
            }
        } catch (IOException e) {
            LogUtils.e("sendSerialPort: 串口数据发送失败：" + e.toString());
            return false;
        }
        return true;
    }

    /**
     * 单开一线程，来读数据
     */
    private class ReadThread extends Thread {
        private BufferedInputStream mInputStream;

        public ReadThread(InputStream is) {
            mInputStream = new BufferedInputStream(is);
        }

        @Override
        public void run() {
            byte[] received = new byte[1024];
            int size;

            LogUtils.e("开始读线程");

            while (true) {

                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                try {

                    int available = mInputStream.available();

                    if (available > 0) {
                        size = mInputStream.read(received);
                        if (size > 0) {
                            onDataReceive(received, size);
                        }
                    } else {
                        // 暂停一点时间，免得一直循环造成CPU占用率过高
                        SystemClock.sleep(1);
                    }
                } catch (IOException e) {
                    LogUtils.e("读取数据失败" + e);
                    return;
                }
                //Thread.yield();
            }

            LogUtils.e("结束读进程");
        }

        /**
         * 处理获取到的数据
         *
         * @param received
         * @param size
         */
        private void onDataReceive(byte[] received, int size) {
            // TODO: 2018/3/22 解决粘包、分包等
            String hexStr = ByteUtil.bytes2HexStr(received, 0, size);
//            LogUtils.e(hexStr+"\n"+size);
            onDataReceiveListener.onDataReceive(received, size);
        }

        /**
         * 停止读线程
         */
        public void close() {

            try {
                mInputStream.close();
            } catch (IOException e) {
                LogUtils.e("异常" + e);
            } finally {
                super.interrupt();
            }
        }
    }

    //这是写了一监听器来监听接收数据
    public OnDataReceiveListener onDataReceiveListener = null;

    public static interface OnDataReceiveListener {
        public void onDataReceive(byte[] buffer, int size);
    }

    public void setOnDataReceiveListener(OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }
}
