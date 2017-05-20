package com.web.iread.business.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.web.webstart.base.entity.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tb_xa_animal_describeaName")
@ApiModel(value="动物描述实体类定义表")
public class AnimalDescribe extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "描述的动物名字")
	private String describeName;
	
	@ApiModelProperty(value= "描述的动物属于那种动物的id")
	private Long tid;
	
	@ApiModelProperty(value= "描述的动物的颜色")
	private String describeColor;
	
	
	@Column(nullable=true,length=100)
	public String getDescribeName() {
		return describeName;
	}
	public void setDescribeName(String describeName) {
		this.describeName = describeName;
	}
	
	
	@Column(nullable=true,length=50)
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	
	
	@Column(nullable=true,length=50)
	public String getDescribeColor() {
		return describeColor;
	}
	public void setDescribeColor(String describeColor) {
		this.describeColor = describeColor;
	}

	private String animalName;
	private Integer status;

	
	@Transient
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getAnimalName() {
		return animalName;
	}
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}
	
	public AnimalDescribe(){}
	public AnimalDescribe(String describeName,String animalName, String describeColor,Integer status)
	{
		super();
		this.describeName=describeName;
		this.animalName=animalName;
		this.describeColor=describeColor;
		this.status=status;
		
	}
}
