package com.lxz.fdfs.support;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.IOUtils;

import com.github.tobato.fastdfs.proto.storage.DownloadCallback;

/**
 * 
 * Created by xiaolezheng on 16/5/22.
 */
@Slf4j
public class FsDownloadThumbImgFileWriter implements DownloadCallback<String> {
    private String fileName;
    private OutputStream outputStream; // 需要外部进行关闭
    private int width = FsConstants.Default_ThumbImage_width;
    private int height = FsConstants.Default_ThumbImage_height;

    public FsDownloadThumbImgFileWriter() {
    }

    public String getFileName() {
        return fileName;
    }

    public FsDownloadThumbImgFileWriter setFileName(String fileName) {
        this.fileName = fileName;

        return this;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public FsDownloadThumbImgFileWriter setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public FsDownloadThumbImgFileWriter setWidth(int width) {
        this.width = width;

        return this;
    }

    public int getHeight() {
        return height;
    }

    public FsDownloadThumbImgFileWriter setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public String recv(InputStream ins) throws IOException {
        InputStream in = null;
        try {
            in = new BufferedInputStream(ins);
            Thumbnails.of(in).size(this.width, this.width).toOutputStream(outputStream);
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
