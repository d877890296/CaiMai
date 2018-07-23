package com.xfkc.caimai.net.upload;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xfkc.caimai.config.Constant;
import com.xfkc.caimai.net.covert.CustomConverterFactory;
import com.xfkc.caimai.net.intercepter.ProgressRequestIntercept;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by LK on 2017/7/18 14:48.
 */

public class FileApi implements ProgressListener{
  final FileService service;
  private ProgressListener listener;
  private static final int WHAT_UPDATE_PROGRESS = 1;
  private Handler mHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what == WHAT_UPDATE_PROGRESS) {
        final FileProgress progress = (FileProgress) msg.obj;
        listener.onProgress(progress.currentSize, progress.totalSize, progress.done);
      }
    }
  };

  public FileApi(ProgressListener listener){
    this.listener = listener;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            //  .addInterceptor(interceptor)
            .addInterceptor(new ProgressRequestIntercept(this))
            .connectTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(CustomConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    service = retrofit.create(FileService.class);

  }

  public FileService getService(){
    return service;
  }

  @Override
  public void onProgress(long currentSize, long totalSize, boolean isFinish) {
    if (listener != null) {
      mHandler.obtainMessage(WHAT_UPDATE_PROGRESS, new FileProgress(currentSize, totalSize, isFinish)).sendToTarget();
    }

  }
}
