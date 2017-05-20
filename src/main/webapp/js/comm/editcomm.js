/** 图片上传处理预留,有图片上传可打开此注释 */

//这一块的函数是上传下载的函数
function upload(modelName){
	$(document).on('change','#uploadPhotoFile',function(){
		$.ajaxFileUpload({
			url:'../../cms/' + modelName + '/photoUpload',
			secureuri:false,
			fileElementId:'uploadPhotoFile',
			type:'POST',
			dataType: 'json',			
			success: function (data, status){
				if(data){
					var ds = data.object.split(":");
					if(ds[0] == 1){
						$("#imgUrl").val(ds[1]);
						$("#myImageShow").attr('src',"../../"+ds[1]);
						$("#myImageShow").attr('width','100px');
						$("#myImageShow").attr('height','100px');
					}else{
						alert(ds[1]);
					}
				}
			},
			error: function (data, status, e){
				alert(data);
			}
		});
	});
}

//返回函数
function backAction(backurl){
	if(getUrlParam('from')){
		location.href = getUrlParam('from');
	}else{
		window.location.href = backurl;
	}
}
//通过ID获取单条数据
function getDataById(url,tmpl,content){
	var tId=getUrlParam("id");//获取url中的参数，那么id是不是一个url?
	if(!tId){
	alert('id参数不能为空');
		return;
	}
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{
			modelId:tId//传过去的参数 名值对
		},
		success:function(data){
			if(data.code == 1){
				var contentHtml=$(tmpl).render(data.object,{mydata: data});
				$(content).html(contentHtml);
			}else{
				var contentHtml=$(tmpl).render("");
				$(content).html(contentHtml);
			}
		}
	});
}

//保存数据
function saveData(url,backUrl){
	$('#submit_form').isValid(function(result){
	    if(!result){
			alert("还有不符合规定的字段填写，请检查！");
			return;
	    }
		$.ajax({
			url:url,
			type:"POST",
			data:$("#submit_form").serialize(),
			success:function(data){
				if(data.code==1){
					backAction(backUrl);
				}else{
					alert(data.message);//显示错误数据
				}
			}
		});
	});
}