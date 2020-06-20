# flutter_fai_umeng

友盟统计 flutter 插件 

## Getting Started

友盟统计插件

## 初始化

 ```
    ///友盟的初始化
    ///参数一 appkey
    ///参数二 推送使用的pushSecret
    ///参数三 是否打开调试日志
    FlutterFaiUmeng.uMengInit("5dcfb8f84ca357f70e000b0a",
        pushSecret: "5cb4fc014c143a77fb85cb17edd807a2", logEnabled: true);

    /// 监听原生消息
    FlutterFaiUmeng.receiveMessage((message) {
      print(message);
      setState(() {
        _result = message.toString();
      });
    });
```