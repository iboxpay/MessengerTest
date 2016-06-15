package com.example.chenxiaojiong.messengertext;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chenxiaojiong on 2016/6/8.
 */
public class MessengerServices extends Service {
    private static final int FROM_SERVICE = 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //获取Messenger的Binder对象
        return mMessenger.getBinder();
    }

    private Messenger mMessenger=new Messenger(new MessengerHandler());

    //设置Handler对象来接受客户端传递的信息并加以处理
    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            //Message msgToClient = Message.obtain(msg);//返回给客户端的消息，也可以使用这个方法
            switch (msg.what){
                case 1:
                    Log.i("kkkk", "msg from Client:" + msg.getData().getString("client"));
                    //获取客户端传递的Messenger对象（由于replyTo参数，那到客户端的Messenger对象）
                    Messenger messengerFromClient = msg.replyTo;

                    //打包你要发送的数据，一般使用bundle，使用message来装载信息
                    // Message message = new Message();不能使用这个方法，不然回复给客户端不知道是从哪里发来的
                    Message msgToClient=Message.obtain(null,FROM_SERVICE);

                    Bundle bundle = new Bundle();
                    bundle.putString("service","哥是服务端");
                    msgToClient.setData(bundle);
                    //回复客户端的消息
                    try {
                        messengerFromClient.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                    default:
                        super.handleMessage(msg);
                        break;
            }
        }
    }

}
