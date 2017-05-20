package com.web.iread.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.web.webstart.base.entity.BaseEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "tb_xa_animal")
@ApiModel(value = "动物实体类定义表")
public class Animal extends BaseEntity
{
	
	private static final long serialVersionUID = 1L;
	

	@ApiModelProperty(value = "动物名字,动物名字")
	private String animalName;
	@ApiModelProperty(value = "动物类型,动物类型")
	private String animalType;
	@ApiModelProperty(value= "动物数量,动物数量")
	private String animalNum;
	
	
	@Column(nullable=true,length=200)
	public String getAnimalName() {
		return animalName;
	}
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}
	
	@Column(nullable=true,length=100)
	public String getAnimalType() {
		return animalType;
	}
	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}
	
	@Column(nullable=true,length=50)
	public String getAnimalNum() {
		return animalNum;
	}
	public void setAnimalNum(String animalNum) {
		this.animalNum = animalNum;
	}
	
	
	public String toString()
	{
		return "Animal [animalName="+ animalName +",animalType = "+animalType+",animalNum = "+animalNum+"]";
	}
}
