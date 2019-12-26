#import "FlutterFaiUmengPlugin.h"
#import <Flutter/Flutter.h>
#import <UMCommon/UMCommon.h>
#import <UMCommonLog/UMCommonLogHeaders.h>
#import <UMAnalytics/MobClick.h>
#import "UMengRecordTool.h"

@interface FlutterFaiUmengPlugin( )


@property(nonatomic,strong) NSMutableArray *pageList;
@end

@implementation FlutterFaiUmengPlugin



-(NSMutableArray *)pageList{
    if(_pageList){
        _pageList = [[NSMutableArray alloc]init];
    }
    return _pageList;
}

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    
    FlutterBasicMessageChannel*  messageChannel = [FlutterBasicMessageChannel messageChannelWithName:@"flutter_and_native_um_100" binaryMessenger:registrar.messenger];
    
    // 接收消息监听
    [messageChannel setMessageHandler:^(id message, FlutterReply callback) {
        
        NSString *method=message[@"method"];
        if ([method isEqualToString:@"umInit"]) {
            NSLog(@"flutter 调用到了 ios umInit");
            NSString *appkey=message[@"appkey"];
            NSString *pushSecret=message[@"pushSecret"];
            BOOL logEnabled =message[@"logEnabled"];
            
            NSLog(@"%@", [NSString stringWithFormat: @"umeng 初始化消息【 appkey:%@ pushSecret:%@ logEnabled:%d 】",appkey,pushSecret,logEnabled]);
            //日志
            [UMConfigure setLogEnabled:logEnabled];
            if(logEnabled){
                //开发者需要显式的调用此函数，日志系统才能工作
                [UMCommonLogManager setUpUMCommonLogManager];
            }
            //渠道设置以及友盟的初始化
            [UMConfigure initWithAppkey:appkey channel:@"App Store"];
            //设置为自动采集页面
            [MobClick setAutoPageEnabled:NO];
        }else   if ([method isEqualToString:@"umPageStart"]) {
            
            NSString *pageTitle=message[@"pageTitle"];
            if(pageTitle!=nil){
                [UMengRecordTool umengEnterViewWithName:pageTitle];
            }
        }else   if ([method isEqualToString:@"umPageEnd"]) {
            
            NSString *pageTitle=message[@"pageTitle"];
            if(pageTitle!=nil){
                [UMengRecordTool umengOutViewWithName:pageTitle];
            }
        }else   if ([method isEqualToString:@"eventClick"]) {
            
            NSString *eventTitle=message[@"eventTitle"];
            if(eventTitle!=nil){
                [UMengRecordTool umengEventCountWithId:eventTitle];
            }
        }else   if ([method isEqualToString:@"umError"]) {
            NSString *errorMessage=message[@"errorMessage"];
            [UMengRecordTool umengErrorMessage:errorMessage];
        }
        
    }];
    
}
- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
    } else {
        result(FlutterMethodNotImplemented);
    }
}

@end
