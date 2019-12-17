//
//  UMengRecordTool.h
//  flutter_fai_umeng
//
//  Created by  androidlongs on 2019/12/17.
//


#import <Foundation/Foundation.h>

#import <UMCommon/UMCommon.h>
#import <UMCommonLog/UMCommonLogHeaders.h>
#import <UMAnalytics/MobClick.h>
@interface UMengRecordTool : NSObject


/**
 @ 页面统计 - 进入
 @param name  界面名称
 */
+ (void)umengEnterViewWithName:(NSString *)name;



/**
 @ 页面统计 - 退出
 @param name  界面名称
 */
+ (void)umengOutViewWithName:(NSString *)name;



/**
 @ 计数事件统计
 @param eventId   事件Id
 */
+ (void)umengEventCountWithId:(NSString *)eventId;

/**
 @ 错误信息统计
 @param errorMessage   错误信息日志
 */
+ (void)umengErrorMessage:(NSString *)errorMessage;

/**
 @ 计算事件统计
 @param eventId     事件Id
 @param attributes  统计内容
 @param number      统计的数
 */
+ (void)umengEventCalculatWithId:(NSString *)eventId
                      attributes:(NSDictionary *)attributes
                          number:(id)number;



/**
 ScrollView 已滚动/浏览的百分比
 
 @param eventId     事件Id
 @param attributes  内容[可不传、为nil]
 @param scrollview  滚动视图
 @param isVertical  竖直方向YES、 水平方向NO
 */
+ (void)umengEventScrollViewWithId:(NSString *)eventId
                        attributes:(NSDictionary *)attributes
                        scrollview:(UIScrollView *)scrollview
                        isVertical:(BOOL)isVertical;


@end
