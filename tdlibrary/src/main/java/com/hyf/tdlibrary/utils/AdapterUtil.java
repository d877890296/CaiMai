package com.hyf.tdlibrary.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by LK on 2016/4/6 14:57
 */
public class AdapterUtil {
	/**
	 * 创建BaseAdapter
	 * 面向: AbsListView
	 * 1. 抽象出重复代码,默认实现一些常规代码
	 * 2. 封装了ViewHolder
	 * 3. 自动传递Model给getView
	 * @param context
	 * @param list model的列表
	 * @param layoutId 布局xml的id
	 * @param callBack 包含getView方法的回调
	 * @param <T>
	 * @return
	 */
	public static <T> BaseAdapter base(final Context context,
										final List<T> list,
										final int layoutId,
										final CallBack callBack) {

		BaseAdapter result = new BaseAdapter() {

			@Override
			public int getCount() {
				if (list != null) {
					return list.size();
				}
				return 0;
			}

			@Override
			public T getItem(int position) {
				return list.get(position);
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder;
				if (null == convertView) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(context).inflate(layoutId, null);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				T t = null;
				if (position < list.size()) {
					t = getItem(position);
				}
				return callBack.getView(position, convertView, holder, t);
			}

		};
		return result;
	}

	/**
	 * 简化版本的$base的CallBack
	 * @param <T>
	 */
	public interface CallBack<T> {
		View getView(int position, View convertView, ViewHolder holder, T t);
	}

	/**
	 * ViewHolder类相当于一个享元模式的工厂类
	 * 主要用了以下优化点:
	 * 1. 缓存了findViewById的view,如果已经创建,则直接返回,提高了性能
	 * 2. 用SparseArray代替HashMap优化性能
	 */
	public static class ViewHolder {
		public SparseArray<View> views = new SparseArray<>();

		/**
		 * 从缓存里获取viewId对应的View
		 * @param convertView
		 * @param viewId
		 * @param <T>
		 * @return
		 */
		public <T extends View> T view(View convertView, int viewId) {
			View v = views.get(viewId);
			if (null == v) {
				v = convertView.findViewById(viewId);
				views.put(viewId, v);
			}
			return (T) v;
		}
	}
}
