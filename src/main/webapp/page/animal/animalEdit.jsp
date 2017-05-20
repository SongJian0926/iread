<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>动物实体类 编辑页面</title>
       <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <jsp:include page="../com/header.jsp"></jsp:include>
        <script type="text/javascript" src="../../js/comm/editcomm.js"></script>
        <script type="text/javascript">
    	$(function(){
			getDataById(root + 'cms/animal/findAnimalById',"#animalTmpl","#animalContent");
			$("#saveOrupdate").click(function(){
				saveData(root + 'cms/animal/saveAnimal','animalList.jsp');
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
                          
                          <!-- backAction是一个已经定义好的返回函数，参数是返回地址 -->
                          <button onclick="backAction('animalList.jsp');" class="btn btn-info" >返回</button>
                      </div>
                     <form id="submit_form" class="form-horizontal">
                        <div class="tab" id="animalContent">
                  	    </div>
                  	 </form>
              </div>
          </div>
      </section>
      
      
     <!-- 当点击新增的时候，参数id=0，所以下载下面数据的时候不会显示内容，也就是说value值是没有的 -->
     <!-- 当点击更新的时候， 参数是获取的你选中的那条数据的内容，是通过ckSelect的checked的属性获取选定数据的内容 -->
	<script type="text/x-jsrender" id="animalTmpl">
			<fieldset> 
				<input type="hidden" id="tId" name="id" value="{{:id}}" />

				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">动物名字：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写动物名字" class="form-control input-sm" data-rule="required;length[0~500]" id="animalName" name="animalName" value="{{:animalName}}" />
					</div>
      			</div>
				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">动物数量：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写动物数量" class="form-control input-sm" data-rule="required;length[0~50]" id="animalNum" name="animalNum" value="{{:animalNum}}" />
					</div>
      			</div>
				<div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">动物类型：</label>
       				 <div class="col-md-3">
						<input type="text" placeholder="请填写动物类型" class="form-control input-sm" data-rule="required;length[0~50]" id="animalType" name="animalType" value="{{:animalType}}" />
					</div>
      			</div>
			</fieldset>
	</script>
	
	
  </body>
</html>
