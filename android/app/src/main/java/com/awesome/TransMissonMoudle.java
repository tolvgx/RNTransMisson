package com.awesome;

/**
 * Created by Tolvgx on 18/07/31.
 */

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;

public class TransMissonMoudle extends ReactContextBaseJavaModule {

    private static final String REACT_CLASS = "TransMissonMoudle";
    private ReactContext mReactContext;

    public TransMissonMoudle(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }


    @ReactMethod
    public void contactWithRTC(String type) {
        WritableMap writableMap = new WritableNativeMap();
        switch (type){
            case "getTime":
                writableMap.putString("type",type);
                writableMap.putString("age","10");
                writableMap.putString("time", getTimeMillis());
                break;
            default:
                break;
        }

        sendTransMisson(mReactContext, "rnTransMisson", writableMap);
    }

    /**
     * 1.RCTDeviceEventEmitter方式
     * @param reactContext
     * @param eventName    事件名
     * @param params       传参
     */
    public void sendTransMisson(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    /**
     * 2.CallBack方式
     * @param type
     * @param callback
     */
    @ReactMethod
    public void contactWithCallBack(String type, Callback callback) {
        switch (type){
            case "getTime":
                callback.invoke(getTimeMillis());
                break;
            default:
                break;
        }
    }

    /**
     * 3.Promise方式
     * @param type
     * @param promise
     */
    @ReactMethod
    public void contactWithPromise(String type, Promise promise) {
        switch (type) {
            case "getTime":

                WritableMap writableMap=new WritableNativeMap();
                writableMap.putString("age","30");
                writableMap.putString("time",getTimeMillis());
                promise.resolve(writableMap);

                break;
            default:
                break;
        }
    }


    /**
     * 获取当前时间
     * @return time
     */
    private String getTimeMillis() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = formatDate.format(date);
        return time;
    }
}
