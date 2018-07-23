package com.hyf.tdlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by LK on 2016/3/7 15:08
 */
public abstract class BaseActivity extends AppCompatActivity{

      public Context mContext;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initVariables();
            setContentView(getLayoutResource());
            mContext = this;
            ButterKnife.bind(this);
            initViews(savedInstanceState);
            loadData();
      }

	  /**
	   * 初始化变量，包括Intent带的数据和activity内的变量
       */
      protected void initVariables(){

      }

      /**
       * 加载layout布局文件
       */
      protected abstract int getLayoutResource();

      /**
       * 初始化控件，设置控件事件
       * @param savedInstanceState
       */
      protected abstract void initViews(Bundle savedInstanceState);

      /**
       * 调用api获取数据
       */
      protected abstract  void loadData();


      protected  String getName(){
            return BaseActivity.class.getName();
      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
            ButterKnife.unbind(this);
            //TDRequestManager.getSingleton().cancelRequest(getName());
      }

}
