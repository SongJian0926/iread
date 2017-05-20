package com.web.webstart.base.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.util.XaUtil;
import com.wordnik.swagger.annotations.ApiModelProperty;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * ID
	 */
	private Long id;

	@ApiModelProperty(value = "状态，0为无效，1为正常,2为发布,3删除 参看XaConstant.Status")
	private Integer status;

	@ApiModelProperty(value = "@Fields createUser : 创建者")
	private String createUser;

	@ApiModelProperty(value = "@Fields createTime : 创建时间")
	private String createTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 为确保赋值增加默认值1:正常
	 */
	@Column(nullable = false, columnDefinition = "int default 1")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "createUser")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "createTime")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 数据插入前的操作
	 */
	@PrePersist
	public void setInsertBefore() {
		this.createTime = XaUtil.getToDayStr();
		this.status = XaConstant.Status.valid;
	}

}
