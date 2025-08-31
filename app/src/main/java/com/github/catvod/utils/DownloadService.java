package com.github.catvod.utils;


import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.net.OkHttp;
import okhttp3.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadService {
    private static final int THREAD_NUM = 16;
    //最小分片，按这个下载
    private static final int PER_PIECE = 1024 * 1024;
    //按照这个分割视频，每一个部分多线程下载，下载完下载下一个部分
    private static final int PER_PART = 1024 * 1024 * THREAD_NUM;
    private List<Future<Response>> results = new ArrayList<Future<Response>>();
    private RandomAccessFile file = null;
    private List<long[]> parts;
    private AtomicInteger index = new AtomicInteger(0);

    private static class Loader {
        static volatile DownloadService INSTANCE = new DownloadService();
    }

    public static DownloadService get() {
        return DownloadService.Loader.INSTANCE;
    }

    /**
     * 下载链接
     */
    private String url;
    /**
     * 下载信息
     */
    private DownloadInfo downloadInfo;


    public void submitDownload(String url, Map<String, String> headers, long length) {
        if (url.equals(this.url)) {
            SpiderDebug.log("url相同，任务已存在");
        } else {
            this.url = url;
            parts = generatePart(length);
            downloadInfo = new DownloadInfo();
            downloadInfo.setParts(parts);
            downloadInfo.setUrl(url);
           new Thread(new Runnable() {
               @Override
               public void run() {
                   ExecutorService service = Executors.newFixedThreadPool(THREAD_NUM);

                   for (long[] part : parts) {
                       String newRange = "bytes=" + part[0] + "-" + part[1];
                       SpiderDebug.log("下载开始" + ";newRange:" + newRange);

                       Map<String, String> headerNew = new HashMap<>(headers);

                       headerNew.put("range", newRange);
                       headerNew.put("Range", newRange);
                       Future<Response> result = service.submit(() -> {
                           try {
                               part[2] = 1;
                               return OkHttp.newCall(url, headerNew);
                           } catch (Exception e) {
                               throw new RuntimeException(e);
                           }
                       });
                       results.add(result);
                   }
                   try {
                       file = new RandomAccessFile(Path.tv() + File.separator + Util.MD5(url) + ".mp4", "rw");
                   } catch (FileNotFoundException e) {
                       SpiderDebug.log("创建文件失败");
                       throw new RuntimeException(e);
                   }


                   while (index.get() < results.size()) {
                       Response response = null;
                       try {
                           response = results.get(index.get()).get();
                           file.seek(parts.get(index.get())[0]);
                           file.write(response.body().bytes());
                           index.addAndGet(1);
                       } catch (ExecutionException e) {
                           throw new RuntimeException(e);
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }

                   }

                   service.shutdown();
               }
           }).start();

        }
    }

    /**
     * 返回信息
     *
     * @param start 开始
     * @return
     */
    public Object[] getDownloadBytes(long start) throws ExecutionException, InterruptedException {
        long partIndex = start / PER_PART;

        while (parts.get((int) partIndex)[2] == 0) {
            index.set((int) partIndex);
        }
        try {
            file.seek(start);
            byte[] bytes = new byte[Math.toIntExact(parts.get((int) partIndex)[1] - start + 1)];
            file.read(bytes);
            return new Object[]{bytes,start,parts.get((int) partIndex)[1] };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 分割为需要下载的部分
     *
     * @param total 文件总大小
     * @return
     */
    private static List<long[]> generatePart(long total) {
        SpiderDebug.log("generatePart.total:" + total);

        long start = 0;
        long end = total - 1;

        //分割为多少个部分
        long size = total / PER_PART;
        SpiderDebug.log("generatePart.size:" + size);
        List<long[]> partList = new ArrayList<>();
        for (int i = 0; i < size * THREAD_NUM; i++) {
            long partEnd = Math.min(start + PER_PIECE, end);

            partList.add(new long[]{start, partEnd, 0});
            start = partEnd + 1;
        }

        //最后多出分为一块下载
        if (start <= end) {
            partList.add(new long[]{start, end, 0});
        }
        return partList;
    }

}
