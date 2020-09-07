package com.studyyoun.flutter_fai_umeng;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import io.flutter.embedding.android.FlutterView;

/**
 * Developer defined push intent service.
 * Remember to call {@link com.umeng.message.PushAgent#setPushIntentServiceClass(Class)}.
 *
 * @author lucas
 */
//完全自定义处理类
public class FaiUMengPushIntentService extends UmengMessageService {
    private static final String TAG = FaiUMengPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
           
            Log.d(TAG, "message=" + message);    //消息体
            Log.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            Log.d(TAG, "title=" + msg.title);    //通知标题
            Log.d(TAG, "text=" + msg.text);    //通知内容
           
            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }
    
    
            showNotification(context,msg,intent);
            // 使用完全自定义消息来开启应用服务进程的示例代码
            // 首先需要设置完全自定义消息处理方式
            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
            // code to handle to start/stop service for app process
            JSONObject json = new JSONObject(msg.custom);
            String topic = json.getString("topic");
            Log.d(TAG, "topic=" + topic);
//            if (topic != null && topic.equals("appName:startService")) {
//                // 在友盟portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:startService"}
//                if (Helper.isServiceRunning(context, NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.startService(intent1);
//            } else if (topic != null && topic.equals("appName:stopService")) {
//                // 在友盟portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:stopService"}
//                if (!Helper.isServiceRunning(context, NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.stopService(intent1);
//            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
    
    
    // 通知栏显示当前播放信息，利用通知和 PendingIntent来启动对应的activity
    public void showNotification(Context context,UMessage msg,Intent intent) {
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true);
//        Notification mNotification = builder.build();
//        mNotification.icon = R.drawable.upsdk_cancel_normal;//notification通知栏图标
//        mNotification.defaults |= Notification.DEFAULT_SOUND;
//        mNotification.defaults |= Notification.DEFAULT_VIBRATE ;
//        mNotification.tickerText=msg.ticker;
////		//自定义布局
//		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_umeng_push);
//		contentView.setImageViewResource(R.id.notification_large_icon1, R.drawable.upsdk_btn_emphasis_normal_layer);
//		contentView.setTextViewText(R.id.notification_title, msg.title);
//		contentView.setTextViewText(R.id.notification_text, msg.text);
//		mNotification.contentView = contentView;
//		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
//				intent, PendingIntent.FLAG_UPDATE_CURRENT);//不是Intent
//        mNotification.flags = Notification.FLAG_NO_CLEAR;// 永久在通知栏里
//        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//        //使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法，但是必须定义contentIntent
//		mNotification.contentIntent = contentIntent;
//
//        mNotificationManager.notify(103, mNotification);
    
        String id = "my_channel_01";
        String name="我是渠道名字";
        
        String title = msg.title;
        
        String text = msg.text;
    
    
        Bitmap lBitmap = AppUtils.getBitmap(context);
    
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            Log.i(TAG, mChannel.toString());
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this)
                    .setChannelId(id)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(Icon.createWithBitmap(lBitmap))
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.upsdk_cancel_pressed_bg)
                    .setOngoing(true);//无效
            notification = notificationBuilder.build();
        }
    

        //自定义布局
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.activity_umeng_push);
        contentView.setImageViewResource(R.id.notification_large_icon1, R.drawable.upsdk_btn_emphasis_normal_layer);
        contentView.setTextViewText(R.id.notification_title, msg.title);
        contentView.setTextViewText(R.id.notification_text, msg.text);
        contentView.setImageViewBitmap(R.id.notification_bar_image,lBitmap);
        notification.contentView = contentView;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);//不是Intent
        notification.flags = Notification.FLAG_NO_CLEAR;// 永久在通知栏里
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        //使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法，但是必须定义contentIntent
        notification.contentIntent = contentIntent;
    
        Intent intent2= new Intent(this, MessageChannelItem.mActivity.getClass());
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    
        notification.contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
    
    
        final Notification finalNotification = notification;
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"Handler notify");
                notificationManager.notify(111123, finalNotification);
            }
        });
       
       
    }
}
