package com.hyf.tdlibrary.manager;

import com.hyf.tdlibrary.utils.ToastUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程管理类
 *
 * Created by LK on 2016/4/21 9:36
 */
public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager(){
		int num = Runtime.getRuntime().availableProcessors() * 20;
		service = Executors.newFixedThreadPool(num);
	}

	private static final ThreadPoolManager manager = new ThreadPoolManager();

	public static ThreadPoolManager getInstance(){
		return manager;
	}

	public void executeTask(Runnable runnable){
		service.execute(runnable);
	}

	public static void main(String[] args) {
		ThreadPoolManager.getInstance().executeTask(new Runnable() {
			@Override
			public void run() {
				ToastUtil.showToast("hello world!");
			}
		});
	}

}
