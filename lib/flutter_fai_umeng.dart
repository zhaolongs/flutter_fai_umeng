import 'dart:async';

import 'package:flutter/services.dart';

typedef Future<dynamic> EventHandler(Map<String, dynamic> event);

///友盟统计
class FlutterFaiUmeng {
  String recive = "";

  //创建 BasicMessageChannel
  // flutter_and_native_100 为通信标识
  // StandardMessageCodec() 为参数传递的 编码方式
  static const messageChannel = const BasicMessageChannel(
      'flutter_and_native_um_100', StandardMessageCodec());

  //发送消息
  static Future<Map> sendMessage(Map arguments) async {
    //解析 原生发给 Flutter 的参数
    Map reply = await messageChannel.send(arguments);
    return reply;
  }

  /// [appKey] 创建应用的唯一标识
  /// [pushSecret] 使用推送时必传的参数
  /// [logEnabled] 是否开启友盟推送的日志
  static Future<Map> uMengInit(String appKey,
      {String pushSecret, bool logEnabled = false}) async {
    Map map = new Map();
    map["method"] = "umInit";
    map["appkey"] = appKey;
    map["pushSecret"] = pushSecret;
    map["logEnabled"] = logEnabled;
    return sendMessage(map);
  }

  ///友盟页面进入统计
  static Future<Map> uMengPageStart(String pageTitle) async {
    Map map = new Map();
    map["method"] = "umPageStart";
    map["pageTitle"] = pageTitle;
    return sendMessage(map);
  }

  static Future<Map> uMengPageResum(String pageTitle) async {
    Map map = new Map();
    map["method"] = "umPageResum";
    map["pageTitle"] = pageTitle;
    return sendMessage(map);
  }

  static Future<Map> uMengPagePause(String pageTitle) async {
    Map map = new Map();
    map["method"] = "umPagePause";
    map["pageTitle"] = pageTitle;
    return sendMessage(map);
  }

  ///友盟页面退出统计
  static Future<Map> uMengPageEnd(String pageTitle) async {
    Map map = new Map();
    map["method"] = "umPageEnd";
    map["pageTitle"] = pageTitle;

    return sendMessage(map);
  }

  ///友盟点击事件统计
  static Future<Map> uMengEventClick(String eventTitle,
      {String eventId}) async {
    Map map = new Map();
    map["method"] = "eventClick";
    map["eventTitle"] = eventTitle;
    if (eventId != null) {}
    map['eventId'] = eventId;
    return sendMessage(map);
  }

  ///友盟错误信息统计
  static Future<Map> uMengError(String errorMessage) async {
    Map map = new Map();
    map["method"] = "umError";
    map["errorMessage"] = errorMessage;
    return sendMessage(map);
  }

  static const _channel = const MethodChannel('flutter_and_native_um_101');
  final String flutter_log = "| UMPUSH | Flutter | ";

  Future<Map<dynamic, dynamic>> init(
    String umAppkey,
    String umSecret, {
    String miAppId,
    String miAppKey,
    String mzAppId,
    String mzAppKey,
    String opAppKey,
    String opAppSecret,
    bool debug = true,
  }) async {
    print(flutter_log + "init:");
    Map<dynamic, dynamic> params = Map();
    params["umAppkey"] = umAppkey;
    params["umSecret"] = umSecret;
    params["miAppId"] = miAppId;
    params["miAppKey"] = miAppKey;
    params["mzAppId"] = mzAppId;
    params["mzAppKey"] = mzAppKey;
    params["opAppKey"] = opAppKey;
    params["opAppSecret"] = opAppSecret;
    params["debug"] = debug;
    final Map<dynamic, dynamic> result =
        await _channel.invokeMethod('setTags', params);
    return result;
  }




  Future<Map<dynamic, dynamic>> setTags(List<String> tags) async {
    print(flutter_log + "setTags:");
    final Map<dynamic, dynamic> result =
        await _channel.invokeMethod('setTags', tags);
    return result;
  }
}
