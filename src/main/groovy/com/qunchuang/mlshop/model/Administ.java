package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity;
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.PrivilegeConstraint;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@SchemaDocumentation("管理员")
@CompileStatic
@Bostype("A06")
@Getter
@Setter
@PrivilegeConstraint(expression = "#p.id.endsWith('C03')?'{\"key\":\"id\",\"operator\":\"EQUEAL\",\"value\":\"#p.id\"}':#p.id.endsWith('A06')?#p.getConstraint('ADMIN_MANAGEMENT'):'false'")
//@PrivilegeConstraint(key = "#p.id.contains('A07')?'{\"key\":\"id\",\"operator\":\"EQUEAL\",\"value\":\"lJGJakfJEfGrhNPDC7rsb2A06\"}':null")
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
                .map(RoleItem::getRole)
                .flatMap(role -> role.getPrivilegeItems().stream())
                .map(PrivilegeItem::getPrivilege)
                .map(privilege -> new SimpleGrantedAuthority(privilege.getAuthority()))
                .collect(Collectors.toSet());

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
        return "Administ{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", username='" + username + '\'' +
                ", roleItems=" + roleItems +
                '}';
    }

    public String getConstraint(String privilege) {
        Collection<? extends GrantedAuthority> authorities = this.getAuthorities();

        if (!authorities.contains(new SimpleGrantedAuthority(privilege))) {
//            throw new AccessDeniedException("权限不足");
           return "false";
        }

        List<String> collect = this.getRoleItems().stream().map(RoleItem::getRole)
                .flatMap(role -> role.getPrivilegeItems().stream())
                //如果连个角色中包含同一个权限  且设定了不同的约束条件  todo 待处理
                .filter(privilegeItem -> privilegeItem.getConstraintRule() != null && privilegeItem.getPrivilege().getPrivilege().contains(privilege))
                .map(PrivilegeItem::getConstraintRule)
                .collect(Collectors.toList());
        if (collect.size() == 0) {
            return null;
        } else {
            return collect.get(0);
        }
    }

}
