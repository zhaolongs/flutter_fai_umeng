
#import "UMengRecordTool.h"

@implementation UMengRecordTool


/**
 @ 页面统计 - 进入
 
 */
+ (void)umengEnterViewWithName:(NSString *)name
{
    [MobClick beginLogPageView:name];
}



/**
 @ 页面统计 - 退出
 
 */
+ (void)umengOutViewWithName:(NSString *)name
{
    [MobClick endLogPageView:name];
}

/**
 @ 错误信息统计 暂无实现
 
 */
+(void)umengErrorMessage:(NSString *)errorMessage{
    
}

/**
 @ 计数事件统计
 
 */
+ (void)umengEventCountWithId:(NSString *)eventId
{
    [MobClick event:eventId];
}



/**
 @ 计算事件统计
 
 */
+ (void)umengEventCalculatWithId:(NSString *)eventId attributes:(NSDictionary *)attributes number:(id)number
{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionaryWithDictionary:attributes];
    if (number) {
        NSString *numberKey = @"__ct__";
        [mutableDictionary setObject:[number stringValue] forKey:numberKey];
    }
    
    [MobClick event:eventId attributes:mutableDictionary];
}



/**
 @ ScrollView 已滚动/浏览的百分比
 
 */
+ (void)umengEventScrollViewWithId:(NSString *)eventId attributes:(NSDictionary *)attributes scrollview:(UIScrollView *)scrollview isVertical:(BOOL)isVertical
{
    
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionaryWithDictionary:attributes];
    
    CGFloat x = scrollview.contentOffset.x;
    CGFloat y = scrollview.contentOffset.y;
    
    CGFloat width = scrollview.contentSize.width;
    CGFloat height = scrollview.contentSize.height;
    
    CGFloat superVWidth = scrollview.superview.frame.size.width;
    CGFloat superVHeight = scrollview.superview.frame.size.height;
    
    if (isVertical) { //竖直
        
        int yInt;
        if (height <= superVHeight)
        {
            yInt = 100;
            
        }else
        {
            yInt = ((y+superVHeight)/height) *100;
        }
        NSString *numberKey = @"__ct__";
        [mutableDictionary setObject:[NSString stringWithFormat:@"%d",yInt] forKey:numberKey];
        
    }else{
        
        int xInt;
        if (width <= superVWidth)
        {
            xInt = 100;
            
        }else{
            xInt = ((x+superVWidth)/width) *100;
        }
        NSString *numberKey = @"__ct__";
        [mutableDictionary setObject:[NSString stringWithFormat:@"%d",xInt] forKey:numberKey];
    }
    
    [MobClick event:eventId attributes:mutableDictionary];
    
    
}

@end

