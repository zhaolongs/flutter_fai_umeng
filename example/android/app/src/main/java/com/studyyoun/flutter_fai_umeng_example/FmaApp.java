package com.studyyoun.flutter_fai_umeng_example;

import com.studyyoun.flutter_fai_umeng.UmengUtils;
import com.studyyoun.flutter_fai_umeng.app.FaiUmengApplication;
import com.studyyoun.flutter_fai_umeng.config.FaiUmenConfig;
import com.umeng.commonsdk.UMConfigure;

/*
 * 创建人： Created by  on 2020/9/1.
 * 创建时间：Created by  on 2020/9/1.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */
public class FmaApp extends FaiUmengApplication {
	
	@Override
	public FaiUmenConfig getFaiUmenConfig() {
		
		FaiUmenConfig lFaiUmenConfig = new FaiUmenConfig();
		lFaiUmenConfig.appkey = "5dcfb8f84ca357f70e000b0a";
		lFaiUmenConfig.pushSecret="5cb4fc014c143a77fb85cb17edd807a2";
		lFaiUmenConfig.logEnabled = true;
		
		//小米通道
		lFaiUmenConfig.xiomiId ="2882303761518633606";
		lFaiUmenConfig.xiaomiKey="5121863350606";
		
		
		
		return lFaiUmenConfig;
	}
}
