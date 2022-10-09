package com.nowander.account.controller;

import com.nowander.account.domain.user.avatar.AvatarService;
import com.nowander.account.infrasturcture.feign.file.FileUploadDTO;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.SecurityContextHolder;
import com.nowander.common.security.annotation.AnonymousAccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@RestController
@RequestMapping("/users/avatars")
@RequiredArgsConstructor
@ResponseAdvice
@RefreshScope
public class UserAvatarController {
    private final AvatarService avatarService;

    @AnonymousAccess
    @GetMapping
    public String getAvatarUrl(@RequestParam String fileKey) {
        log.debug("fileKey: {}", fileKey);
        return avatarService.getAvatarUrl(fileKey);
    }

    @PostMapping
    public FileUploadDTO upload(@RequestParam("avatarFile") MultipartFile avatarFile) {
        Integer userId = SecurityContextHolder.require().getUserId();
        return avatarService.uploadAvatar(avatarFile, userId);
    }
}
