"use strict"; 
// DOM 加载完再执行
$(function() {
	
	var _pageSize; // 存储用于搜索
	
	// 根据用户名、页面索引、页面大小获取用户列表
	function getCommentByName(pageIndex, pageSize) {
		 $.ajax({ 
			 url: "/comments/commentm", 
			 contentType : 'application/json',
			 data:{
				 "async":true, 
				 "pageIndex":pageIndex,
				 "pageSize":pageSize,
				 "content":$("#searchName").val()
			 },
			 success: function(data){
				 $("#mainContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	
	// 分页
	$.tbpage("#mainContainer", function (pageIndex, pageSize) {
		getCommentByName(pageIndex, pageSize);
		_pageSize = pageSize;
	});
	
	// 搜索
	$("#searchNameBtn").click(function() {
		getCommentByName(0, _pageSize);
	});
	// 获取查看用户的界面
	$("#rightContainer").on("click",".blog-view-comment", function () { 
		$.ajax({ 
			 url: "/comments/view/" + $(this).attr("commentId"), 
			 success: function(data){
				 $("#commentFormContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	// 删除博客
	$("#rightContainer").on("click",".blog-delete-comment", function () { 
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		$.ajax({ 
			 url: "/comments/commentm/" + $(this).attr("commentId") , 
			 type: 'DELETE', 
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 // 从新刷新主界面
					 getCommentByName(0, _pageSize);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
});