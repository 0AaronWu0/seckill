
/*************************************************************
**@beanName：UserEntity
**@descrption：用户信息表
**@author ：
**@createTime ：2016-12-12 19:49:55
**************************************************************/

package org.seckill.entity;

import java.util.Date;

/**
*
*用户信息表(USER)
*
*@author ${autor}
*@version 1.0.0 2016-12-12
*/
public class Suser implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6271963988703809531L;
    
    /** 用户ID */
    private String userId;
    /** 用户名 */
    private String userName;    
    /** 密码 */
    private String userPassword;   
    /** 邮箱 */
    private String userEmail;    
    /** 手机 */
    private String userPhone;    
    /** 令牌 */
    private String userToken;  
    /** 说明 */
    private String userDesc;
    /** 创建时间 */
    private Date createTime;
    
    public Suser() {
		super();
	}
	public Suser(String userName, String userPassword, String userEmail, String userPhone,
			String userToken, String userDesc) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userToken = userToken;
		this.userDesc = userDesc;
	}
    /**
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public String getUserId() {
        return this.userId;
    }
     

	/**
     * 设置用户ID
     * 
     * @param userId
     *          用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public String getUserName() {
        return this.userName;
    }
     
    /**
     * 设置用户名
     * 
     * @param userName
     *          用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getUserPassword() {
        return this.userPassword;
    }
     
    /**
     * 设置密码
     * 
     * @param userPassword
     *          密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
    /**
     * 获取邮箱
     * 
     * @return 邮箱
     */
    public String getUserEmail() {
        return this.userEmail;
    }
     
    /**
     * 设置邮箱
     * 
     * @param userEmail
     *          邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    /**
     * 获取手机
     * 
     * @return 手机
     */
    public String getUserPhone() {
        return this.userPhone;
    }
     
    /**
     * 设置手机
     * 
     * @param userPhone
     *          手机
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    
    /**
     * 获取令牌
     * 
     * @return 令牌
     */
    public String getUserToken() {
        return this.userToken;
    }
     
    /**
     * 设置令牌
     * 
     * @param userToken
     *          令牌
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
    
    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }
     
    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 获取说明
     * 
     * @return 说明
     */
    public String getUserDesc() {
        return this.userDesc;
    }
     
    /**
     * 设置说明
     * 
     * @param userDesc
     *          说明
     */
    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userEmail="
				+ userEmail + ", userPhone=" + userPhone + ", userToken=" + userToken + ", createTime=" + createTime
				+ ", userDesc=" + userDesc + "]";
	}
    
    
}