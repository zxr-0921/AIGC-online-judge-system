package com.zxr.aiojbakcend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *  用户实体类
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 身份证号码
     */
    private String idNum;

    /**
     * 姓
     */
    private String firstName;

    /**
     * 名
     */
    private String lastName;

    /**
     * 身份
     */
    private String identity;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别（0-女，1-男）
     */
    private Integer sex;

    /**
     * 头像图片地址
     */
    private String avatarImageUrl;

    /**
     * 个性签名
     */
    private String personalSignature;

    /**
     * 个人简介
     */
    private String personProfile;

    /**
     * 账号状态（0-正常，1-禁用）
     */
    private Integer status;

    /**
     * 删除键（0-正常，1-删除）
     */
    @TableLogic
    private Integer idDelete;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 更新时间
     */
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}