package com.web.iread.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.web.webstart.base.entity.BaseEntity;

/**
 * 
* @ClassName: Book 
* @Description: 书籍实体类定义表
* @author eason
* @date 2015年3月23日 下午1:00:00 
*
 */
@Entity
@Table(name = "tb_xa_book")
@ApiModel(value="书籍实体类定义表")
public class Book extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="书籍名称,书籍名称")
	private String bookName;
	@ApiModelProperty(value="作者,作者")
	private String autor;
	@ApiModelProperty(value="国籍,国籍")
	private String navication;
	@ApiModelProperty(value="作者介绍,作者介绍")
	private String autorDesc;
		
	
	public void setBookName(String bookName){
		this.bookName=bookName;
	}
	
	@Column(nullable=true,length=500)
	public String getBookName(){
		return bookName;
	}
	public void setAutor(String autor){
		this.autor=autor;
	}
	
	@Column(nullable=true,length=50)
	public String getAutor(){
		return autor;
	}
	public void setNavication(String navication){
		this.navication=navication;
	}
	
	@Column(nullable=true,length=50)
	public String getNavication(){
		return navication;
	}
	public void setAutorDesc(String autorDesc){
		this.autorDesc=autorDesc;
	}
	
	@Column(nullable=true,length=500)
	public String getAutorDesc(){
		return autorDesc;
	}

	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", autor=" + autor
				+ ", navication=" + navication + ", autorDesc=" + autorDesc
				+ "]";
	}

}
