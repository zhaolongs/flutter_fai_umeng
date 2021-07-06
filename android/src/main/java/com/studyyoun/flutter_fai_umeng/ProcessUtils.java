package com.studyyoun.flutter_fai_umeng;

/*
 * 创建人： Created by  on 2020/9/4.
 * 创建时间：Created by  on 2020/9/4.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

import java.util.List;

public  class ProcessUtils {
	
	/**
	 * 获取当前App所有进程
	 */
	public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcessInfos(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningAppProcesses();
	}
	
	/**
	 * 判断该进程ID是否属于该进程名
	 * @param context
	 * @param pid 进程ID
	 * @param p_name 进程名
	 * @return true属于该进程名
	 */
	public static boolean isPidOfProcessName(Context context, int pid, String p_name) {
		if (p_name == null)
			return false;
		boolean isMain = false;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//遍历所有进程
		for (ActivityManager.RunningAppProcessInfo process : am.getRunningAppProcesses()) {
			if (process.pid == pid) {
				//进程ID相同时判断该进程名是否一致
				if (process.processName.equals(p_name)) {
					isMain = true;
				}
				break;
			}
		}
		return isMain;
	}
	
	/**
	 * 获取主进程名
	 * @param context 上下文
	 * @return 主进程名
	 */
	public static String getMainProcessName(Context context) throws PackageManager.NameNotFoundException {
		return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).processName;
	}
	
	/**
	 * 判断是否主进程
	 * @param context 上下文
	 * @return true是主进程
	 */
	public static boolean isMainProcess(Context context) throws PackageManager.NameNotFoundException {
		return isPidOfProcessName(context, android.os.Process.myPid(), getMainProcessName(context));
	}
	
	
	public static boolean isMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}
}
