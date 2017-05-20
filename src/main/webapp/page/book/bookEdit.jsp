<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>书籍实体类 编辑页面</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <jsp:include page="../com/header.jsp"></jsp:include>
        <script type="text/javascript" src="../../js/comm/editcomm.js"></script>
        <script type="text/javascript">
		$(function(){
			getDataById(root + 'cms/book/findBookById',"#bookTmpl","#bookContent");
			$("#saveOrupdate").click(function(){
				saveData(root + 'cms/book/saveBook','bookList.jsp');
			});
		});
        </script>
    </head>
    <body>
      <section>
          <div>
              <div class="col-xs-12">
                  <div class="box" style="height:900px">
                      <div class="box-header">
                      <br>
                          <input type="button" value="确定" id="saveOrupdate" class="btn btn-success">
                          <button onclick="backAction('bookList.jsp');" class="btn btn-info" >返回</button>
                      </div>
                     <form id="submit_form" class="form-horizontal">
                        <div class="tab" id="bookContent">
                  	    </div>
                  	 </form>
              </div>
          </div>
      </section>
      <script type="text/x-jsrender" id="bookTmpl">
			<fieldset> 
				<input type="hidden" id="tId" name="id" value="{{:id}}" />
	        	{{!--<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">图片：</label>
       				 <div class="col-md-3">
						<div class="upload_div">
                        	<img id="myImageShow" src="../../{{:imgUrl}}" width="100px" height="100px" />
                        	<input type="hidden" id="imgUrl" name="imgUrl" value="{{:imgUrl}}"/>
                        	<input type="file" id="uploadPhotoFile" name="photoFile" class="upload_file">
                        </div>
					</div>
      			</div>--}}
				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">书籍名称：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写书籍名称" class="form-control input-sm" data-rule="required;length[0~500]" id="bookName" name="bookName" value="{{:bookName}}" />
					</div>
      			</div>
				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">作者：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写作者" class="form-control input-sm" data-rule="required;length[0~50]" id="autor" name="autor" value="{{:autor}}" />
					</div>
      			</div>
				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">国籍：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写国籍" class="form-control input-sm" data-rule="required;length[0~50]" id="navication" name="navication" value="{{:navication}}" />
					</div>
      			</div>
				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">作者介绍：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写作者介绍" class="form-control input-sm" data-rule="required;length[0~500]" id="autorDesc" name="autorDesc" value="{{:autorDesc}}" />
					</div>
      			</div>
			</fieldset>
	</script>
    </body>
</html>