package com.nowander.oss.domain.file;

import com.nowander.common.core.exception.rest.MissingParamException;
import com.nowander.common.core.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * @author wtk
 * @date 2022-09-04
 */
@Service
@Slf4j
@Validated
public class FileService {

    private final OssFileService ossFileService;
    private final String filePathSeparator;

    public FileService(OssFileService ossFileService,
                       @Value("${app.file.path-separator}") @NotEmpty String filePathSeparator) {
        this.ossFileService = ossFileService;
        this.filePathSeparator = filePathSeparator;
    }

    /**
     * 上传文件
     * @param avatarFile
     * @return
     * @throws IOException
     */
    public FileUploadDTO uploadFile(MultipartFile avatarFile, String filePath, String fileName) throws IOException {
        if (!StringUtils.hasText(fileName)) {
            fileName = avatarFile.getOriginalFilename();
        }
        if (!StringUtils.hasText(fileName)) {
            throw new MissingParamException("文件名不能为空");
        }
        String fileExt = FileUtil.getFileExt(fileName);
        String fileContentType = FileUtil.getContentType(fileExt);
        String fileKey = filePath + filePathSeparator + fileName;
        String fileUrl = ossFileService.upload(avatarFile.getInputStream(), fileKey, fileContentType);

        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setFileKey(fileKey);
        fileUploadDTO.setFilePath(filePath);
        fileUploadDTO.setFileName(fileName);
        fileUploadDTO.setFileContentType(fileContentType);
        fileUploadDTO.setFileExt(fileExt);
        fileUploadDTO.setUrl(fileUrl);
        log.info("文件上传成功：{}", fileUploadDTO);
        return fileUploadDTO;
    }

    public String getFileUrl(String fileKey) {
        return ossFileService.getUrl(fileKey);
    }

}
