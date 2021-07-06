package com.studyyoun.flutter_fai_umeng.config;

import com.umeng.commonsdk.UMConfigure;

/*
 * 创建人： Created by  on 2020/9/4.
 * 创建时间：Created by  on 2020/9/4.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */
public class FaiUmenConfig {
	
	public  String appkey;
	public String channel;
	public int deviceType = UMConfigure.DEVICE_TYPE_PHONE;
	public String pushSecret;
	public boolean logEnabled;
	
	//小米通道
	public String xiomiId;
	public String xiaomiKey;
	
	//魅族通道
	//填写您在魅族后台APP对应的app id
	public String meizuAppKey;
	//填写您在魅族后台APP对应的app key
	public String meizuAppId;
	
	//OPPO通道
	//填写您在OPPO后台APP对应的app key
	public String oppoAppSecret;
	//填写您在魅族后台APP对应的app secret
	public String oppoAppKey;
	
	//华为通道，注意华为通道的初始化参数在minifest中配置
}
