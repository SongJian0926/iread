$.views.converters({
	"subString":function(str){
		if(str==null || str==""){
			return "";
		}
    	if(str.length>15){
    		return str.substring(0,15)+"...";
    	}else{
    		return str;
    	}
	},
});
//初始化分页参数
var nextPage = 0;
var pageSize = 10;

/** 删除事件 */
function del(delurl){
	var checkError = false;
	var delArray = [];
	$(".ckSelect").each(function(){
    	var check = $(this).is(':checked');
    	if(check){
			var _id = $(this).attr("ckId");
			delArray.push(_id);//调用批量删除
        }
    });
    if(delArray.length == 0){
		alert("请选择要删除的项"); return;
    }
    if(confirm("你确定要删除选中项吗？")){
    	var url = delurl + delArray.join(",");
    	$.ajax({
			url: url,
			type: 'post',
			dataType: 'json',
			success: function(data){
				if(data.code==1){
					dipscontent(getfilter(),contentUrl)
				}else{
					alert(data.msg);
				}
			}
        });
    }
}

/** 锁定事件 */
function lock(lockurl){
	var checkError = false;
	var delArray = [];
	$(".ckSelect").each(function(){
    	var check = $(this).is(':checked');
    	if(check){
			var _id = $(this).attr("ckId");
			delArray.push(_id);
        }
    });
    if(delArray.length == 0){
		alert("请选择要锁定的项"); return;
    }
    if(confirm("你确定要锁定选中项吗？")){
    	$.ajax({
			url: lockurl,
			type: 'post',
			data:{
				modelIds:delArray.join(","),
				status:-1
			},
			dataType: 'json',
			success: function(data){
				if(data.code==1){
					dipscontent(getfilter(),contentUrl)
				}else{
					alert(data.msg);
				}
			}
        });
    }
}

/** 启用事件 */
function unlock(unlockurl){
	var checkError = false;
	var delArray = [];
	$(".ckSelect").each(function(){
    	var check = $(this).is(':checked');
    	if(check){
			var _id = $(this).attr("ckId");
			delArray.push(_id);
        }
    });
    if(delArray.length == 0){
		alert("请选择要启用的项"); return;
    }
    if(confirm("你确定要启用选中项吗？")){
    	$.ajax({
			url: unlockurl,
			type: 'post',
			data:{
				modelIds:delArray.join(","),
				status:1
			},
			dataType: 'json',
			success: function(data){
				if(data.code==1){
					dipscontent(getfilter(),contentUrl)
				}else{
					alert(data.msg);
				}
			}
        });
    }
}

/** 加载内容 */
function dipscontent(jsonFilter,urls){
	if(getUrlParam('nextPage')){
		nextPage = getUrlParam('nextPage') - 1;
	}
	if($("#_click_page").val() != undefined && $("#_click_page").val()){
		nextPage = $("#_click_page").val();
	}
	if(typeof(nextPage) == 'undefined' || nextPage==""){
		nextPage = 0;
		$("#_click_page").val(nextPage);
	}
	if(getUrlParam('pageSize')){
		pageSize = getUrlParam('pageSize');
	}
	if($("#selectPageSize").val() && $("#selectPageSize").val() != undefined){
		pageSize = $("#selectPageSize").val();
	}
	if(typeof(pageSize) == 'undefined' || pageSize==""){
		pageSize = 10;
		$("#selectPageSize").val(pageSize);
	}
	$.ajax({
		url:urls,
		dataType:'json',
		type:'POST',
		data:{
			jsonFilter: jsonFilter,
			nextPage: nextPage,
			pageSize:pageSize
		},
		success:function(data){
			console.log(data);
			if(data.code == 1){
            	var tblContentHtml = $("#tableContentTmple").render(data.object.content);
            	$("#mycontent").html(tblContentHtml);
            	//加载分页器
            	loadTmpl.renderExternalTemplate("page", "#displayPage", data.object , 1);
			}
			else{
				alert(data.message);
			}
		}
	});
}

//页面加载的时候开启的内容
$(function(){
	//回车。当我们输入完网页的时候会有个回车动作 这样就会触发这个事件
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	dipscontent(getfilter(),contentUrl)//getfilter 是一个在list页面定义好的方法用于做复杂查询的
	     }
	}
	//表格列拖动以及行高亮选中
	$("#example2").resizableColumns({});
	$("#example2").tableGrid({
		checkAllId: "_selectAll",	//全选框的ID属性
		singleCheckboxClass: "ckSelect",
		selectRowClass: "warning",
		paging: "displayPage",
		pageAjax: function(){
			dipscontent(getfilter(),contentUrl);
		}
	});
	
	//点击查询按钮
	$("#search").click(function(){
		dipscontent(getfilter(),contentUrl);
	});
	
	//点击新增按钮  id是0
	$("#addSome").click(function(){
		location.href = editUrl + 0;
	});
			
	//点击修改按钮 
	//需要对数据库访问，同时还需要判断你选择的数量，修改一次只能修改一条数据，判断条数在这里判断
	$("#updateSome").click(function(){
		var updateArray = [];
		$(".ckSelect").each(function(){
	    	var check=$(this).is(':checked');
	    	if(check    ){
				var _id =$(this).attr("ckId");
				updateArray.push(_id);//push 压栈，也就是把id存放到updata数组中
	        }
	    });
		//这块的代码就是那带你选择的内容，然后下面做判断，满足1个即可继续执行。
		//带选择内容的一些字段 ，带着url + 字段超链到editUrl页面
		
		if(updateArray.length == 0){
			alert("请选择要编辑的项"); return;
		}
		if(updateArray.length > 1){
			alert("每次只能编辑一项"); return;
		}
		var from = window.location.href.split("?")[0] + "?nextPage=" + (parseInt($("#_click_page").val())+1) + "&pageSize=" + $("#selectPageSize").val();
		location.href = editUrl + updateArray[0] + '&from=' + from;
	});
			
	//点击删除按钮
	$("#deleteSome").click(function() {
         del(operateUrl + '?modelIds=');
	});
			
	//点击锁定按钮
	$("#lockSome").click(function() {
		lock(operateUrl);
	});
			
	//点击启用按钮
	$("#openSome").click(function() {
		unlock(operateUrl);
	});
	
	//分页点击事件
	$('#example2_paginate').find('a').click(function(){
		dipscontent(getfilter(),contentUrl);
	});
	
});