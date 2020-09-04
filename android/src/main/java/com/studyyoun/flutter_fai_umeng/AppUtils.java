package com.studyyoun.flutter_fai_umeng;

/*
 * 创建人： Created by  on 2020/9/4.
 * 创建时间：Created by  on 2020/9/4.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */   //跟App相关的辅助类

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AppUtils {
	
	/**
	 * 获取应用程序名称
	 */
	
	public static synchronized String getAppName(Context context) {
		
		try {
			
			PackageManager packageManager = context.getPackageManager();
			
			PackageInfo packageInfo = packageManager.getPackageInfo(
					
					context.getPackageName(), 0);
			
			int labelRes = packageInfo.applicationInfo.labelRes;
			
			return context.getResources().getString(labelRes);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	
	/**
	 * [获取应用程序版本名称信息]
	 *
	 * @param context
	 * @return 当前应用的版本名称
	 */
	
	public static synchronized String getVersionName(Context context) {
		
		try {
			
			PackageManager packageManager = context.getPackageManager();
			
			PackageInfo packageInfo = packageManager.getPackageInfo(
					
					context.getPackageName(), 0);
			
			return packageInfo.versionName;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	
	/**
	 * [获取应用程序版本名称信息]
	 *
	 * @param context
	 * @return 当前应用的版本名称
	 */
	
	public static synchronized int getVersionCode(Context context) {
		
		try {
			
			PackageManager packageManager = context.getPackageManager();
			
			PackageInfo packageInfo = packageManager.getPackageInfo(
					
					context.getPackageName(), 0);
			
			return packageInfo.versionCode;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return 0;
		
	}
	
	
	/**
	 * [获取应用程序版本名称信息]
	 *
	 * @param context
	 * @return 当前应用的版本名称
	 */
	
	public static synchronized String getPackageName(Context context) {
		
		try {
			
			PackageManager packageManager = context.getPackageManager();
			
			PackageInfo packageInfo = packageManager.getPackageInfo(
					
					context.getPackageName(), 0);
			
			return packageInfo.packageName;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	
	/**
	 * 获取图标 bitmap
	 *
	 * @param context
	 */
	
	public static synchronized Bitmap getBitmap(Context context) {
		
		PackageManager packageManager = null;
		
		ApplicationInfo applicationInfo = null;
		
		try {
			
			packageManager = context.getApplicationContext()
					
					.getPackageManager();
			
			applicationInfo = packageManager.getApplicationInfo(
					
					context.getPackageName(), 0);
			
		} catch (PackageManager.NameNotFoundException e) {
			
			applicationInfo = null;
			
		}
		
		Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
		
		BitmapDrawable bd = (BitmapDrawable) d;
		
		Bitmap bm = bd.getBitmap();
		
		return bm;
		
	}
	public static synchronized Drawable getDrawable(Context context) {
		
		PackageManager packageManager = null;
		
		ApplicationInfo applicationInfo = null;
		
		try {
			
			packageManager = context.getApplicationContext()
					
					.getPackageManager();
			
			applicationInfo = packageManager.getApplicationInfo(
					
					context.getPackageName(), 0);
			
		} catch (PackageManager.NameNotFoundException e) {
			
			applicationInfo = null;
			
		}
		
		Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
		
		
		
		return d;
		
	}
	
}
