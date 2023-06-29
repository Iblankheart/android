package com.dlc.mp3demo;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.dlc.mp3demo.mP3s.VideoCacheProxy;

import java.util.concurrent.TimeUnit;



public class MyAPP extends Application implements  VideoCacheProxy.AppWrapper{

    private static Context mTotalContext;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static Context getTotalContext() {
        return mTotalContext;
    }

    private HttpProxyCacheServer mProxy;

    private HttpProxyCacheServer newProxy() {

        return new HttpProxyCacheServer.Builder(this)
           
                .cacheDirectory(VideoCacheProxy.getVideoCacheDir(this))
                .build();
    }

    @Override
    public HttpProxyCacheServer getVideoCacheProxy() {
        return mProxy == null ? (mProxy = newProxy()) : mProxy;
    }
}
