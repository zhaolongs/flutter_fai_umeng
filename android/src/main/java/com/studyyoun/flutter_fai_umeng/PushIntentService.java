package com.studyyoun.flutter_fai_umeng;

/*
 * 创建人： Created by  on 2020/9/4.
 * 创建时间：Created by  on 2020/9/4.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.json.JSONObject;

import java.util.Map;

public class PushIntentService extends Service {
	private static final String TAG = PushIntentService.class.getName();
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("PushService", "PushService onCreate");
		//用AlarmManager定时发送广播
		AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		Intent intent = new Intent(this, ShowNotificationReceiver.class);
		
		PendingIntent pendingIntent =
				PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.currentThreadTimeMillis(), pendingIntent);
		
	}
}
