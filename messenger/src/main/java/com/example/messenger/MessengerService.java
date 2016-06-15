package com.example.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by chenxiaojiong on 2016/6/12.
 */
public class MessengerService extends Service {

    private static final int FROM_SERVICE = 2;

    //最好换成HandlerThread的形式
    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgfromClient) {

            Message msgToClient = Message.obtain(msgfromClient);//返回给客户端的message,也可以使用同个包里面MessengerService中的方法

            //msg 客户端传来的消息
            switch (msgfromClient.what) {
                case 1:
                    Log.i("kkkk", "msg from client:"+msgfromClient.getData().getString("client"));
                    //给message设置标记，为msg.what赋值，让客户端做针对性的处理
                    msgToClient.what = FROM_SERVICE;
                    try {
                        //模拟耗时
                        Thread.sleep(2000);
                        //打包数据
                        Bundle bundle=new Bundle();
                        bundle.putString("service","哥是远程服务端.....");
                        msgToClient.setData(bundle);
                        //回复给客户端
                        msgfromClient.replyTo.send(msgToClient);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msgfromClient);
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        //返回messenger的Binder对象
        return mMessenger.getBinder();
    }
}
