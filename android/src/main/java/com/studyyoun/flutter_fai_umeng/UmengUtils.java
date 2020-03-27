package com.studyyoun.flutter_fai_umeng;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

import static android.os.Looper.getMainLooper;

/**
 * 创建人： $(USER)
 * 创建时间：$(DATE)
 * 页面说明：
 * 功能性修改记录：
 */
public class UmengUtils {
	private static String LOGTAG=UmengUtils.class.getSimpleName();
	private static UmengUtils sMUmengUtils = new UmengUtils();
	private static PushAgent sMPushAgent;
	
	
	public static UmengUtils getInstance() {
		return sMUmengUtils;
	}
	
	
	public static void uMengInit(Context context, String appkey, String pushSecret, boolean logEnabled) {
		///在本方法中使用了[getChannelName]方法来获取配置在 AndroidManifest.xml中的友盟的渠道名称
		///如使用 360加固打包启动了多渠道打包配制，getChannelName 方法将会获取到这个渠道名称
		uMengInit(context, appkey, getChannelName(context), UMConfigure.DEVICE_TYPE_PHONE, pushSecret, logEnabled);
	}
	
	/**
	 * @param channel    渠道的命名规范
	 *                   1.可以由英文字母、阿拉伯数字、下划线、中划线、空格、括号组成，可以含汉字以及其他明文字符，但是不建议使用中文命名，会出现乱码。
	 *                   2.首尾字符不可以为空格。
	 *                   3.不要使用纯数字作为渠道ID。
	 *                   4.最多256个字符。
	 *                   5.”unknown” 及其各种大小写形式，作为【友盟+】保留的字段，不可以作为渠道名。
	 *                   <p>
	 *                   在您查看数据时，渠道会作为一个数据细分的维度。
	 * @param context
	 * @param appkey
	 * @param channel
	 * @param deviceType
	 * @param pushSecret
	 */
	public static void uMengInit(Context context, String appkey, String channel, int deviceType, String pushSecret, boolean logEnabled) {
		
		/**
		 * 设置组件化的Log开关
		 * 参数: boolean 默认为false，如需查看LOG设置为true
		 * Error(打印SDK集成或运行时错误信息)。
		 * Warn(打印SDK警告信息)。
		 * Info(打印SDK提示信息)。
		 * Debug(打印SDK调试信息)。
		 */
		UMConfigure.setLogEnabled(logEnabled);
		//打开调试模式
		MobclickAgent.setDebugMode( logEnabled );
		//true打开 ，false为关闭
		//禁止默认的页面统计方式  （一般不用设置，因为默认是进行统计的）
		//MobclickAgent.openActivityDurationTrack(true);
		//错误收集（默认已经配置，而且已经打开）
		//MobclickAgent.setCatchUncaughtExceptions(true);
		
		if (channel == null || channel.equals("")) {
			channel = "test";
		}
		if (appkey == null || appkey.equals("")) {
			throw new NullPointerException("appkey is null");
		}
		
		/**
		 * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
		 * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
		 * UMConfigure.init调用中appkey和channel参数请置为null）。
		 */
		UMConfigure.init(context, appkey, channel, deviceType, pushSecret);
		// 选用LEGACY_AUTO页面采集模式
		MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
		// 支持在子进程中统计自定义事件
		UMConfigure.setProcessEvent(true);
		
		if (pushSecret != null && !pushSecret.equals("")) {
			initUpush(context);
		}
	}
	
	private static void initUpush(Context context) {
		//获取消息推送代理示例
		sMPushAgent = PushAgent.getInstance(context);
		
		
		//sdk开启通知声音
		sMPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
		// sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
		// 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
		
		sMPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
		registerPush();
		
	}
	
	/**
	 * 注册友盟推送
	 */
	private static void registerPush() {
		//注册推送服务，每次调用register方法都会回调该接口
		sMPushAgent.register(new IUmengRegisterCallback() {
			@Override
			public void onSuccess(String deviceToken) {
				//注册成功会返回deviceToken deviceToken是推送消息的唯一标志
				Log.i(LOGTAG,"注册成功：deviceToken：-------->  " + deviceToken);
			}
			@Override
			public void onFailure(String s, String s1) {
				Log.e(LOGTAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
			}
		});
	}
	
	
	/**
	 * 获取渠道名
	 *
	 * @param ctx 此处习惯性的设置为activity，实际上context就可以
	 * @return 如果没有获取成功，那么返回值为空
	 */
	public static String getChannelName(Context ctx) {
		if (ctx == null) {
			return null;
		}
		String channelName = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				//注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
					}
				}
				
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return channelName;
	}
	
	public static void onResume(Context context) {
		MobclickAgent.onResume(context);
	}
	
	public static void onPause(Context context) {
		MobclickAgent.onPause(context);
	}
	
	public static void uMengPageStart(String pageName) {
		if (pageName == null || pageName.equals("")) {
			pageName = "test";
		}
		MobclickAgent.onPageStart(pageName); //统计页面("MainScreen"为页面名称，可自定义)
	}
	
	public static void uMengPageEnd(String pageName) {
		if (pageName == null || pageName.equals("")) {
			pageName = "test";
		}
		MobclickAgent.onPageEnd(pageName);
	}
	
	public static void uMengEventObject(Context context, String eventName) {
		uMengEventObject(context,eventName,null);
	}
	public static void uMengEventObject(Context context, String eventName, String eventId) {
		Map<String, Object> music = new HashMap<String, Object>();
		if (eventName == null || eventName.equals("")) {
			eventName = "test_click";
		}
		if(eventId==null||"".equals(eventId)){
			MobclickAgent.onEvent(context, eventName);
		}else {
			music.put("eventName", eventName);//自定义参数：音乐类型，值：流行
			MobclickAgent.onEventObject(context, eventId, music);
		}
	
	}
	public static void uMengError(Context context, String errorMessage) {
		Map<String, Object> music = new HashMap<String, Object>();
		if (errorMessage == null || errorMessage.equals("")) {
			errorMessage = "未知异常统计 ";
		}
		MobclickAgent.reportError(context, errorMessage);
	}
	
	
	public static String[] getTestDeviceInfo(Context context){
		String[] deviceInfo = new String[2];
		try {
			if(context != null){
				deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
				deviceInfo[1] = DeviceConfig.getMac(context);
			}
		} catch (Exception e){
		}
		return deviceInfo;
	}
}
