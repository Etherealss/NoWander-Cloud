package com.nowander.oss.controller;

import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.InternalAuth;
import com.nowander.oss.domain.file.FileService;
import com.nowander.oss.domain.file.FileUploadDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wtk
 * @date 2022-09-04
 */
@Slf4j
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@ResponseAdvice
public class FileController {
    private final FileService fileService;

    /**
     * 上传文件
     * @return 文件访问路径
     * @throws Exception
     */
    @InternalAuth
    @PostMapping
    public FileUploadDTO upload(@RequestParam("file") MultipartFile file,
                                @RequestParam(value = "filePath", defaultValue = "") String filePath,
                                @RequestParam(value = "fileName", required = false) String fileName) throws Exception {
        return fileService.uploadFile(file, filePath, fileName);
    }

    /**
     * @param fileKey
     * @return
     */
    @InternalAuth
    @GetMapping
    public String getUrl(@RequestParam("fileKey") String fileKey) {
        log.debug("fileKey: {}", fileKey);
        return fileService.getFileUrl(fileKey);
    }
}
