package com.nowander.account.domain.user.avatar;

import com.nowander.account.infrasturcture.feign.file.FileUploadDTO;
import com.nowander.account.infrasturcture.feign.file.OssFileFeign;
import com.nowander.common.core.exception.internal.ConfigurationException;
import com.nowander.common.core.exception.rest.MissingParamException;
import com.nowander.common.core.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-04-30
 */
@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class AvatarService {
    @Value("${app.file.oss.avatar-dir}")
    @NotEmpty
    private String avatarDir;
    @Value("${app.file.path-separator}")
    @NotEmpty
    private String filePathSeparator;
    private String defaultBoyAvatar;
    private String defaultGirlAvatar;
    private final OssFileFeign ossFileFeign;
    /**
     * 用于在OSS上命名，格式 ：年月日/文件名.后缀名
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void init() {
        if (avatarDir.startsWith("/") || avatarDir.endsWith("/")) {
            throw new ConfigurationException("avatarDir 不能以“/”开头或结尾");
        }
        defaultBoyAvatar = avatarDir + filePathSeparator + "default-boy.png";
        defaultGirlAvatar = avatarDir + filePathSeparator + "default-girl.png";
    }

    /**
     * 通过用户的头像在OSS上的访问路径，获取用户头像在外网的url地址
     * @param fileKey
     * @return 可在外网访问的url地址
     */
    public String getAvatarUrl(String fileKey) {
        return ossFileFeign.getUrl(fileKey);
    }

    public String getDefaultAvatarUrl(boolean isMan) {
        return ossFileFeign.getUrl(isMan ? defaultBoyAvatar : defaultGirlAvatar);
    }

    /**
     * 上传头像
     * @param avatarFile
     * @param userId
     * @return 头像保存路径，同时也是访问文件的url
     */
    public FileUploadDTO uploadAvatar(MultipartFile avatarFile, Integer userId) {
        String originalFilename = avatarFile.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new MissingParamException("无法获取原始文件的文件扩展名");
        }
        String fileExt = FileUtil.getFileExt(originalFilename);
        String fileName = userId + fileExt;
        return ossFileFeign.upload(avatarFile, getSavePath(), fileName);
    }

    private String getSavePath() {
        return avatarDir + filePathSeparator + DATE_FORMAT.format(new Date());
    }
}
