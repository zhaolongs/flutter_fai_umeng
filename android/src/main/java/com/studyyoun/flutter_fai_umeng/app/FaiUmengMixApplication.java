package com.studyyoun.flutter_fai_umeng.app;

import android.app.Application;

import com.studyyoun.flutter_fai_umeng.UmengUtils;
import com.studyyoun.flutter_fai_umeng.config.FaiUmenConfig;

import io.flutter.app.FlutterApplication;

public class FaiUmengMixApplication {
	private static final String TAG = FaiUmengMixApplication.class.getName();
	
	
	public void init(FaiUmenConfig lFaiUmenConfig, Application application) {
		
		if (lFaiUmenConfig == null) {
			throw new RuntimeException("FaiUmenConfig 配制不可为 null ");
		}
		if (application == null) {
			throw new RuntimeException("FaiUmenConfig 配制不可为 null ");
		}
		UmengUtils.uMengInit(application, lFaiUmenConfig);
	}
	
}
