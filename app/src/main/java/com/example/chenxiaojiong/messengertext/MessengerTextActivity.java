package com.example.chenxiaojiong.messengertext;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MessengerTextActivity extends AppCompatActivity {

    private static final int FROM_CLIENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_text);
        //指定action
        Intent intent = new Intent("messengerTest");
        //5.0之后必须显式声明
        intent.setPackage(this.getPackageName());
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
    }

    ServiceConnection mServiceConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取服务端传递过来的Messenger对象
            Messenger messenger=new android.os.Messenger(service);
            //new 个message对象，通过他向服务端发送信息(必须带上标记，让服务端可以区分)
            Message obtain = Message.obtain(null, FROM_CLIENT);
            Bundle bundle=new Bundle();
            bundle.putString("client","哥是客户端");
            obtain.setData(bundle);
            //将接受服务端的Messenger对象（就是客户端中的Messenger）通过Message的replyTo参数传递给服务端
            obtain.replyTo=mGetReplyToMessenger;
            //发送信息给服务端
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger mGetReplyToMessenger=new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    //获取服务端的响应
                    String info = msg.getData().getString("service");
                    Log.i("kkkk", "msg from Service:" + info);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
