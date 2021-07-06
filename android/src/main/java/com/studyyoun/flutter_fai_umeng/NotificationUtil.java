package com.studyyoun.flutter_fai_umeng;

/*
 * 创建人： Created by  on 2020/9/4.
 * 创建时间：Created by  on 2020/9/4.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotificationUtil {
	private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
	private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
	
	//调用该方法获取是否开启通知栏权限
	public static boolean isNotifyEnabled(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			return isEnableV26(context);
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				return isEnabledV19(context);
			}else {
				return true;
			}
		}
	}
	
	/**
	 * 8.0以下判断
	 *
	 * @param context api19  4.4及以上判断
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private static boolean isEnabledV19(Context context) {
		
		AppOpsManager mAppOps =
				(AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
		
		ApplicationInfo appInfo = context.getApplicationInfo();
		String pkg = context.getApplicationContext().getPackageName();
		int uid = appInfo.uid;
		Class appOpsClass = null;
		
		try {
			appOpsClass = Class.forName(AppOpsManager.class.getName());
			
			Method checkOpNoThrowMethod =
					appOpsClass.getMethod(CHECK_OP_NO_THROW,
							Integer.TYPE, Integer.TYPE, String.class);
			
			Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
			int value = (Integer) opPostNotificationValue.get(Integer.class);
			
			return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
					AppOpsManager.MODE_ALLOWED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 8.0及以上通知权限判断
	 *
	 * @param context
	 * @return
	 */
	private static boolean isEnableV26(Context context) {
		ApplicationInfo appInfo = context.getApplicationInfo();
		String pkg = context.getApplicationContext().getPackageName();
		int uid = appInfo.uid;
		try {
			NotificationManager notificationManager = (NotificationManager)
					context.getSystemService(Context.NOTIFICATION_SERVICE);
			Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
			sServiceField.setAccessible(true);
			Object sService = sServiceField.invoke(notificationManager);
			
			Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage"
					, String.class, Integer.TYPE);
			method.setAccessible(true);
			return (boolean) method.invoke(sService, pkg, uid);
		} catch (Exception e) {
			return true;
		}
	}
}
