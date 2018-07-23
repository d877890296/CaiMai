package com.xfkc.caimai.net.upload;

/**
 * Created by LK on 2017/7/18 14:45.
 */

public interface ProgressListener {
  void onProgress(long currentSize, long totalSize, boolean isFinish);
}
