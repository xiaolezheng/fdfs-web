package com.lxz.fdfs.support;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

import com.github.tobato.fastdfs.proto.storage.DownloadCallback;

/**
 *  自定义文件下载回调接口
 *
 * Created by xiaolezheng on 16/5/21.
 */
@Slf4j
public class FsDownloadFileWriter implements DownloadCallback<String> {
    private String fileName;
    private OutputStream outputStream;   // 需要外部进行关闭

    public FsDownloadFileWriter(String fileName) {
        this.fileName = fileName;
    }

    public FsDownloadFileWriter(String fileName, OutputStream outputStream) {
        this.fileName = fileName;
        this.outputStream = outputStream;
    }

    @Override
    public String recv(InputStream ins) throws IOException {
        InputStream in = null;
        try {
            in = new BufferedInputStream(ins);
            IOUtils.copy(in, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            // 关闭流
            IOUtils.closeQuietly(in);
        }

        return this.fileName;
    }
}
