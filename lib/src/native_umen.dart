import 'dart:async';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';


class FaiUmengWidget extends StatefulWidget {

  FaiUmengWidget();

  @override
  State<StatefulWidget> createState() {

    return AndroidUmengtate();
  }


}

class AndroidUmengtate extends State<FaiUmengWidget> {

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

  }

  @override
  void dispose() {
    super.dispose();
    // NativeEventMessage.getDefault().unregister();
  }

  @override
  Widget build(BuildContext context) {

  }


}
