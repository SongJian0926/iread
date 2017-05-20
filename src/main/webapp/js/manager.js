$(document).ready(function(){ 
	//鼠标移动到项上改变背景颜色
	$(".box-list").on('hover','.value-item',function(){
//	$(".value-item").hover(function(){
		$(this).addClass('state-hover');
	},function(){
		$(this).removeClass('state-hover');
	});
	
	//选择项后加样式
	$(".box-list").on('click','.value-item',function(){
		if($(this).hasClass('state-selected')){
			$(this).removeClass('state-selected');
		}else{
			$(this).addClass('state-selected');
		}
		//提交按钮样式
		$('.button-submit').attr('disabled',false);
		$('.button-submit').removeClass('button-state-disabled');
		//全选按钮是否全选
		$('input[type=checkbox]').eq(0).prop('checked', $(".unjoin-list .value-item").length == $(".unjoin-list .state-selected").length);
		$('input[type=checkbox]').eq(1).prop('checked', $(".join-list .value-item").length == $(".join-list .state-selected").length);
		//选中个数
		$('.select-summary .num').eq(0).html($(".unjoin-list .state-selected").length);
		$('.select-summary .num').eq(1).html($(".join-list .state-selected").length);
		//左边ul点击后添加按钮样式
		if($(".unjoin-list .value-item").hasClass('state-selected')){
			$('.move-right-btn').attr('disabled',false);
			$('.move-right-btn').removeClass('button-state-disabled');
		}else{
			$('.move-right-btn').attr('disabled',true);
			$('.move-right-btn').addClass('button-state-disabled');
		}
		//右边ul点击后取消按钮样式
		if($(".join-list .value-item").hasClass('state-selected')){
			$('.move-left-btn').attr('disabled',false);
			$('.move-left-btn').removeClass('button-state-disabled');
		}else{
			$('.move-left-btn').attr('disabled',true);
			$('.move-left-btn').addClass('button-state-disabled');
		}
	});
	//左边全选事件
	$('input[type=checkbox]').eq(0).change(function () {
		if($(this).prop('checked')){
			$(".unjoin-list .value-item").addClass('state-selected');
		}else{
			$(".unjoin-list .value-item").removeClass('state-selected');
		}
    });
	//右边全选事件
    $('input[type=checkbox]').eq(1).change(function () {
		if($(this).prop('checked')){
			$(".join-list .value-item").addClass('state-selected');
		}else{
			$(".join-list .value-item").removeClass('state-selected');
		}
    });
    //添加事件点击
    $('.move-right-btn').click(function(){
    	var h = $(".unjoin-list .state-selected").parents("li");
    	$(".join-list").append(h);
    	$(".join-list .state-selected").removeClass("state-selected");
    });
    //取消事件点击
    $('.move-left-btn').click(function(){
    	var h = $(".join-list .state-selected").parents("li");
    	$(".unjoin-list").append(h);
    	$(".unjoin-list .state-selected").removeClass("state-selected");
    });
});