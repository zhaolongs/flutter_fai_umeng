import 'package:flutter/material.dart';
import 'package:flutter_fai_umeng/flutter_fai_umeng.dart';

void main() {
  print("消息测试 main");
//  WidgetsFlutterBinding.ensureInitialized();
  ///友盟的初始化
  ///参数一 appkey
  ///参数二 推送使用的pushSecret
  ///参数三 是否打开调试日志

  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _result = '暂无数据';

  @override
  void initState() {
    super.initState();

    initUmeng();

    /// 监听原生消息
    FlutterFaiUmeng.receiveMessage((Map<dynamic, dynamic> message) {
      print("监听原生消息 " + message.toString());
      setState(() {
        _result = message.toString();
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('测试友盟统计 '),
        ),
        body: Center(
            child: Column(
          children: <Widget>[
            TextButton(
              onPressed: () {
                FlutterFaiUmeng.uMengPageStart("测试页面1");
              },
              child: Text("统计页面进入"),
            ),
            TextButton(
              onPressed: () {
                FlutterFaiUmeng.uMengPageEnd("测试页面1");
              },
              child: Text("统计页面结束"),
            ),
            TextButton(
              onPressed: () {
                FlutterFaiUmeng.uMengEventClick("login");
              },
              child: Text("统计按钮点击事件"),
            ),
            TextButton(
              onPressed: () {
                var flag = 1 / 0;
                print(flag);
              },
              child: Text("测试异常"),
            ),
            TextButton(
              onPressed: () {
                FlutterFaiUmeng.uMengError("有错误了");
              },
              child: Text("测试异常上报"),
            ),
            TextButton(
              onPressed: () async {
                var result = await FlutterFaiUmeng.pushInit();
                setState(() {
                  _result = result?['result']?.toString() ?? "";
                });
              },
              child: Text('注册通知'),
            ),
            TextButton(
              onPressed: () async {
                var result =
                    await FlutterFaiUmeng.setAlias(alias: '17700000000');
                setState(() {
                  _result = result['result'].toString();
                });
              },
              child: Text('设置别名'),
            ),
            Expanded(
              child: Text(_result),
            ),
          ],
        )),
      ),
    );
  }

  void initUmeng() {
    FlutterFaiUmeng.uMengInit("5dcfb8f84ca357f70e000b0a",
        pushSecret: "5cb4fc014c143a77fb85cb17edd807a2", logEnabled: true);
  }
}
