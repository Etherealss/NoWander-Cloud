package com.nowander.discussion.infrastructure.feign.user.info;

import lombok.Data;

import java.util.Date;

/**
 * 展示用户简略信息的DTO
 * @author wtk
 * @since 2022-01-05
 */
@Data
public class UserBriefDTO {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱注册
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 用户头像路径
     */
    private String avatar;

    /**
     * 高中/大学/教师/其他
     */
    private UserType userType;

    /**
     * 获赞数
     */
    private Integer likedCount;

    /**
     * 收藏数
     */
    private Integer collectedCount;
}
