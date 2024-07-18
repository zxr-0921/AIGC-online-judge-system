package com.zxr.aiojbakcend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class LoginUserVo implements Serializable {
    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 身份证号码
     */
    @TableField(value = "id_num")
    private String idNum;

    /**
     * 姓
     */
    @TableField(value = "first_name")
    private String firstName;

    /**
     * 名
     */
    @TableField(value = "last_name")
    private String lastName;

    /**
     * 身份
     */
    @TableField(value = "identity")
    private String identity;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 性别（0-女，1-男）
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 头像图片地址
     */
    @TableField(value = "avatar_image_url")
    private String avatarImageUrl;

    /**
     * 个性签名
     */
    @TableField(value = "personal_signature")
    private String personalSignature;

    /**
     * 个人简介
     */
    @TableField(value = "person_profile")
    private String personProfile;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_created")
    private Date gmtCreated;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}