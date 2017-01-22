package com.supertg.supertg.eventbus;

/**
 * 作者：陈冬冬
 * <p>
 * 说明：使用EventBus传递一个字符串和2个标记字符，需要传递其他类型的数据得重新写一个类
 * <p>
 * EventBus使用参考网站：http://blog.csdn.net/harvic880925/article/details/40660137
 * <p>
 * 例如：在SmartBraHttp请求服务器发送消息，EventBus.getDefault().post(new
 * CommonEvent(result,1));
 * <p>
 * 在LoginActivity中接收消息，在onCreate中注册EventBus,在onDestory中反注册 在public void
 * onEventMainThread(CommonEvent event) 方法中接收处理消息
 * <p>
 * 时间：2015-12-28 上午11:13:54
 */
public class CommonEvent {


    private Boolean b;
    public Object object;
    public String msg; // 接收的消息内容
    // 标记请求服务的状态
    public int what; // 1表示请求成功，0表示请求失败，-1表示连接服务器失败
    public static final int REQUESTSUEECCD = 1;
    public static final int REQUESTFAILUE = 0;
    public static final int LINKFAILUE = -1;

    //标记是哪里的发送的消息,每次使用时记得在下面写上注释，避免重复使用同一个标记
    public int type;
    public static final int asyncRecycleMain = 0; //首页recycleview更新

    public CommonEvent(String msg, int what, int type) {
        this.msg = msg;
        this.what = what;
        this.type = type;
    }

    public CommonEvent(boolean b, int what, int type) {
        this.b = b;
        this.what = what;
        this.type = type;
    }

    public CommonEvent(Object object, int what, int type) {
        this.object = object;
        this.what = what;
        this.type = type;
    }
}
