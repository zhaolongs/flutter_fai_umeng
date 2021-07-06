package com.studyyoun.flutter_fai_umeng;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.studyyoun.flutter_fai_umeng.app.FaiUmengApplication;
import com.studyyoun.flutter_fai_umeng.config.FaiUmenConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建人： $(USER)
 * 创建时间：$(DATE)
 * 页面说明：
 * 功能性修改记录：
 */
public class UmengUtils {
	private static String LOGTAG = UmengUtils.class.getSimpleName();
	protected static  PushAgent mPushAgent;
	/**
	 * @param context
	 * @param config
	 */
	public static void uMengInit(final Application context, FaiUmenConfig config) {
		
		/**
		 * 设置组件化的Log开关
		 * 参数: boolean 默认为false，如需查看LOG设置为true
		 * Error(打印SDK集成或运行时错误信息)。
		 * Warn(打印SDK警告信息)。
		 * Info(打印SDK提示信息)。
		 * Debug(打印SDK调试信息)。
		 */
		UMConfigure.setLogEnabled(config.logEnabled);
		
		String channel = config.channel;
		String appkey = config.appkey;
		int deviceType = config.deviceType;
		String pushSecret = config.pushSecret;
		if (channel == null || channel.equals("")) {
			channel =getChannelName(context);
			if (channel == null || channel.equals("")) {
				channel = "umen";
			}
		}
		if (appkey == null || appkey.equals("")) {
			throw new NullPointerException("appkey is null");
		}
		
		
		// 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
		// 参数一：当前上下文context；
		// 参数二：应用申请的Appkey（需替换）；
		// 参数三：渠道名称；
		// 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
		// 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
		UMConfigure.init(context, appkey, channel, deviceType, pushSecret);
		
		
		if (pushSecret != null && !pushSecret.equals("")) {
			initUpush(context, config);
		}
	}
	
	private static void initUpush(Application context, FaiUmenConfig config) {
		
		//获取消息推送代理示例
		 mPushAgent = PushAgent.getInstance(context);
		
//		mPushAgent.setPushIntentServiceClass(FaiUMengPushIntentService.class);
		
		
		//注册推送服务，每次调用register方法都会回调该接口
		mPushAgent.register(new IUmengRegisterCallback() {
			
			@Override
			public void onSuccess(String deviceToken) {
				//注册成功会返回deviceToken deviceToken是推送消息的唯一标志
				Log.i(LOGTAG, "注册成功：deviceToken：-------->  " + deviceToken);
				MessageChannelItem.getInstance().sendRegisterNotification(1,deviceToken);
			}
			
			@Override
			public void onFailure(String s, String s1) {
				Log.e(LOGTAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
				MessageChannelItem.getInstance().sendRegisterNotification(2, "s:" + s + ",s1:" + s1);
			}
		});

		
		/**
		 * 初始化厂商通道
		 */
		if (config.xiomiId != null && !config.xiomiId.equals("")) {
			//小米通道
			MiPushRegistar.register(context, config.xiomiId, config.xiaomiKey);
		}
		//华为通道，注意华为通道的初始化参数在minifest中配置
//		HuaWeiRegister.register(context);
		
		if (config.meizuAppId != null && !config.meizuAppId.equals("")) {
			//魅族通道
			MeizuRegister.register(context, config.meizuAppId, config.meizuAppKey);
		}
		if (config.oppoAppKey != null && !config.oppoAppKey.equals("")) {
//        //OPPO通道
			OppoRegister.register(context, config.oppoAppKey, config.oppoAppSecret);
		}

//        //VIVO 通道，注意VIVO通道的初始化参数在minifest中配置
		VivoRegister.register(context);
		
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
		uMengEventObject(context, eventName, null);
	}
	
	public static void uMengEventObject(Context context, String eventName, String eventId) {
		Map<String, Object> music = new HashMap<String, Object>();
		if (eventName == null || eventName.equals("")) {
			eventName = "test_click";
		}
		if (eventId == null || "".equals(eventId)) {
			MobclickAgent.onEvent(context, eventName);
		} else {
			music.put("eventName", eventName);//自定义参数：音乐类型，值：流行
//			MobclickAgent.onEventObject(context, eventId, music);
		}
		
	}
	
	public static void uMengError(Context context, String errorMessage) {
		Map<String, Object> music = new HashMap<String, Object>();
		if (errorMessage == null || errorMessage.equals("")) {
			errorMessage = "未知异常统计 ";
		}
		MobclickAgent.reportError(context, errorMessage);
	}
	
	
	public static String[] getTestDeviceInfo(Context context) {
		String[] deviceInfo = new String[2];
		try {
			if (context != null) {
				deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
				deviceInfo[1] = DeviceConfig.getMac(context);
			}
		} catch (Exception e) {
		}
		return deviceInfo;
	}
	
	
}
