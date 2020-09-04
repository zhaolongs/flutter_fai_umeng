package com.studyyoun.flutter_fai_umeng;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/*
 * 创建人： Created by  on 2020/9/4.
 * 创建时间：Created by  on 2020/9/4.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */
public class ShowNotificationReceiver extends BroadcastReceiver {
	private static final String TAG = "RepeatReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "ShowNotificationReceiver onReceive");
		//设置点击通知栏的动作为启动另外一个广播
		Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
		PendingIntent pendingIntent = PendingIntent.
				getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle("这就是通知的头")
				.setTicker("这是通知的ticker")
				.setContentIntent(pendingIntent)
				.setSmallIcon(android.R.drawable.ic_lock_idle_charging);
		
		Log.i("repeat", "showNotification");
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(2, builder.build());
	}
}