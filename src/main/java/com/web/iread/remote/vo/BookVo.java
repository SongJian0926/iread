package com.web.iread.remote.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: BookVo
 * @Description:书籍实体类 Vo
 * @author eason.zt
 * @date 2015年3月23日 下午1:00:00
 *
 */
@ApiModel(value="书籍实体类Vo对象")
public class BookVo {
	@ApiModelProperty(value="主键，自动增长")
	private Long id;
	
	@ApiModelProperty(value="创建时间")
	private String createTime;
	
 	@ApiModelProperty(value="书籍名称,书籍名称")
	private String bookName;
	@ApiModelProperty(value="作者,作者")
	private String autor;
	@ApiModelProperty(value="国籍,国籍")
	private String navication;
	@ApiModelProperty(value="作者介绍,作者介绍")
	private String autorDesc;
		
	public BookVo(Long id,String bookName,String autor,String navication,String autorDesc) {
		this.id = id;
		this.bookName = bookName;
		this.autor = autor;
		this.navication = navication;
		this.autorDesc = autorDesc;
	}
	
	public BookVo() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
		public void setBookName(String bookName){
		this.bookName=bookName;
	}
	
	public String getBookName(){
		return bookName;
	}
		public void setAutor(String autor){
		this.autor=autor;
	}
	
	public String getAutor(){
		return autor;
	}
		public void setNavication(String navication){
		this.navication=navication;
	}
	
	public String getNavication(){
		return navication;
	}
		public void setAutorDesc(String autorDesc){
		this.autorDesc=autorDesc;
	}
	
	public String getAutorDesc(){
		return autorDesc;
	}
		
}

