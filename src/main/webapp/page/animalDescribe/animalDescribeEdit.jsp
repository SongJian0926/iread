<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>动物 编辑页面</title>
       <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <jsp:include page="../com/header.jsp"></jsp:include>
        <script type="text/javascript" src="../../js/comm/editcomm.js"></script>
        <script type="text/javascript">
    		$(function(){
    				getDataById(root + 'cms/animal/findAnimalDescribeById',"#animalTmpl","#animalContent");
			$("#saveOrupdate").click(function(){
				saveData(root + 'cms/AnimalDescribe/saveAnimalDescribe','AnimalDescribesList.jsp');
			});
    		$.ajax({
    			url:'../../cms/animalDescribe/findanimalNEStatusPage',
				dataType:'json',
				type:'POST',
    			data:{},
    			sucess:function(data1)
    			{
    				if(data1.code == 1)
    				{
    					var _options = "<option value=''>请选择</option>";
    					$.each(data1.object.content,function(){
					      if(data.object.bookId == this.id){
					  _options += "<option value='" + this.id + "' selected>" + this.animalName + "</option>";					       
					     }
					     else{
					  _options += "<option value='" + this.id + "'>" + this.animalName + "</option>";					       
					           }	            	
					    });
					    $('#animalName').html(_options);
    				}
    			else{
						alert(data1.message);
					}
    			}
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
                          <input type="button" value="提交" id="saveOrupdate" class="btn btn-success">
                          <button onclick="backAction();" class="btn btn-info" >返回</button>
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
	
				<input type="hidden" id="id" name="id" value="{{:id}}" />

				 <div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">动物名字：</label>
       				 <div class="col-md-3">
						<input type="text" class="form-control input-sm" data-rule="required;length[0~10];text" id="describeName" name="describeName" value="{{:describeName}}" />
					</div>
      			</div>
				<div class="form-group">
				  <label class="col-md-2 control-label controls" for="formGroupInputSmall">动物类型：</label>
	                 <select name="animalId"  id="animalName" value="{{:animalId}}">                   
                     </select>
				</div>

				 <div class="form-group">
        			<label class="col-md-2 control-label controls" for="formGroupInputSmall">动物颜色：</label>
       				 <div class="col-md-3">
						<input type="text" class="form-control input-sm" data-rule="required;length[0~10]" id="author" name="author" value="{{:author}}" />
					</div>
      			</div>
			</fieldset>
		</script>
  </body>
</html>
