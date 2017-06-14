package cn.share.jack.cyghttp.app;

import android.app.Application;

/**
 * Created by jack on 2017/6/13
 * 全局唯一的Application实例，只要继承MyApplication就可以了，MyApplication会把this存到单实例引用
 */

public class CygApplication extends Application {

    private static CygApplication sInstance;

    public static CygApplication getInstance() {
        return sInstance;
    }

    protected CygApplication() {
        sInstance = this;
    }
}