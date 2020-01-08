package com.cpetsol.cpetsolutions.myaaptha.util.Downloader.listener;

import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.pojo.FileResponse;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.request.FileLoadRequest;

/**
 * Created by krishna on 12/10/17.
 */

public interface FileRequestListener<T> {
    void onLoad(FileLoadRequest request, FileResponse<T> response);

    void onError(FileLoadRequest request, Throwable t);
}
