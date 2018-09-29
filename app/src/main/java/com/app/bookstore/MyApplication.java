package com.app.bookstore;

import android.app.Application;

import org.xutils.x;

/**
 * Created by ming on 2018/09/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
