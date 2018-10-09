package com.qunchuang.mlshop.aspect;

import com.qunchuang.mlshop.anntations.RoleAuthority;
import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum;
import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.utils.RoleUtil;
import com.zzk.test.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.BadCredentialsException;
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
    public void doBefore(JoinPoint joinPoint, RoleAuthority roleAuthority) {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //匿名用户 principal=anonymousUser    普通用户登录为User   管理用户为Administ
        //如果是匿名用户之间返回未登录
        if (principal.getClass().isAssignableFrom(String.class)) {
            //抛出BadCredentialsException异常  会自动被spring处理 返回401
            throw new BadCredentialsException("未登录");
        }
        //如果roleAuthority  等于NO_ROLE 那么只要确定用户登录了  就通过权限验证
        if (principal.getClass().isAssignableFrom(User.class) && !(roleAuthority.value().getCode()).equals(RoleAuthorityFunctionEnum.NO_ROLE.getCode())) {
            throw new RuntimeException("不允许操作,无权限");
        }
        //如果roleAuthority  等于 具体的那么   就需要判断管理人员是否包含这个权限
        if (principal.getClass().isAssignableFrom(Administ.class) && !(roleAuthority.value().getCode()).equals(RoleAuthorityFunctionEnum.NO_ROLE.getCode())) {
            RoleUtil.checkRoleFunction((Administ) principal, roleAuthority.value().getCode());
        }


    }
}
