package com.atguigu.vo;


/**
 * 注册对象
 */
public class RegisterVo {

    // 昵称
    private String nickName;

    // 手机号
    private String phone;

    // 密码
    private String password;

    // 验证码
    private String code;


    public RegisterVo() {
    }

    public RegisterVo(String nickName, String phone, String password, String code) {
        this.nickName = nickName;
        this.phone = phone;
        this.password = password;
        this.code = code;
    }

    /**
     * 获取
     * @return nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return "RegisterVo{nickName = " + nickName + ", phone = " + phone + ", password = " + password + ", code = " + code + "}";
    }
}
