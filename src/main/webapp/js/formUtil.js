
function filterForm(formDom){
	
	var formData = $(formDom).serialize();
	
	for(var i = 0 ;i < formDom.length ; i++){
		
		if($(formDom[i]).val()==null || $(formDom[i]).val()==""){
			
			var formName = formDom[i].name;
			
			var indexNull = formData.indexOf(formName+"=&");
			
			var strLen = formName.length;
			
			if(indexNull!=-1){
				
				formData = formData.substring(0,indexNull) +   formData.substring(indexNull + strLen + 2);
				alert(formData);
			}else{
				
				indexNull = formData.indexOf("&"+formName+"=");
				
				if(indexNull!=-1){
					
					formData = formData.substring(0,indexNull) +   formData.substring(indexNull + strLen + 2);
				}
				
			}
			
		}
		
	}
	
	return formData;
}

