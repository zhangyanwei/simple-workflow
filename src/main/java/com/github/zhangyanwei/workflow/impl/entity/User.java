package com.github.zhangyanwei.workflow.impl.entity;

import com.github.zhangyanwei.workflow.impl.entity.converter.RoleSetConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.EnumSet;

// http://stackoverflow.com/questions/13012584/jpa-how-to-convert-a-native-query-result-set-to-pojo-class-collection
@Entity
@Table(name = "x_user")
public class User extends Base {

    private static final long serialVersionUID = 4015081435369839569L;

    @NotNull
    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "password")
    private String password;

    @Convert(converter = RoleSetConverter.class)
    @Column(name = "authorities")
    private EnumSet<Role> authorities;

    @Column(name = "last_access_time")
    private Date lastAccessTime;

    @Column(name = "registration_time")
    private Date registrationTime;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumSet<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(EnumSet<Role> authorities) {
        this.authorities = authorities;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}