package com.studyyoun.flutter_fai_umeng.app;

import android.util.Log;

import com.studyyoun.flutter_fai_umeng.UmengUtils;
import com.studyyoun.flutter_fai_umeng.config.FaiUmenConfig;
import com.umeng.commonsdk.UMConfigure;

import io.flutter.app.FlutterApplication;

public abstract class FaiUmengApplication extends FlutterApplication {
	private static final String TAG = FaiUmengApplication.class.getName();
	
	@Override
	public void onCreate() {
		super.onCreate();
		FaiUmenConfig lFaiUmenConfig = getFaiUmenConfig();
		if (lFaiUmenConfig == null) {
			throw new RuntimeException("FaiUmenConfig 配制不可为 null ");
		}
		UmengUtils.uMengInit(this,lFaiUmenConfig);
	}
	
	
	public abstract FaiUmenConfig getFaiUmenConfig();
	
}
