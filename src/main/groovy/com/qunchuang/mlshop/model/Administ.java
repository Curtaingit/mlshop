package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity;
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@SchemaDocumentation("管理员")
@CompileStatic
@Bostype("A06")
@Getter
@Setter
public class Administ extends BosEntity implements UserDetails {
    @SchemaDocumentation("姓名")
    @NotNull
    String name;

    @SchemaDocumentation("联系方式")
    @Length(min = 11, max = 11, message = "长度不正确")
    String tel;

    @SchemaDocumentation("用户名")
    String username;

    @SchemaDocumentation("密码")
    String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true, fetch = FetchType.EAGER)
    Set<RoleItem> roleItems = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Privilege> collect = roleItems
                .stream()
                .map(roleItem -> roleItem.getRole())
                .flatMap(role -> role.getPrivilegeItems().stream())
                .map(privilegeItem -> privilegeItem.getPrivilege())
                .collect(Collectors.toList());

        return collect;
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
