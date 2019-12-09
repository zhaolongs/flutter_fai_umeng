package com.studyyoun.flutter_fai_umeng;

import android.content.Context;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterFaiUmengPlugin */
public class FlutterFaiUmengPlugin {
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    
    Context lContext = registrar.context();
    /**
     * 注册消息监听通道
     */
    MessageChannelItem.getInstance().messageChannelFunction(registrar.messenger(),lContext);
  }
  
  
}
