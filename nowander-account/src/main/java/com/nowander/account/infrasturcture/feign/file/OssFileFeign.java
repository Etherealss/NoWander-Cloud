package com.nowander.account.infrasturcture.feign.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wtk
 * @date 2022-09-06
 */
@FeignClient(value = "nowander-oss", path = "/files")
public interface OssFileFeign {
    /**
     * 上传文件
     * @return 文件访问路径
     * @throws Exception
     */
    @PostMapping
    FileUploadDTO upload(@RequestParam("file") MultipartFile file,
                         @RequestParam(value = "filePath", defaultValue = "") String filePath,
                         @RequestParam(value = "fileName", required = false) String fileName);

    @GetMapping
    String getUrl(@RequestParam("fileKey") String fileKey);
}
