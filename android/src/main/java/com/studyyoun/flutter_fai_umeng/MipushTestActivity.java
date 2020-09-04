package com.studyyoun.flutter_fai_umeng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;
/*
 * 创建人： Created by  on 2020/8/31.
 * 创建时间：Created by  on 2020/8/31.
 * 页面说明：
 * 可关注公众号：我的大前端生涯   获取最新技术分享
 * 可关注网易云课堂：https://study.163.com/instructor/1021406098.htm
 * 可关注博客：https://blog.csdn.net/zl18603543572
 */


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

public class MipushTestActivity extends UmengNotifyClickActivity {
	private static String TAG = MipushTestActivity.class.getName();
	private TextView mipushTextView;
	
//	@Override
//	protected void onCreate(Bundle bundle) {
//		super.onCreate(bundle);
//		setContentView(R.layout.activity_mipush);
//		mipushTextView = (TextView) findViewById(R.id.mipushTextView);
//	}
//
//	@Override
//	public void onMessage(Intent intent) {
//		super.onMessage(intent);
//		final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//		Log.i(TAG, body);
//		if (!TextUtils.isEmpty(body)) {
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					mipushTextView.setText(body);
//				}
//			});
//		}
//	}
}
