package com.hyj.wiki.req;

import javax.validation.constraints.NotNull;

public class UserResetPasswordReq {
    private Long id;

    @NotNull(message = "[密码]不能为空")
//    @Length(min = 6, max = 20, message = "[密码]6~20位")
//    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-z]+$)[0-9A-Za-z]{6,20}$", message = "[密码]至少包含数字和英文，长度6-20")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserResetPasswordReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}