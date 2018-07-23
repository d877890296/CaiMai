package com.hyf.tdlibrary.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.hyf.tdlibrary.R;
import com.hyf.tdlibrary.service.UpdateService;
import com.hyf.tdlibrary.utils.AppUtil;

/**
 * Created by LK on 2016/4/6 16:08
 */
public class UpdateManager {
	public static boolean check(final Context context, VersionBean versionBean) {
		int vercode = 0;
		String vername = "";
		String log = "";
		String download;
		try {
			vercode = Integer.parseInt(versionBean.getVercode());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		vername = versionBean.getVername();
		download = versionBean.getUpgradeUrl();
		log = versionBean.getUpgradeContent();

		return check(context, vercode, vername, download, log);
	}

	/**
	 * 根据解析的结果来比较是否有更新
	 * @param context
	 * @param vercode
	 * @param vername
	 * @param download
	 * @param log
	 * @return
	 */
	public static boolean check(final Context context,
								 int vercode,
								 String vername,
								 final String download,
								 String log) {
		// 无更新
		if (!hasUpdate(vercode)) {
			return false;
		}

		// 有更新,则弹出对话框告知用户
		new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.td_app_download_dialog_title) + vername)
				.setMessage(log)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						download(context, download);
					}
				}).show();

		return true;
	}

	/**
	 * 根据版本判断是否有更新
	 * @param vercode
	 * @return
	 */
	public static boolean hasUpdate(int vercode) {
		if (vercode <= AppUtil.vercode()) {
			return false;
		}
		return true;
	}

	/**
	 * 启动下载服务,开始下载APK文件
	 * @param context
	 * @param download
	 */
	public static void download(Context context, String download) {
		Intent intent = new Intent(context, UpdateService.class);
		intent.putExtra("download_url", download);
		context.startService(intent);
	}
}
