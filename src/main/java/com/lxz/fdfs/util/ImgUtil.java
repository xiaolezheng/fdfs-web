package com.lxz.fdfs.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;

import com.lxz.fdfs.support.FsConstants;

/**
 * Created by xiaolezheng on 16/5/22.
 */
public class ImgUtil {

    /**
     * 获取缩略图输入流
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static InputStream getThumbImageStream(InputStream inputStream) throws IOException {
        // 在内存当中生成缩略图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(inputStream).size(FsConstants.Default_ThumbImage_width, FsConstants.Default_ThumbImage_height)
                .toOutputStream(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

}
