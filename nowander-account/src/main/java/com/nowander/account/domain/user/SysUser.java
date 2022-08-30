package com.nowander.account.domain.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.core.pojo.Converter;
import com.nowander.common.core.pojo.entity.IdentifiedEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 类名叫做SysUser是为了避免与其他类库的User类重复
 * @author wtk
 * @since 2022-01-05
 */
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class SysUser extends IdentifiedEntity implements Converter<UserBriefDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱注册
     */
    private String email;

    /**
     * 密码
     */
    private String password;

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
