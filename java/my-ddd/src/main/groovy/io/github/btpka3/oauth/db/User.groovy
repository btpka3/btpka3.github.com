package io.github.btpka3.oauth.db

import com.sun.xml.internal.rngom.parse.host.Base
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document


@Document
@CompoundIndexes({
})
public class User extends Base {

    /**
     * 是否有效
     */
    boolean enabled = true;

    /**
     * 帐号是否锁定
     */
    // FIXME: 账户被锁定的同时密码也不存在，则视为刚输入手机号码注册。但没有输入密码
    boolean accountLocked = false;

    /**
     * 帐号是否过期
     */
    boolean accountExpired = false;

    /**
     * 邀请人
     */
    @DBRef
    User inviter;

    // ---------------------------------- 绑定用户信息:  用户名、密码、支付密码

    /**
     * 用户名
     */
    @Indexed(unique = true, sparse = true)
    String username;

    /**
     * 登录密码
     */
    String password;

    /**
     * 支付密码
     *
     * @deprecated 暂未使用
     */
    @Deprecated
    String payPassword;

    // ---------------------------------- 绑定用户信息:  手机号
    /**
     * 手机号
     */
    @Indexed(unique = true, sparse = true)
    String phone;

    /**
     * 手机号验证的时间。是有当该字段非空时，才表示手机号完成绑定。
     */
    Date phoneVerifiedAt;

    // ---------------------------------- 绑定用户信息:  微信公众号的账户
    // 注意：该表中微信用户信息必须是同一个微信开放平台下的应用

    /**
     * 微信授权登录对应的openId
     */
    @Indexed(unique = true, sparse = true)
    String openId;

    /**
     * 微信登录时的code
     */
    String code;

    // ---------------------------------- 绑定用户信息:  微信企业号的员工账户
    // 注意：该表中微信用户信息必须是同一个微信开放平台下的应用

    /**
     * 微信授权登录对应的openId
     */
    @Indexed(unique = true, sparse = true)
    String wxQyhOpenId;

    /**
     * 微信登录时的code
     */
    @Indexed(unique = true, sparse = true)
    String wxQyhUserId;

    // ---------------------------------- 绑定用户信息:  电子邮箱

    /**
     * 邮箱
     */
    @Indexed(unique = true, sparse = true)
    String email;

    /**
     * 邮箱验证的时间。是有当该字段非空时，才表示邮箱完成绑定。
     */
    Date emailVerifiedAt;


}
