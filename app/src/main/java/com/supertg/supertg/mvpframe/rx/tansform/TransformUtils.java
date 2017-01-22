package com.supertg.supertg.mvpframe.rx.tansform;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * 暂未使用
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:17
 */
@Deprecated
public class TransformUtils {

    /**
     *  .subscribeOn(Schedulers.io())//请求在io线程执行
     *  .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> defaultSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }


    /**
     * 都在io线程执行
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> all_io() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        };
    }
}
