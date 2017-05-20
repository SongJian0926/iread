function browseFolder(path) {
	try {
		var Message = "/u8bf7/u9009/u62e9/u6587/u4ef6/u5939"; //选择框提示信息
		var Shell;
		if(window.ActiveXObject){
				//支持-通过ActiveXObject的一个新实例来创建XMLHttpRequest对象
				Shell = new ActiveXObject("Shell.Application");
			}else if(window.XMLHttpRequest){
				Shell = new XMLHttpRequest()
			}
		}
		var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
		//var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
		if (Folder != null) {
			Folder = Folder.items(); // 返回 FolderItems 对象
			Folder = Folder.item(); // 返回 Folderitem 对象
			Folder = Folder.Path; // 返回路径
			if (Folder.charAt(Folder.length - 1) != "") {
				Folder = Folder + "";
			}
			document.getElementById(path).value = Folder;
			return Folder;
		}
	}catch (e) {
		alert(e.message);
	}
}