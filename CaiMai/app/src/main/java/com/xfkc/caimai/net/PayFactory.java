package com.xfkc.caimai.net;


import com.xfkc.caimai.net.upload.FileApi;
import com.xfkc.caimai.net.upload.FileService;
import com.xfkc.caimai.net.upload.ProgressListener;

/**
 * Created by LK on 2017/5/5 10:45.
 */

public class PayFactory {
  private static PayService PayService;
  private static FileService fileService;
  protected static final Object monitor = new Object();

  public static PayService getPayService() {
    synchronized (monitor){
      if(PayService==null){
        PayService = new PayRetrofit().getService();
      }
      return PayService;
    }
  }

  public static FileService getFileService(ProgressListener listener) {
    synchronized (monitor){
      if(fileService==null){
        fileService = new FileApi(listener).getService();
      }
      return fileService;
    }
  }

//  public static  void post(Observable ob, Subscriber subscriber) {
//    ob.subscribeOn(Schedulers.io())
//            .unsubscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(subscriber);
//  }
//
//  public static  void post(Observable ob, Observable.Transformer transformer, Subscriber subscriber) {
//    ob.subscribeOn(Schedulers.io())
//            .unsubscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(subscriber);
//  }
}
