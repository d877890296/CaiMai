package com.xfkc.caimai.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hyf.tdlibrary.utils.StatusBarUtil;
import com.xfkc.caimai.R;
import com.xfkc.caimai.application.MyApplication;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by LK on 2016/3/7 15:08
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Map<String, Object> extraMap;
    public String userid, usercode, username;
    public int start = 0, pageSize = 20;
    public String token ;//用户登录
    public Context mContext;
    public MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        setContentView(getLayoutResource());
        mContext = this;
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        loadData();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 50);
    }

    /**
     * 初始化变量，包括Intent带的数据和activity内的变量
     */
    protected void initVariables() {
        app = MyApplication.getInstance();

        app.queueList.add(this);
        //跳转传值
        extraMap = new HashMap<String, Object>();
    }


    /**
     * 加载layout布局文件
     */
    protected abstract int getLayoutResource();

    /**
     * 初始化控件，设置控件事件
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 调用api获取数据
     */
    protected abstract void loadData();


    protected String getName() {
        return BaseActivity.class.getName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.queueList.remove(this);
        extraMap.clear();
        extraMap = null;
        ButterKnife.unbind(this);
        //TDRequestManager.getSingleton().cancelRequest(getName());
    }

    // 通过关闭所有的activity，退出App
    public void exitAPP() {
        for (int i = 0; i < app.queueList.size(); i++) {
            Activity activity = app.queueList.get(i);
            if (activity != null) {
                activity.finish();

                System.gc();
            }
        }
    }

    /***
     *
     * 类的跳转
     *
     * @param who
     */
    public void skip_classView(Class<?> who, Map<String, Object> extraMap, boolean isFinish) {
        this.extraMap = extraMap;
        Intent intent = new Intent();
        intent.setClass(this, who);
        putExtra(intent, extraMap);
        startActivity(intent);
        if (isFinish == true) {
            finish();
        }
        overridePendingTransition(R.anim.alpha_have_no, R.anim.alpha_no_have);
        // overridePendingTransition(R.anim.translate_top,
        // R.anim.translate_top_exit);
    }

    /***
     *
     * 类的跳转
     *
     * @param who
     */
    public void skip_classView(Class<?> who, Map<String, Object> extraMap, boolean isFinish, int requestCode) {
        this.extraMap = extraMap;
        Intent intent = new Intent();
        intent.setClass(this, who);
        putExtra(intent, this.extraMap);
        startActivityForResult(intent, requestCode);
        if (isFinish == true) {
            finish();
        }
        overridePendingTransition(R.anim.alpha_have_no, R.anim.alpha_no_have);
        // overridePendingTransition(R.anim.translate_top,
        // R.anim.translate_top_exit);
    }

    /**
     * 返回 回调跳转
     *
     * @param requestCode
     * @param isFinish
     * @param isMove
     * @param extraMap
     */
    public void backHistory(int requestCode, boolean isFinish, boolean isMove, Map<String, Object> extraMap) {
        this.extraMap = extraMap;
        Intent intent = new Intent();
        putExtra(intent, this.extraMap);
        setResult(requestCode, intent);
        if (isFinish == true) {
            finish();
        }
        if (isMove == true) {
            overridePendingTransition(R.anim.alpha_have_no, R.anim.alpha_no_have);
        }
    }

    /***
     * 压值
     *
     * @param intent
     * @param extraMap
     */
    public void putExtra(Intent intent, Map<String, Object> extraMap) {
        for (Map.Entry<String, Object> entry : extraMap.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            intent.putExtra(key, value);

        }

    }


}
