import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FaiUmengWidget extends StatefulWidget {
  FaiUmengWidget();

  @override
  State<StatefulWidget> createState() {
    return AndroidUmengState();
  }
}

class AndroidUmengState extends State<FaiUmengWidget> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
    // NativeEventMessage.getDefault().unregister();
  }

  @override
  Widget build(BuildContext context) {
    return Container();
  }
}
