import 'package:flutter/material.dart';
import 'package:flutter_fai_umeng/flutter_fai_umeng.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _result = '暂无数据';

  @override
  void initState() {
    super.initState();

    ///友盟的初始化
    ///参数一 appkey
    ///参数二 推送使用的pushSecret
    ///参数三 是否打开调试日志
    FlutterFaiUmeng.uMengInit("5df99ab50cafb2040d000468",
        pushSecret: "", logEnabled: true);

    /// 监听原生消息
    FlutterFaiUmeng.receiveMessage((message) {
      print(message);
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
            FlatButton(
              onPressed: () {
                FlutterFaiUmeng.uMengPageStart("测试页面1");
              },
              child: Text("统计页面进入"),
            ),
            FlatButton(
              onPressed: () {
                FlutterFaiUmeng.uMengPageEnd("测试页面1");
              },
              child: Text("统计页面结束"),
            ),
            FlatButton(
              onPressed: () {
                FlutterFaiUmeng.uMengEventClick("login");
              },
              child: Text("统计按钮点击事件"),
            ),
            FlatButton(
              onPressed: () {
                var falg = 1 / 0;
              },
              child: Text("测试异常"),
            ),
            FlatButton(
              onPressed: () {
                FlutterFaiUmeng.uMengError("有错误了");
              },
              child: Text("测试异常上报"),
            ),
            FlatButton(
              onPressed: () async {
                var result = await FlutterFaiUmeng.pushInit();
                setState(() {
                  _result = result['result'].toString();
                });
              },
              child: Text('注册通知'),
            ),
            FlatButton(
              onPressed: () async {
                var result = await FlutterFaiUmeng.setAlias(alias: '17700000000');
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
}
