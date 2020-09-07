package com.studyyoun.flutter_fai_umeng;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.inapp.IUmengInAppMsgCloseCallback;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.tag.TagManager;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.flutter.app.FlutterApplication;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterNativeView;

import static android.os.Looper.getMainLooper;

/**
 * FlutterFaiUmengPlugin
 */
public class FlutterFaiUmengPlugin {



    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {

        Context lContext = registrar.context();
        /**
         * 注册消息监听通道
         */
        MessageChannelItem.getInstance().messageChannelFunction(registrar.messenger(), lContext, registrar.activity());
    

    
        registrar.addViewDestroyListener(new PluginRegistry.ViewDestroyListener() {
            @Override
            public boolean onViewDestroy(FlutterNativeView view) {
                return false;
            }
        });
    }


//    private FlutterFaiUmengPlugin(Registrar registrar, MethodChannel channel) {
//        this.registrar = registrar;
//        this.channel = channel;
//        this.callbackMap = new HashMap<>();
//        instance = this;
//    }
//
//
//    @Override
//    public void onMethodCall(MethodCall call, Result result) {
//        Log.i(TAG, call.method);
//        if (call.method.equals("getPlatformVersion")) {
//            result.success("Android " + android.os.Build.VERSION.RELEASE);
//        } else if (call.method.equals("init")) {
//            init(call, result);
//        } else if (call.method.equals("addTags")) {
//            addTags(call, result);
//        } else if (call.method.equals("deleteTags")) {
//            deleteTags(call, result);
//        } else if (call.method.equals("getAllTags")) {
//            getAllTags(call, result);
//        } else if (call.method.equals("setAlias")) {
//            setAlias(call, result);
//        } else if (call.method.equals("addAlias")) {
//            addAlias(call, result);
//        } else if (call.method.equals("deleteAlias")) {
//            deleteAlias(call, result);
//        } else if (call.method.equals("stopPush")) {
//            stopPush(call, result);
//        } else if (call.method.equals("openPush")) {
//            openPush(call, result);
//        } else if (call.method.equals("setNotificaitonOnForeground")) {
//            setNotificaitonOnForeground(call, result);
//        } else if (call.method.equals("setCustomMessageStyle")) {
////            setCustomMessageStyle(call, result);
//        } else if (call.method.equals("handleMessage")) {
////            handleMessage(call, result);
//        } else if (call.method.equals("setDisplayNotificationNumber")) {
//            setDisplayNotificationNumber(call, result);
//        } else if (call.method.equals("setNotificationPlaySound")) {
//            setNotificationPlaySound(call, result);
//        } else if (call.method.equals("setNotificationLights")) {
//            setNotificationLights(call, result);
//        } else if (call.method.equals("setNotificationPlayVibrate")) {
//            setNotificationPlayVibrate(call, result);
//        } else if (call.method.equals("setNoDisturbMode")) {
//            setNoDisturbMode(call, result);
//        } else if (call.method.equals("setMuteDurationSeconds")) {
//            setMuteDurationSeconds(call, result);
//        } else if (call.method.equals("setResourcePackageName")) {
//            setResourcePackageName(call, result);
//        } else if (call.method.equals("setInAppMsgDebugMode")) {
//            setInAppMsgDebugMode(call, result);
//        } else if (call.method.equals("setSplashMessage")) {
//            setSplashMessage(call, result);
//        } else if (call.method.equals("showCardMessage")) {
//            showCardMessage(call, result);
//        } else if (call.method.equals("setPushCheck")) {
//            setPushCheck(call, result);
//        } else {
//            result.notImplemented();
//        }
//    }
//
//    // 主线程再返回数据
//    public void runMainThread(final Map<String, Object> map, final Result result, final String method) {
//        Log.d(TAG, "runMainThread:" + "map = " + map + ",method =" + method);
//        android.os.Handler handler = new Handler(getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (result == null && method != null) {
//                    channel.invokeMethod(method, map);
//                } else {
//                    result.success(map);
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 初始化UMPush
//     * 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
//     * 参数一：当前上下文context；
//     * 参数二：应用申请的Appkey（需替换）；
//     * 参数三：渠道名称；
//     * 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
//     * 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
//     *
//     * @param call
//     * @param result
//     */
//    public void init(MethodCall call, final Result result) {
//        Log.d(TAG, "setup :" + call.arguments);
//        HashMap<String, Object> map = call.arguments();
//        String umAppkey = (String) map.get("umAppkey");
//        String umSecret = (String) map.get("umSecret");
//        String miAppId = (String) map.get("miAppId");
//        String miAppKey = (String) map.get("miAppKey");
//        String mzAppId = (String) map.get("mzAppId");
//        String mzAppKey = (String) map.get("mzAppKey");
//        String opAppKey = (String) map.get("opAppKey");
//        String opAppSecret = (String) map.get("opAppSecret");
//
//        UMConfigure.init(this, umAppkey, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, umSecret);
//
//        //获取消息推送代理示例
//        mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
//                result.success("注册成功" + deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//                result.error("注册失败", s, s1);
//            }
//        });
//
//        mPushAgent.setMessageHandler(messageHandler);
//
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
//
//
//        /**
//         * 初始化厂商通道
//         */
//        //小米通道
//        MiPushRegistar.register(this, miAppId, miAppKey);
//        //华为通道，注意华为通道的初始化参数在minifest中配置
//        HuaWeiRegister.register(this);
//        //魅族通道
//        MeizuRegister.register(this, mzAppId, mzAppKey);
//        //OPPO通道
//        OppoRegister.register(this, opAppKey, opAppSecret);
//        //VIVO 通道，注意VIVO通道的初始化参数在minifest中配置
//        VivoRegister.register(this);
//
//    }
//
//
//    /**
//     * 添加标签 示例：将“标签1”、“标签2”绑定至该设备
//     *
//     * @param call
//     * @param result0
//     */
//    public void addTags(final MethodCall call, final Result result0) {
//        Log.d(TAG, "addTags: " + call.arguments);
//
//        List<String> tagList = call.arguments();
//        final Set<String> tags = new HashSet<>(tagList);
//        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
//            @Override
//            public void onMessage(boolean b, ITagManager.Result result) {
//                result0.success("addTags" + tags.toString() + b);
//
//            }
//        }, tags.toString());
//    }
//
//
//    /**
//     * 删除标签,将之前添加的标签中的一个或多个删除
//     *
//     * @param call
//     * @param result0
//     */
//    public void deleteTags(MethodCall call, final Result result0) {
//        Log.d(TAG, "deleteTags： " + call.arguments);
//
//        List<String> tagList = call.arguments();
//        final Set<String> tags = new HashSet<>(tagList);
//        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
//            @Override
//            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
//                result0.success("deleteTags" + tags.toString() + isSuccess);
//            }
//        }, "标签1", "标签2");
//    }
//
//    /**
//     * 获取服务器端的所有标签
//     *
//     * @param call
//     * @param result0
//     */
//    public void getAllTags(MethodCall call, final Result result0) {
//        Log.d(TAG, "getAllTags： ");
//        mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, List<String> result) {
//                result0.success("getAllTags:" + isSuccess);
//            }
//        });
//    }
//
//    /**
//     * 别名绑定，将某一类型的别名ID绑定至某设备，老的绑定设备信息被覆盖，别名ID和deviceToken是一对一的映射关系
//     *
//     * @param call
//     * @param result0
//     */
//    public void setAlias(MethodCall call, final Result result0) {
//        Log.d(TAG, "setAlias: " + call.arguments);
//        String alias = call.arguments();
//        mPushAgent.setAlias(alias, "自定义类型", new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//                result0.success("setAlias:" + isSuccess);
//
//            }
//        });
//
//    }
//
//    /**
//     * 别名增加，将某一类型的别名ID绑定至某设备，老的绑定设备信息还在，别名ID和device_token是一对多的映射关系
//     *
//     * @param call
//     * @param result0
//     */
//    public void addAlias(MethodCall call, final Result result0) {
//        Log.d(TAG, "addAlias: " + call.arguments);
//
//        String alias = call.arguments();
//        mPushAgent.addAlias(alias, "自定义类型", new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//                result0.success("addAlias:" + isSuccess);
//            }
//        });
//
//    }
//
//    /**
//     * 移除别名ID
//     *
//     * @param call
//     * @param result0
//     */
//    public void deleteAlias(MethodCall call, final Result result0) {
//        Log.d(TAG, "deleteAlias:");
//        String alias = call.arguments();
//        mPushAgent.deleteAlias(alias, "自定义类型", new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//                result0.success("deleteAlias:" + isSuccess);
//            }
//        });
//    }
//
//
//    /**
//     * 如果您的应用在前台，您可以设置不显示通知栏消息。默认情况下，应用在前台是显示通知的。 开发者更改前台通知显示设置后，会根据更改生效
//     * 此方法请在mPushAgent.register方法之前调用。
//     *
//     * @param call
//     * @param result
//     */
//    public void setNotificaitonOnForeground(MethodCall call, Result result) {
//        Log.d(TAG, "setNotificaitonOnForeground:");
//        boolean isForeground = call.arguments();
//        mPushAgent.setNotificaitonOnForeground(isForeground);
//    }
//
//
//    /**
//     * 在PushSDK里，UmengMessageHandler类负责处理消息，包括通知和自定义消息。其中，成员函数getNotification负责定义通知栏样式。
//     * 若SDK默认的消息展示样式不符合开发者的需求，可通过覆盖该方法来自定义通知栏展示样式
//     * 每当有通知送达时，均会回调getNotification方法，因此可以通过监听此方法来判断通知是否送达。
//     * msg.builder_id是服务器下发的消息字段，用来指定通知消息的样式
//     * <p>
//     * 消息到达时获取自定义参数。重写UmengMessageHandler类中的getNotification(Context context, UMessage msg)方法：
//     * 自定义消息
//     * for (Map.Entry entry : msg.extra.entrySet()) {
//     * Object key = entry.getKey();
//     * Object value = entry.getValue();
//     * }
//     *
//     * @param call
//     * @param result
//     */
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
////                result.success(msg.getRaw());
//                Log.d(TAG + "Message", msg.getRaw().toString());
//
//                switch (msg.builder_id) {
//                    case 1:
////                        Notification.Builder builder = new Notification.Builder(context);
////                        自定义消息样式
////              RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
////                      R.layout.notification_view);
////              myNotificationView.setTextViewText(R.id.notification_title, msg.title);
////              myNotificationView.setTextViewText(R.id.notification_text, msg.text);
////              myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
////                      getLargeIcon(context, msg));
////              myNotificationView.setImageViewResource(R.id.notification_small_icon,
////                      getSmallIconId(context, msg));
////              builder.setContent(myNotificationView)
////                      .setSmallIcon(getSmallIconId(context, msg))
////                      .setTicker(msg.ticker)
////                      .setAutoCancel(true);
//                        return null;
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//
//
//    /**
//     * 开发者可自定义用户点击通知栏时的后续动作。自定义行为的数据放在UMessage.custom字段。
//     * 在【友盟+】后台或通过API发送消息时，在“后续动作”中的“自定义行为”中输入相应的值或代码即可实现。
//     * 若开发者需要处理自定义行为，则可以重写方法dealWithCustomAction()。其中自定义行为的内容，存放在UMessage.custom中
//     * <p>
//     * 消息点击时获取自定义参数。通过重写UmengNotificationClickHandler类中的launchApp、openUrl、
//     * openActivity、dealWithCustomAction方法，均可以从msg.extra中获取自定义参数：
//     * <p>
//     * 进入Activity时获取自定义参数。若您使用Push SDK的默认设置处理通知消息，则从服务端传的自定义参数将会通过Intent传递给相应的Activity
//     * 您可以在相应的Activity中的onResume()方法内通过以下代码获得传递的参数
//     * <p>
//     * Bundle bun = getIntent().getExtras();
//     * if (bun != null) {
//     * Set<String> keySet = bun.keySet();
//     * for (String key : keySet) {
//     * String value = bun.getString(key);
//     * ...
//     * }
//     * }
//     * 注意：如果在Activity中获取自定义参数，则需要将该Activity的launchMode设置为android:launchMode=”singleTask”，并重写onNewIntent方法：
//     *
//     * @param call
//     * @param result
//     * @Override protected void onNewIntent(Intent intent) {
//     * super.onNewIntent(intent);
//     * setIntent(intent);
//     * }
//     * <p>
//     * 自定义消息不是通知，默认不会被SDK展示到通知栏上，【友盟+】推送仅负责将消息透传给SDK，其内容和展示形式是则完全可以由开发者自己定义。
//     * 若开发者要使用自定义消息，则需重在自定义Application类的onCreate() 中重写dealWithCustomMessage()方法，
//     * 自定义消息的内容存放在UMessage.custom字段里。代码如下所示：
//     * UmengMessageHandler messageHandler = new UmengMessageHandler(){
//     * @Override public void dealWithCustomMessage(final Context context, final UMessage msg) {
//     * new Handler(getMainLooper()).post(new Runnable() {
//     * @Override public void run() {
//     * // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
//     * boolean isClickOrDismissed = true;
//     * if(isClickOrDismissed) {
//     * //自定义消息的点击统计
//     * UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//     * } else {
//     * //自定义消息的忽略统计
//     * UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//     * }
//     * Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//     * }
//     * });
//     * }
//     * };
//     * mPushAgent.setMessageHandler(messageHandler);
//     */
//
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
////                result.success(msg.getRaw());
//                Log.d(TAG + "handleMessage", msg.getRaw().toString());
//
//            }
//
//
//            @Override
//            public void launchApp(Context context, UMessage msg) {
//                super.launchApp(context, msg);
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage msg) {
//                super.openUrl(context, msg);
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage msg) {
//                super.openActivity(context, msg);
//            }
//        };
//
//
////    }
//
//    /**
//     * 设置通知栏显示数量
//     * 通知栏可以设置最多显示通知的条数，当通知栏显示数目大于设置值，此时再有新通知到达时，会把旧的一条通知隐藏。
//     * 参数number可以设置为0~10之间任意整数。当参数为0时，表示不合并通知；
//     * 当该方法存在多次调用时，一最后一次调用时的设置为准。
//     *
//     * @param call
//     * @param result
//     */
//    public void setDisplayNotificationNumber(MethodCall call, Result result) {
//        Log.d(TAG, "setDisplayNotificationNumber:");
//        int number = call.arguments();
//        mPushAgent.setDisplayNotificationNumber(number);
//    }
//
//
//    /**
//     * 通知响铃、震动及呼吸灯控制
//     * 响铃、
//     * 1、服务端控制：通过服务端推送状态来设置通知到达后响铃、震动、呼吸灯的状态；
//     * 2、客户端控制：关闭服务端推送控制能力，由客户端控制通知到达后是否响铃、震动以及呼吸灯是否点亮；
//     * 3、客户端控制：客户端禁止；
//     *
//     * @param call
//     * @param result
//     */
//    public void setNotificationPlaySound(MethodCall call, Result result) {
//        Log.d(TAG, "setNotificationPlaySound:");
//        int type = call.arguments();
//        if (type == 1) {
//            mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
//        } else if (type == 2) {
//            mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//        } else {
//            mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        }
//
//    }
//
//    /**
//     * 通知响铃、震动及呼吸灯控制
//     * 呼吸灯
//     * 1、服务端控制：通过服务端推送状态来设置通知到达后响铃、震动、呼吸灯的状态；
//     * 2、客户端控制：关闭服务端推送控制能力，由客户端控制通知到达后是否响铃、震动以及呼吸灯是否点亮；
//     * 3、客户端控制：客户端禁止；
//     *
//     * @param call
//     * @param result
//     */
//    public void setNotificationLights(MethodCall call, Result result) {
//        Log.d(TAG, "setNotificationLights:");
//        int type = call.arguments();
//        if (type == 1) {
//            mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);
//        } else if (type == 2) {
//            mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//        } else {
//            mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        }
//
//    }
//
//    /**
//     * 通知响铃、震动及呼吸灯控制
//     * 震动：
//     * 1、服务端控制：通过服务端推送状态来设置通知到达后响铃、震动、呼吸灯的状态；
//     * 2、客户端控制：关闭服务端推送控制能力，由客户端控制通知到达后是否响铃、震动以及呼吸灯是否点亮；
//     * 3、客户端控制：客户端禁止；
//     *
//     * @param call
//     * @param result
//     */
//    public void setNotificationPlayVibrate(MethodCall call, Result result) {
//        Log.d(TAG, "setNotificationPlayVibrate:");
//        int type = call.arguments();
//        if (type == 1) {
//            mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);
//        } else if (type == 2) {
//            mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//        } else {
//            mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        }
//    }
//
//
//    /**
//     * 通知免打扰模式
//     * 为免过度打扰用户，SDK默认在“23:00”到“7:00”之间收到通知消息时不响铃，不振动，不闪灯。
//     * 如果需要改变默认的静音时间，可以使用以下接口
//     * <p>
//     * 可以通过下面的设置，来关闭免打扰模式：
//     * mPushAgent.setNoDisturbMode(0, 0, 0, 0);
//     *
//     * @param call
//     * @param result
//     */
//    public void setNoDisturbMode(MethodCall call, Result result) {
//        Log.d(TAG, "setNoDisturbMode:");
//        HashMap<String, Object> map = call.arguments();
//        int startHour = (int) map.get("startHour");
//        int startMinute = (int) map.get("startMinute");
//        int endHour = (int) map.get("endHour");
//        int endMinute = (int) map.get("endMinute");
//        mPushAgent.setNoDisturbMode(startHour, startMinute, endHour, endMinute);
//    }
//
//
//    /**
//     * 通知免打扰模式
//     * 默认情况下，同一台设备在1分钟内收到同一个应用的多条通知时，不会重复提醒，同时在通知栏里新的通知会替换掉旧的通知。
//     * 可以通过如下方法来设置冷却时间：
//     *
//     * @param call
//     * @param result
//     */
//    public void setMuteDurationSeconds(MethodCall call, Result result) {
//        Log.d(TAG, "setMuteDurationSeconds:");
//        int seconds = call.arguments();
//        mPushAgent.setMuteDurationSeconds(seconds);
//    }
//
//    /**
//     * 自定义资源包名
//     * Android Studio开发工具是基于gradle的配置方式，资源文件的包和应用程序的包是可以分开的，
//     * 为了正确的找到资源包名，为开发者提供了自定义的设置资源包的接口。当资源包名和应用程序包名不一致时，调用设置资源包名的接口：
//     *
//     * @param call
//     * @param result
//     */
//    public void setResourcePackageName(MethodCall call, Result result) {
//        Log.d(TAG, "setResourcePackageName:");
//        String packageName = call.arguments();
//        mPushAgent.setResourcePackageName(packageName);
//    }
//
//    /**
//     * 应用内消息控制
//     * 应用内消息默认为线上模式，如需使用测试模式，
//     *
//     * @param call
//     * @param result
//     */
//    public void setInAppMsgDebugMode(MethodCall call, Result result) {
//        Log.d(TAG, "setInAppMsgDebugMode :" + call.arguments);
//        boolean debug = (boolean) call.arguments;
//        InAppMessageManager.getInstance(registrar.context()).setInAppMsgDebugMode(debug);
//    }
//
//
//    /**
//     * 全屏消息
//     * 全屏消息是App首次启动打开进入的页面，以全屏图片的形式展示
//     *
//     * @param call
//     * @param result 1、在主工程的values目录下的styles.xml文件中添加如下代码，
//     *               并在drawable目录下放置一张名为umeng\_push\_default\_splash\_bg的默认图片（推荐1920*1080分辨率，也可以根据适配需要引用xml资源）。
//     *               <style name="Theme_Umeng_Push_Splash" parent="android:Theme.NoTitleBar.Fullscreen">
//     *               <item name="android:windowBackground">@drawable/umeng_push_default_splash_bg</item>
//     *               </style>
//     *               <p>
//     *               2、新建一个Activity，继承自UmengSplashMessageActivity，重写onCustomPretreatment方法，并设置全屏消息默认跳转Activity的路径，例如：
//     *               public class SplashTestActivity extends UmengSplashMessageActivity {
//     * @Override public boolean onCustomPretreatment() {
//     * InAppMessageManager mInAppMessageManager = InAppMessageManager.getInstance(this);
//     * //设置应用内消息为Debug模式
//     * mInAppMessageManager.setInAppMsgDebugMode(true);
//     * //参数为Activity的完整包路径，下面仅是示例代码，请按实际需求填写
//     * mInAppMessageManager.setMainActivityPath("com.umeng.message.example.MainActivity");
//     * return super.onCustomPretreatment();
//     * }
//     * }
//     * 说明：onCustomPretreatment方法默认的返回值为false，返回false则会走全屏消息的默认逻辑。
//     * 若开发者在全屏消息的Activity里有动态申请权限的需求，则可以在onCustomPretreatment内进行处理，并return true，则全屏消息逻辑不会继续执行
//     * <p>
//     * 3、在主工程的AndroidManifest.xml中的<application>标签下注册Activity，并将其配置为App首次启动打开的Activity，
//     * theme设置为步骤1所写的Theme_Umeng_Push_Splash，例如：
//     * <activity
//     * android:name="com.umeng.message.example.SplashTestActivity"
//     * android:screenOrientation="portrait"
//     * android:theme="@style/Theme_Umeng_Push_Splash">
//     * <intent-filter>
//     * <action android:name="android.intent.action.MAIN" />
//     * <p>
//     * <category android:name="android.intent.category.LAUNCHER" />
//     * </intent-filter>
//     * </activity>
//     * <p>
//     * 说明：
//     * <p>
//     * 生产模式请求服务器的最小间隔是30分钟，测试模式的最小间隔是1秒。
//     * 全屏消息默认的逻辑为显示2s默认图片，若在2s内请求到全屏消息，则展示全屏消息，否则就跳转到开发者设置的页面。
//     * 全屏消息的图片会自动缓存，并在有新消息到来时，删除旧消息的缓存。
//     */
//    public void setSplashMessage(MethodCall call, Result result) {
//        Log.d(TAG, "setSplashMessage:");
//    }
//
//
//    /**
//     * 插屏消息
//     * 插屏消息是在App页面之上弹出的图片或文本消息。插屏消息分为三种类型：插屏、自定义插屏和纯文本。
//     * <p>
//     * 说明：
//     * <p>
//     * label是插屏消息的标签，用来标识该消息。
//     * 客户端需先调用showCardMessage，把label发送到服务器，之后U-Push后台【展示位置】才会出现可选label。
//     * 以label为单位，生产模式请求服务器的最小间隔是30分钟，测试模式的最小间隔是1秒。
//     * 插屏消息的图片会自动缓存，并在有新消息到来时，删除旧消息的缓存。
//     * 注意：安装到设备上后，每个版本（versionCode）的App最多打10个标签。
//     * <p>
//     * 自定义插屏
//     * 自定义插屏允许开发者来控制插屏的展示样式。若要使用自定义插屏样式，则需在工程中新建一个命名为umeng_custom_card_message.xml的布局文件，
//     * 开发者可以随意修改布局（ImageView和两个Button的id不能改变）。例如：
//     * <?xml version="1.0" encoding="utf-8"?>
//     * <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//     * android:layout_width="match_parent"
//     * android:layout_height="match_parent"
//     * android:orientation="vertical"
//     * android:gravity="center"
//     * android:background="#33000000">
//     * <p>
//     * <FrameLayout
//     * android:layout_width="match_parent"
//     * android:layout_height="wrap_content"
//     * android:background="@color/white"
//     * android:layout_margin="60dp">
//     * <p>
//     * <RelativeLayout
//     * android:layout_width="match_parent"
//     * android:layout_height="wrap_content"
//     * android:layout_margin="20dp">
//     * <p>
//     * <ImageView
//     * android:id="@+id/umeng_card_message_image"
//     * android:layout_width="match_parent"
//     * android:layout_height="wrap_content"
//     * android:scaleType="centerCrop"/>
//     * <p>
//     * <Button
//     * android:id="@+id/umeng_card_message_ok"
//     * android:layout_width="match_parent"
//     * android:layout_height="wrap_content"
//     * android:layout_below="@id/umeng_card_message_image"
//     * android:layout_marginTop="20dp"
//     * android:text="确定"/>
//     * </RelativeLayout>
//     * <p>
//     * <Button
//     * android:id="@+id/umeng_card_message_close"
//     * android:layout_width="wrap_content"
//     * android:layout_height="wrap_content"
//     * android:layout_gravity="top|right"
//     * android:text="关闭"/>
//     *
//     * </FrameLayout>
//     *
//     * </RelativeLayout>
//     * <p>
//     * <p>
//     * 纯文本
//     * <p>
//     * 纯文本插屏字体大小可以由开发者控制，单位为sp，默认为18、16、16，可以使用以下方法设置（在showCardMessage之前调用）
//     * InAppMessageManager.getInstance(Context context).setPlainTextSize(int titleTextSize, int contentTextSize, int buttonTextSize);
//     *
//     * @param call
//     * @param result
//     */
//    public void showCardMessage(MethodCall call, Result result) {
//        Log.d(TAG, "showCardMessage:");
//        InAppMessageManager.getInstance(registrar.context()).showCardMessage(registrar.activity(), "main",
//                new IUmengInAppMsgCloseCallback() {
//                    @Override
//                    public void onClose() {
//                        //插屏消息关闭时，会回调该方法
//                        Log.i(TAG, "card message close");
//
//                    }
//
//                });
//    }
//
//    /**
//     * 检查集成配置文件
//     * 为了便于开发者更好的集成配置文件，我们提供了对于AndroidManifest配置文件的检查工具，可以自行检查开发者的配置问题。SDK默认是不检查集成配置文件的。
//     *
//     * @param call
//     * @param result
//     */
//    public void setPushCheck(MethodCall call, Result result) {
//        Log.d(TAG, "setPushCheck :" + call.arguments);
//        boolean check = (boolean) call.arguments;
//        mPushAgent.setPushCheck(check);
//
//    }
//
//
//    /**
//     * 关闭推送
//     *
//     * @param call
//     * @param result
//     */
//    public void stopPush(MethodCall call, Result result) {
//        Log.d(TAG, "stopPush:");
//        mPushAgent.disable(new IUmengCallback() {
//            @Override
//            public void onSuccess() {
////          callbackMap.put("stopPush","onSuccess");
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//            }
//        });
//
//    }
//
//
//    /**
//     * 若调用关闭推送后，想要再次开启推送
//     *
//     * @param call
//     * @param result
//     */
//    public void openPush(MethodCall call, final Result result) {
//        Log.d(TAG, "openPush:");
//        mPushAgent.enable(new IUmengCallback() {
//            @Override
//            public void onSuccess() {
//                result.success("openPush:success");
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                result.success("openPush:failure" + s + s1);
//            }
//        });
//
//    }
//
//    @Override
//    public void onListen(Object arguments, EventChannel.EventSink events) {
//
//    }
//
//    @Override
//    public void onCancel(Object arguments) {
//
//    }
}
