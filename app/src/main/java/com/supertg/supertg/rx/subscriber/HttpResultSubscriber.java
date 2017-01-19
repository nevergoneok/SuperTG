package com.supertg.supertg.rx.subscriber;

import com.supertg.supertg.rx.HttpResult;

import rx.Subscriber;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:21:27
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            e.printStackTrace();
            if (e.getMessage() == null) {
                _onError(new Throwable(e.toString()));
            } else {
                _onError(new Throwable(e.getMessage()));
            }
        } else {
            _onError(new Exception("null message"));
        }
    }

    @Override
    public void onNext(HttpResult<T> t) {
        if ("0".equals(t.ret))
            onSuccess(t.errDesc);
        else
            _onError(new Throwable("error=true"));
    }

    public abstract void onSuccess(T t);

    public abstract void _onError(Throwable e);
}