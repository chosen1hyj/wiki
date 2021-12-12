package com.hyj.wiki.req;

import javax.validation.constraints.NotNull;

public class UserLoginReq {
    @NotNull(message = "[用户名]不能为空")
    private String loginName;

    @NotNull(message = "[密码]不能为空")
//    @Length(min = 6, max = 20, message = "[密码]6~20位")
//    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-z]+$)[0-9A-Za-z]{6,20}$", message = "[密码]至少包含数字和英文，长度6-20")
    private String password;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "loginName='" + loginName + '\'' +
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