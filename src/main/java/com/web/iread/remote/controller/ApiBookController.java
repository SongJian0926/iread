package com.web.iread.remote.controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.controller.BaseController;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.util.XaResult;
import com.web.webstart.base.util.WebUitl;
import com.web.iread.remote.service.ApiBookService;
import com.web.iread.remote.vo.BookVo;
import com.web.iread.business.entity.Book;

/**
 * @Title: ApiBookController.java
 * @Package com.web.iread.remote.controller
 * @Description: 书籍实体类信息接口
 * @author eason.zt
 * @date 2015年3月23日 下午1:00:00
 * @version V1.0
 */
@Api(value = "Book", description = "书籍实体类接口", position = 10)
@Controller
@RequestMapping("/api/book")
public class ApiBookController extends BaseController {

	@Autowired
	private ApiBookService bookService;
	
	/**
	 * @Title: findBookList
	 * @Description: 查询所有书籍实体类信息
	 * @return    
	 */
	@ApiOperation(value="查询所有书籍实体类",notes="查询所有书籍实体类信息,当返回的code=1时，取出object进行显示,存放为数组对象")
	@ResponseBody
	@RequestMapping(value="findBookList",method=RequestMethod.POST)
	public XaResult<List<BookVo>> findBookList(
		@ApiParam("页号,字段名:nextPage,默认0,从第0页开始") @RequestParam(defaultValue = "0") Integer nextPage,
		@ApiParam("页长,字段名:pageSize,默认10") @RequestParam(defaultValue = "10") Integer pageSize
	) throws BusinessException{
		Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, "[{property:'createTime',direction:'DESC'}]");
		Map<String , Object> filterParams =  WebUitl.getParametersStartingWith("{}", "search_");
		return bookService.findListEQStatusByFilter(XaConstant.Status.valid, filterParams, pageable);
	}
	
	/**
	 * @Title: findBookById
	 * @Description: 根据ID查找单条实体
	 * @param modelId
	 * @return    
	 */
	@ApiOperation(value="根据tId查询书籍实体类",notes="查询书籍实体类详细信息,当返回的code=1时，取出object进行显示,存放为单体对象")
	@ResponseBody
	@RequestMapping(value="findBookById",method=RequestMethod.POST)
	public XaResult<BookVo> findBookById(
		@ApiParam("编号,字段名:modelId,必填") @RequestParam(value = "modelId") Long modelId
	) throws BusinessException{
		return bookService.findOne(modelId);
	}
	
	/**
	 * @Title: save
	 * @Description: 新增一条实体
	 * @param userId
	 * @param model
	 * @return    
	 */
	@ApiOperation(value="新增书籍实体类",notes="供前端前期填充数据测试使用,上线后删除;新增书籍实体类,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="saveBook",method=RequestMethod.POST)
	public XaResult<BookVo> saveBook(
		@ApiParam("书籍名称,字段名:bookName") @RequestParam(value = "bookName") String bookName,
		@ApiParam("作者,字段名:autor") @RequestParam(value = "autor") String autor,
		@ApiParam("国籍,字段名:navication") @RequestParam(value = "navication") String navication,
		@ApiParam("作者介绍,字段名:autorDesc") @RequestParam(value = "autorDesc") String autorDesc,
		HttpServletRequest request
	) throws BusinessException{
		Book model = new Book();
		model.setBookName(bookName);
		model.setAutor(autor);
		model.setNavication(navication);
		model.setAutorDesc(autorDesc);
		return bookService.createModel(model);
	}
	
	/**
	 * @Title: 
	 * @Description: 修改一条实体
	 * @param userId
	 * @param model
	 * @return    
	 */
	@ApiOperation(value="修改书籍实体类",notes="修改书籍实体类,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="updateBook",method=RequestMethod.POST)
	public XaResult<BookVo> updateBook(
		@ApiParam("书籍名称,字段名:bookName") @RequestParam(value = "bookName") String bookName,
		@ApiParam("作者,字段名:autor") @RequestParam(value = "autor") String autor,
		@ApiParam("国籍,字段名:navication") @RequestParam(value = "navication") String navication,
		@ApiParam("作者介绍,字段名:autorDesc") @RequestParam(value = "autorDesc") String autorDesc,
		@ApiParam("版本编号,字段名:tId") @RequestParam(value = "tId") Long tId,
	HttpServletRequest request
	) throws BusinessException{
		Book model = new Book();
		model.setBookName(bookName);
		model.setAutor(autor);
		model.setNavication(navication);
		model.setAutorDesc(autorDesc);
		model.setId(tId);
		return bookService.updateModel(model);
	}
	
	/**
	 * @Title: operateBookById
	 * @Description: 操作一个实体状态,根据status进行操作
	 * @param modelId
	 * @param status 		操作类型:-1锁定,0无效,1正常,2发布,3删除,默认删除操作
	 * @return    
	 */
	@ApiOperation(value="根据ID修改书籍实体类状态",notes="修改书籍实体类状态,当返回的code=1时，保存成功后object返回对象")
	@ResponseBody
	@RequestMapping(value="operateBookById",method=RequestMethod.POST)
	public XaResult<BookVo> operateBookById(
		HttpServletRequest request,
		@ApiParam("编号,字段名:modelIds,必填") @RequestParam(value = "modelId") String modelIds,
		@ApiParam("操作类型,字段名:status,-1锁定,0无效,1正常,2发布,3删除,选填,默认删除操作") @RequestParam(defaultValue = "3") Integer status
	) throws BusinessException{
		return bookService.multiOperate(modelIds,status);
	}
	
	/**
	 * @Title: upload
	 * @Description: 图片上传
	 * @param photoFile
	 * @param request
	 * @return    
	 */
	@ApiOperation(value="图片上传",notes="异步图片上传,返回上传图片的地址、宽、高")
	@ResponseBody
	@RequestMapping(value="photoUpload",method=RequestMethod.POST)
	public XaResult<Map<String, Object>> photoUpload(
		@ApiParam("上传的图片,字段名:photoFile,必填") @RequestParam(value = "photoFile") MultipartFile photoFile, 
		HttpServletRequest request
	) throws BusinessException{
		XaResult<Map<String, Object>> result = new XaResult<Map<String, Object>>();
		String root=request.getSession().getServletContext().getRealPath("/");
		String picturePath = "/upload/book";
		String ext =photoFile.getOriginalFilename().substring(photoFile.getOriginalFilename().lastIndexOf("."));
		String newName = new Date().getTime()+ext;
		File filedict = new File(root+picturePath);
		if(!filedict.exists()){
			filedict.mkdir();
		}
		File targetFile=new File(root+picturePath+File.separator+newName);
		try {
			if(StringUtils.equalsIgnoreCase(ext, ".jpg") || StringUtils.equalsIgnoreCase(ext, ".png")){
				photoFile.transferTo(targetFile);
				BufferedImage bimg = ImageIO.read(targetFile);
				int width = bimg.getWidth();
				int height = bimg.getHeight();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pictureHeight",height);
				map.put("pictureWidth",width);
				map.put("picturePath",picturePath+"/"+newName);
				result.setObject(map);
				return result;
			}
			else{
				throw new BusinessException("上传文件类型不允许,请上传jpg/png图片");
			}
		} catch (IllegalStateException e) {
			throw new BusinessException("图片上传失败");
		} catch (IOException e) {
			throw new BusinessException("图片上传失败");
		} catch (Exception e) {
			throw new BusinessException("图片上传失败");
		}
	}
}

