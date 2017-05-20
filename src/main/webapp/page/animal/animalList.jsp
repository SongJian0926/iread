<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>动物实体管理类</title>
	<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
	<jsp:include page="../com/header.jsp"></jsp:include>
	<script type="text/javascript" src="../../js/comm/listcomm.js"></script>
	<script type="text/javascript">
			var editUrl = 'animalEdit.jsp?id=';//增加，修改的时候需要跳转的页面
			var operateUrl = root + 'cms/animal/operateAnimalByIds';//删除的时候需要请求的action的一部分
			var contentUrl = root + "cms/animal/findAnimalNEStatusPage";
			
		function getfilter(){
		var _jsonFilter = "{";
				if($("#search_bookName").val() && $("#search_bookName").val().length > 0){
			_jsonFilter += "'search_LIKE_bookName':'"+$("#search_bookName").val()+"',";
		}
				if($("#search_autor").val() && $("#search_autor").val().length > 0){
			_jsonFilter += "'search_EQ_autor':'"+$("#search_autor").val()+"',";
		}
				if($("#search_navication").val() && $("#search_navication").val().length > 0){
			_jsonFilter += "'search_EQ_navication':'"+$("#search_navication").val()+"',";
		}
				if($("#search_autorDesc").val() && $("#search_autorDesc").val().length > 0){
			_jsonFilter += "'search_EQ_autorDesc':'"+$("#search_autorDesc").val()+"',";
		}
				if(_jsonFilter != "{"){
			_jsonFilter = _jsonFilter.substring(0,_jsonFilter.length - 1);
		}
		_jsonFilter += "}";
		return _jsonFilter;
	}
	</script>
  </head>
  <body>
 	 <section>
 	 	<div>
 	 		<div class="box-body table-responsive" style="clear:both;">
                          <input type="button" value="新增" id="addSome" class="btn btn-success">
                          <input type="button" value="编辑" id="updateSome" class="btn btn-info">
                          <input type="button" value="删除" id="deleteSome" class="btn btn-danger">
                      </div>
             <div class="box-body table-responsive">
             		<table id="example2" class="table table-bordered table-hover">
             			<thead>
             				<tr>
             					 <th width="30"><input type="checkbox" id="_selectAll" /></th>
          		               		  <th width="80">动物名字</th>
				               		  <th width="80">动物类型</th>
				               		  <th width="80">动物数量</th>
				               		  <th width="80">状态</th>
             				</tr>
             			</thead>
             			<tbody id="mycontent"></tbody>
             		</table>
             		
             		  <div class="row page_big_div" id="displayPage"></div><!-- 分页的内容 -->
             		  
             		   <script id="tableContentTmple" type="text/x-jsrender">
								<tr>
									<td><input id="ck_{{:id}}" ckId="{{:id}}" ckStatus="{{:status}}" class="ckSelect" type="checkbox" /></td>
									//这是个复选框，可以通过selected 获取到你选择的内容
									<td>{{:animalName}}</td>
									<td>{{:animalType}}</td>
									<td>{{:animalNum}}</td>
									<td>{{if status==1}}正常{{else status==2}}已发布{{else status==3}}已删除{{else status==-1}}已锁定{{else}}无效{{/if}}</td>
								</tr>
						  </script>
             		
             		</div>
 	 	</div>
 	 </section>
  </body>
</html>
