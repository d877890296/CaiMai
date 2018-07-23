package com.xfkc.caimai.net.upload;

/**
 * Created by BlingBling on 2017/3/10.
 */

public class FileProgress {
    public long currentSize;
    public long totalSize;
    public boolean done;

    public FileProgress(long currentSize, long totalSize, boolean done) {
        this.currentSize = currentSize;
        this.totalSize = totalSize;
        this.done = done;
    }
}
