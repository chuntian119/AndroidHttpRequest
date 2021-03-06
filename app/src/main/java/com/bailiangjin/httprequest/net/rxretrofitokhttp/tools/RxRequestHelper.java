package com.bailiangjin.httprequest.net.rxretrofitokhttp.tools;

import com.bailiangjin.httprequest.net.rxretrofitokhttp.design.BaseData;
import com.bailiangjin.httprequest.net.rxretrofitokhttp.design.CommonErrors;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络请求的RxHelper类 功能 1、将网络请求的回调绑定到主线程 2、进行统一的异常处理 可根据需要选择
 * Created by bailiangjin on 2017/2/16.
 */

public class RxRequestHelper {

    /**
     * 绑定回调到mainthread 并统一处理异常
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public static <T> Observable requestDealCommonError(Observable<BaseData<T>> observable, Subscriber<BaseData<T>> subscriber) {
        mapCommonErrors(observable);
        setSubscribeToAndroidMainThread(observable, subscriber);
        return observable;
    }



    /**
     * 绑定回调到mainthread 不统一处理异常
     * @param observable
     * @param subscriber
     * @param <T>
     */
    public static <T> void requestNotDealCommonError(Observable<T> observable, Subscriber<T> subscriber) {

        setSubscribeToAndroidMainThread(observable, subscriber);
    }


    /**
     * 将回调切换到MainThread
     * @param observable
     * @param subscriber
     * @param <T>
     */
    private static <T> void setSubscribeToAndroidMainThread(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 异常统一处理
     * @param observable
     * @param <T>
     */
    private static <T> void mapCommonErrors(Observable<BaseData<T>> observable) {
        observable.map(new CommonErrors<T>());
    }

}
