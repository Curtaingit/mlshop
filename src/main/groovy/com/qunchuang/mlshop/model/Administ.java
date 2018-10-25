package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity;
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.anntations.AccountType;
import com.qunchuang.mlshop.anntations.PrivilegeType;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.qunchuang.mlshop.enums.RoleAuthorityFunctionConst.ADMIN_MANAGEMENT;

@Entity
@SchemaDocumentation("管理员")
@CompileStatic
@Bostype("A06")
@Getter
@Setter
@AccountType("Administ")
@PrivilegeType(ADMIN_MANAGEMENT)
public class Administ extends BosEntity implements UserDetails {
    @SchemaDocumentation("姓名")
//    @NotNull
    String name;

    @SchemaDocumentation("联系方式")
//    @Length(min = 11, max = 11, message = "长度不正确")
    String tel;

    @SchemaDocumentation("用户名")
    String username;

    @SchemaDocumentation("密码")
    String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true, fetch = FetchType.EAGER)
    Set<RoleItem> roleItems = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> collect = roleItems
                .stream()
                .map(roleItem -> roleItem.getRole())
                .flatMap(role -> role.getPrivilegeItems().stream())
                .map(privilegeItem -> privilegeItem.getPrivilege())
                .map(privilege -> new SimpleGrantedAuthority(privilege.getAuthority()))
                .collect(Collectors.toSet());
        return new ArrayList<>(collect);
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
