package com.qunchuang.mlshop.aspect;

import com.qunchuang.mlshop.anntations.RoleAuthority;
import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum;
import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.utils.RoleUtil;
import com.zzk.test.domain.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Aspect
@Component
@RestController
public class RoleAuthorityCheckAspect {

    @Pointcut("execution(public * com.qunchuang.mlshop.controller.*.*(..)) " +
            " && @annotation(roleAuthority)")
    public void init(RoleAuthority roleAuthority) {
    }

    @Before("init(roleAuthority)")
    public void doBefore(RoleAuthority roleAuthority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //1.未登录用户访问 需要权限的接口
        if (authentication == null) {
            throw new BadCredentialsException("对不起你还未登录，请先登录!");
        }
        Object principal = authentication.getPrincipal();
        //2.普通用户登录  判断是否访问 权限以外的接口
        if (principal.getClass().isAssignableFrom(User.class) && !(roleAuthority.value().getCode()).equals(RoleAuthorityFunctionEnum.NO_ROLE.getCode())) {
            throw new RuntimeException("不允许操作,无权限");
        }
        //3.管理员登录  判断是否访问 权限以外的接口
        if (principal.getClass().isAssignableFrom(Administ.class) && !(roleAuthority.value().getCode()).equals(RoleAuthorityFunctionEnum.NO_ROLE.getCode())) {
            RoleUtil.checkRoleFunction((Administ) principal, roleAuthority.value().getCode());
        }
    }
}
