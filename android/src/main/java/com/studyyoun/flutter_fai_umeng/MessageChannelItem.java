package com.studyyoun.flutter_fai_umeng;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.flutter.Log;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;

/**
 * 创建人： $(USER)
 * 创建时间：$(DATE)
 * 页面说明：
 * 功能性修改记录：
 */
public class MessageChannelItem {
	private String LOGTAG=MessageChannelItem.class.getSimpleName();
	private BasicMessageChannel<Object> mMessageChannel;
	public static MessageChannelItem sMMessageChannelItem = new MessageChannelItem();
	public static MessageChannelItem getInstance() {
		return sMMessageChannelItem;
	}
	private List<String> mPageStartList = new ArrayList<>();
	//获取主线程的Looper对象
	public Handler mHandler = new Handler(Looper.getMainLooper());
	
	/**
	 * 设置消息监听
	 * @param messenger
	 * @param context
	 */
	public void messageChannelFunction(final BinaryMessenger messenger, final Context context) {
		//消息接收监听
		//BasicMessageChannel （主要是传递字符串和一些半结构体的数据）
		//创建通
		mMessageChannel = new BasicMessageChannel<Object>(messenger, "flutter_and_native_um_100", StandardMessageCodec.INSTANCE);
		// 接收消息监听
		mMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler<Object>() {
			@Override
			public void onMessage(final Object o,final BasicMessageChannel.Reply<Object> reply) {
				controllMessageFunction(context,o,reply);
				
			}
		});
		
	}
	
	/**
	 * 消息处理
	 * @param context
	 * @param o
	 * @param reply
	 */
	public void controllMessageFunction(Context context,Object o, BasicMessageChannel.Reply<Object> reply){
		
		try {
			Map<Object, Object> arguments = (Map<Object, Object>) o;
			//方法名标识
			String lMethod = (String) arguments.get("method");
			
			//测试 reply.reply()方法 发消息给Flutter
			if (lMethod.equals("umInit")) {
				
				String appkey = (String) arguments.get("appkey");
				String pushSecret = (String) arguments.get("pushSecret");
				boolean logEnabled = (boolean) arguments.get("logEnabled");
				Log.d(LOGTAG,"umeng 初始化消息【 "+"appkey:"+appkey+" pushSecret:"+pushSecret+" logEnabled:"+logEnabled+"】");
				//初始化
				UmengUtils.uMengInit(context, appkey, pushSecret, logEnabled);
			}else if (lMethod.equals("umPageStart")) {
				
				String pageTitle = (String) arguments.get("pageTitle");
				if(mPageStartList.contains(pageTitle)){
					Log.d(LOGTAG,"umeng umPageStart ERR【 "+"pageTitle:"+pageTitle+"】");
				}else{
					mPageStartList.add(pageTitle);
					Log.d(LOGTAG,"umeng umPageStart【 "+"pageTitle:"+pageTitle+"】");
					//初始化
					UmengUtils.uMengPageStart(pageTitle);
				}
				
			}else if (lMethod.equals("umPageEnd")) {
				
				String pageTitle = (String) arguments.get("pageTitle");
				if(mPageStartList.contains(pageTitle)){
					mPageStartList.remove(pageTitle);
				}
				Log.d(LOGTAG,"umeng umPageEnd【 "+"pageTitle:"+pageTitle+"】");
				//初始化
				UmengUtils.uMengPageEnd(pageTitle);
			}else if (lMethod.equals("eventClick")) {
				
				String eventTitle = (String) arguments.get("eventTitle");
				String eventId =null;
				if (arguments.get("eventTitle") != null) {
					 eventId = (String) arguments.get("eventId");
				}
				
				Log.d(LOGTAG,"umeng eventTitle【 "+"eventTitle:"+eventTitle+"】");
				//初始化
				UmengUtils.uMengEventObject(context,eventTitle,eventId);
			}else if (lMethod.equals("umError")) {
				
				String errorMessage = (String) arguments.get("errorMessage");
				Log.d(LOGTAG,"umeng errorMessage【 "+"errorMessage:"+errorMessage+"】");
				//初始化
				UmengUtils.uMengError(context,errorMessage);
			}
			
			
			
		}catch (Exception e){
			Log.d(LOGTAG,"mMessageChannel 接收到消息异常 "+e.getMessage());
		}
	}
}
