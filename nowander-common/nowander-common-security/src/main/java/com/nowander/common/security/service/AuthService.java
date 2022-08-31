package com.nowander.common.security.service;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.utils.ServletUtil;
import com.nowander.common.security.SecurityContextHolder;
import com.nowander.common.security.UserCredential;
import com.nowander.common.security.annotation.RequiresPermissions;
import com.nowander.common.security.annotation.RequiresRoles;
import com.nowander.common.security.config.AuthConstants;
import com.nowander.common.security.config.TokenConfig;
import com.nowander.common.security.enums.Logical;
import com.nowander.common.security.exception.NotPermissionException;
import com.nowander.common.security.exception.NotRoleException;
import com.nowander.common.security.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Component
@Slf4j
public class AuthService {

    public UserCredential requireToken() {
        UserCredential userCredential = SecurityContextHolder.get();
        if (userCredential == null) {
            String token = ServletUtil.getRequest().getHeader(TokenConfig.HEADER_TOKEN);
            if (!StringUtils.hasText(token)) {
                throw new TokenException(ApiInfo.TOKEN_MISSING);
            } else {
                throw new TokenException(ApiInfo.TOKEN_INVALID);
            }
        }
        return userCredential;
    }

    /**
     * 根据注解传入参数鉴权, 如果验证未通过，则抛出异常: NotPermissionException
     * @param requiresPermissions 权限注解
     */
    public void checkPermi(RequiresPermissions requiresPermissions) {
        if (requiresPermissions.logical() == Logical.AND) {
            checkPermiAnd(requiresPermissions.value());
        } else {
            checkPermiOr(requiresPermissions.value());
        }
    }

    /**
     * 根据注解(@RequiresRoles)鉴权
     * @param requiresRoles 注解对象
     */
    public void checkRole(RequiresRoles requiresRoles) {
        if (requiresRoles.logical() == Logical.AND) {
            checkRoleAnd(requiresRoles.value());
        } else {
            checkRoleOr(requiresRoles.value());
        }
    }

    /**
     * 验证用户是否含有指定角色，必须全部拥有
     * @param roles 角色标识数组
     */
    public void checkRoleAnd(String... roles) {
        Set<String> roleList = getRoleList();
        for (String role : roles) {
            if (!hasRole(roleList, role)) {
                throw new NotRoleException(role);
            }
        }
    }

    /**
     * 验证用户是否含有指定角色，只需包含其中一个
     * @param roles 角色标识数组
     */
    public void checkRoleOr(String... roles) {
        Set<String> roleList = getRoleList();
        for (String role : roles) {
            if (hasRole(roleList, role)) {
                return;
            }
        }
        if (roles.length > 0) {
            throw new NotRoleException(roles);
        }
    }


    /**
     * 验证用户是否含有指定权限，必须全部拥有
     * @param permissions 权限列表
     */
    public void checkPermiAnd(String... permissions) {
        Set<String> permissionList = getPermiList();
        for (String permission : permissions) {
            if (!hasPermi(permissionList, permission)) {
                throw new NotPermissionException(permission);
            }
        }
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     * @param permissions 权限码数组
     */
    public void checkPermiOr(String... permissions) {
        Set<String> permissionList = getPermiList();
        for (String permission : permissions) {
            if (hasPermi(permissionList, permission)) {
                return;
            }
        }
        if (permissions.length > 0) {
            throw new NotPermissionException(permissions);
        }
    }

    /**
     * 获取当前账号的权限列表
     * @return 权限列表
     */
    public Set<String> getPermiList() {
        UserCredential loginUser = SecurityContextHolder.get();
        return loginUser.getPermissions();
    }

    /**
     * 获取当前账号的角色列表
     * @return 角色列表
     */
    public Set<String> getRoleList() {
        UserCredential loginUser = SecurityContextHolder.get();
        return loginUser.getRoles();
    }

    /**
     * 判断是否包含权限
     * @param authorities 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(Collection<String> authorities, String permission) {
        return authorities.stream()
                .filter(StringUtils::hasText)
                .anyMatch(x -> AuthConstants.ALL_PERMISSION.contains(x) ||
                        PatternMatchUtils.simpleMatch(x, permission));
    }

    /**
     * 判断是否包含角色
     * @param roles 角色列表
     * @param role 角色
     * @return 用户是否具备某角色权限
     */
    public boolean hasRole(Collection<String> roles, String role) {
        return roles.stream()
                .filter(StringUtils::hasText)
                .anyMatch(x -> AuthConstants.SUPER_ADMIN.contains(x) ||
                        PatternMatchUtils.simpleMatch(x, role));
    }
}
