package com.lxz.fdfs.api;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.lxz.fdfs.support.FsConstants;
import com.lxz.fdfs.support.FsDownloadFileWriter;
import com.lxz.fdfs.support.FsDownloadThumbImgFileWriter;
import com.lxz.fdfs.support.ResponseBuilder;

/**
 *
 * 文件上传下载接口, 上传文件客户端需要指定http请求头 Content-Type: multipart/form-data 请求fastdfs接口进行存储下载
 *
 * Created by xiaolezheng on 16/5/19.
 */
@RestController
@RequestMapping("/file")
@Import(FdfsClientConfig.class)
@Slf4j
public class FileController {

    @Resource
    FastFileStorageClient client;

    @RequestMapping("/hello")
    public Object hello() {
        return ResponseBuilder.createSuccResponse();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @ResponseBody
    public Object provideUploadInfo() {
        return ResponseBuilder.createFailResponse().setMessage("You can upload a file by post to this same URL");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();

            try {
                String fileExtName = getFileExtension(file.getOriginalFilename());
                ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());

                StorePath path = client.uploadFile(FsConstants.DEFAULT_GROUP, inputStream, file.getSize(), fileExtName);

                log.info("result: {}", path);

                Map<String, String> result = ImmutableMap.of("f", path.getPath(), "g", path.getGroup());

                return ResponseBuilder.createSuccResponse().setData(result);
            } catch (Exception e) {
                log.error("文件上传失败", e);
                return ResponseBuilder.createFailResponse()
                        .setMessage("You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            return ResponseBuilder.createFailResponse().setMessage("You upload the file is empty");
        }
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(HttpServletRequest request) throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<Map> result = Lists.newArrayList();
        for (MultipartFile file : files) {
            String fileName = file.getName();
            if (!file.isEmpty()) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
                String fileExtName = getFileExtension(file.getOriginalFilename());

                StorePath path = client.uploadFile(FsConstants.DEFAULT_GROUP, inputStream, file.getSize(), fileExtName);

                log.info("result: {}", path);

                Map<String, String> item = ImmutableMap.of("f", path.getPath(), "g", path.getGroup());

                result.add(item);

            } else {
                log.warn("You failed to upload " + fileName + " because the file was empty.");
            }
        }

        return ResponseBuilder.createSuccResponse().setData(result);
    }

    @RequestMapping(value = "/upload/batch2", method = RequestMethod.POST)
    public Object fileUpload2(HttpServletRequest request) throws Exception {
        // 设置上下方文
        CommonsMultipartResolver multiPartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<Map> result = Lists.newArrayList();

        // 检查form是否有enctype="multipart/form-data"
        if (multiPartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();

            while (iterator.hasNext()) {
                MultipartFile file = multiRequest.getFile(iterator.next());
                if (!file.isEmpty()) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
                    String fileExtName = getFileExtension(file.getOriginalFilename());

                    StorePath path = client.uploadFile(FsConstants.DEFAULT_GROUP, inputStream, file.getSize(),
                            fileExtName);

                    log.info("result: {}", path);

                    Map<String, String> item = ImmutableMap.of("f", path.getPath(), "g", path.getGroup());
                    result.add(item);
                }

            }
        }

        return ResponseBuilder.createSuccResponse().setData(result);
    }

    /**
     * 文件下载接口
     *
     * @param fileId 文件路径id
     * @param group fastdfs 存储分组
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object fileDownload(@RequestParam(value = "f") String fileId, @RequestParam(value = "g") String group,
            HttpServletResponse response) throws Exception {
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileId);

        OutputStream outputStream = response.getOutputStream();
        FsDownloadFileWriter writerCallback = new FsDownloadFileWriter(fileId, outputStream);
        client.downloadFile(group, fileId, writerCallback);

        IOUtils.closeQuietly(outputStream);

        return "ok";
    }

    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    @ResponseBody
    public Object handleImgUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();

            try {
                String fileExtName = getFileExtension(file.getOriginalFilename());
                ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());

                StorePath path = client.uploadFile(FsConstants.DEFAULT_GROUP, inputStream, file.getSize(), fileExtName);

                log.info("result: {}", path);

                Map<String, String> result = ImmutableMap.of("f", path.getPath(), "g", path.getGroup());

                return ResponseBuilder.createSuccResponse().setData(result);
            } catch (Exception e) {
                log.error("文件上传失败", e);
                return ResponseBuilder.createFailResponse()
                        .setMessage("You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            return ResponseBuilder.createFailResponse().setMessage("You upload the file is empty");
        }
    }

    /**
     * 图片下载接口
     *
     * @param fileId 文件路径id
     * @param q 图片质量(空返回原图; 0[120,120],1[160,160],2[200,200],3[300,300] 返回对应的缩略图)
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/img", method = RequestMethod.GET)
    public Object imgDownload(@RequestParam(value = "f") String fileId, @RequestParam(value = "g") String group,
            @RequestParam(value = "q", required = false) String q, HttpServletResponse response) throws Exception {
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileId);

        OutputStream outputStream = response.getOutputStream();

        if (StringUtils.isNotBlank(q) && Ints.contains(new int[] { 0, 1, 2, 3 }, Ints.tryParse(q.trim()))) {
            Pair<Integer, Integer> sizePair = FsConstants.ThumbImage_Format.get(Ints.tryParse(q.trim()));

            FsDownloadThumbImgFileWriter writerCallback = new FsDownloadThumbImgFileWriter().setFileName(fileId)
                    .setWidth(sizePair.getLeft()).setHeight(sizePair.getRight()).setOutputStream(outputStream);
            client.downloadFile(group, fileId, writerCallback);
        } else {
            FsDownloadFileWriter writerCallback = new FsDownloadFileWriter(fileId, outputStream);
            client.downloadFile(group, fileId, writerCallback);
        }

        IOUtils.closeQuietly(outputStream);

        return "ok";
    }

    private String getFileExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }
}
