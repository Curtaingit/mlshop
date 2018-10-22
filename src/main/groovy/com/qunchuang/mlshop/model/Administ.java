package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity;
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@SchemaDocumentation("管理员")
@CompileStatic
@Bostype("A06")
@Getter
@Setter
public class Administ extends BosEntity implements UserDetails{
    @SchemaDocumentation("姓名")
    @NotNull(message = "xxxxxx")
    String name;
    //todo  异常提示应该是获取 实体类中书写的message 作为反馈     @NotNull（message=xxx）

    @SchemaDocumentation("联系方式")
    String tel;

    @SchemaDocumentation("用户名")
    String username;

    @SchemaDocumentation("密码")
    String password;

//    @SchemaDocumentation("权限")
//    String functionAuthority;


    @ManyToOne
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list=new ArrayList<>();
//        for(String role : .split(",")){
//            list.add(new SimpleGrantedAuthority(role));
//        }

        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return this.username;
    }

}
