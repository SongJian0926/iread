<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8">
	<title>书籍实体类管理</title>
	<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
	<jsp:include page="../com/header.jsp"></jsp:include>
	<script type="text/javascript" src="../../js/comm/listcomm.js"></script>
	<script type="text/javascript">
	
	var contentUrl = root + "cms/book/findBookNEStatusPage";
	
	var editUrl = 'bookEdit.jsp?id=';
	
	var operateUrl = root + 'cms/book/operateBookByIds';
	
	
	function getfilter(){
		var _jsonFilter = "{";
				if($("#search_bookName").val() && $("#search_bookName").val().length > 0){
			_jsonFilter += "'search_EQ_bookName':'"+$("#search_bookName").val()+"',";
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
		$(document).ready(function(){
			$('#exportSome').click(function(){
				location.href = root + 'cms/book/export';
			});
		});
	</script>
    </head>
    <body>
      <section>
          <div>
              <div class="col-xs-12">
                  <div class="box">
	                  <form id="search_form" class="form-horizontal">
                      	<div class="tabSelect" id="selectHeadId">
                      		<br>
           	            	<div class="form-group  col-md-3 search-form">
        						<label class="col-md-4 control-label controls" for="formGroupInputSmall">书籍名称：</label>
       				 			<div class="col-md-8">
									<input type="text" placeholder="书籍名称" class="form-control input-sm" id="search_bookName" name="bookName"/>
								</div>
      						</div>
			            	<div class="form-group  col-md-3 search-form">
        						<label class="col-md-4 control-label controls" for="formGroupInputSmall">作者：</label>
       				 			<div class="col-md-8">
									<input type="text" placeholder="作者" class="form-control input-sm" id="search_autor" name="autor"/>
								</div>
      						</div>
			            	<div class="form-group  col-md-3 search-form">
        						<label class="col-md-4 control-label controls" for="formGroupInputSmall">国籍：</label>
       				 			<div class="col-md-8">
									<input type="text" placeholder="国籍" class="form-control input-sm" id="search_navication" name="navication"/>
								</div>
      						</div>
			            	<div class="form-group  col-md-3 search-form">
        						<label class="col-md-4 control-label controls" for="formGroupInputSmall">作者介绍：</label>
       				 			<div class="col-md-8">
									<input type="text" placeholder="作者介绍" class="form-control input-sm" id="search_autorDesc" name="autorDesc"/>
								</div>
      						</div>
			            	<div class="form-group  col-md-3 search-form">
        						<label class="col-md-4 control-label controls" for="formGroupInputSmall">作者介绍：</label>
       				 			<div class="col-md-8">
									<input type="text" placeholder="作者介绍" class="form-control input-sm" id="search_autorDesc" name="autorDesc"/>
								</div>
      						</div>
				            <input type="button" value="查询" id="search" class="btn btn-primary searchBtn">
                      	</div>
	                  </form>
                      <div class="box-body table-responsive" style="clear:both;">
                          <input type="button" value="新增" id="addSome" class="btn btn-success">
                          <input type="button" value="编辑" id="updateSome" class="btn btn-info">
                          <input type="button" value="启用" id="openSome" class="btn btn-success">
                          <input type="button" value="锁定" id="lockSome" class="btn btn">
                          <input type="button" value="删除" id="deleteSome" class="btn btn-danger">
                          <input type="button" value="导出" id="exportSome" class="btn btn-danger">
                      </div>
                      <div class="box-body table-responsive">
                          <table id="example2" class="table table-bordered table-hover">
                              <thead>
                                  <tr>
                                      <th width="30"><input type="checkbox" id="_selectAll" /></th>
          		               		  <th width="80">书籍名称</th>
				               		  <th width="80">作者</th>
				               		  <th width="80">国籍</th>
				               		  <th width="80">作者介绍</th>
				               		  <th width="80">状态</th>
                                  </tr>
                              </thead>
                              <!-- 表格内容 start -->
                              <tbody id="mycontent"></tbody>
                              <!-- 表格内容 end -->
                          </table>
                          <!-- 分页标签 start -->
                          <div class="row page_big_div" id="displayPage"></div>
                          <!-- 分页标签 end -->
                          <script id="tableContentTmple" type="text/x-jsrender">
								<tr>
									<td><input id="ck_{{:id}}" ckId="{{:id}}" ckStatus="{{:status}}" class="ckSelect" type="checkbox" /></td>
									<td>{{:bookName}}</td>
									<td>{{:autor}}</td>
									<td>{{:navication}}</td>
									<td>{{:autorDesc}}</td>
									<td>{{if status==1}}正常{{else status==2}}已发布{{else status==3}}已删除{{else status==-1}}已锁定{{else}}无效{{/if}}</td>
								</tr>
						  </script>
                  </div>
              </div>
          </div>
      </section>
    </body>
</html>