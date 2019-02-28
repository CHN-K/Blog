"use strict";
$(function() {

	var _pageSize; // 存储用于搜索

	// 根据用户名、页面索引、页面大小获取用户列表
	function getRoleByName(pageIndex, pageSize) {
		$.ajax({
			url : "/roles",
			contentType : 'application/json',
			data : {
				"async" : true,
				"pageIndex" : pageIndex,
				"pageSize" : pageSize,
				"name" : $("#searchName").val()
			},
			success : function(data) {
				$("#mainContainer").html(data);
			},
			error : function() {
				toastr.error("error!");
			}
		});
	}

	// 分页
	$.tbpage("#mainContainer", function(pageIndex, pageSize) {
		getRoleByName(pageIndex, pageSize);
		_pageSize = pageSize;
	});

	// 搜索
	$("#searchNameBtn").click(function() {
		getRoleByName(0, _pageSize);
	});

	// 获取添加用户的界面
	$("#addRole").click(function() {
		$.ajax({
			url : "/roles/add",
			success : function(data) {
				$("#roleFormContainer").html(data);
			},
			error : function(data) {
				toastr.error("error!");
			}
		});
	});
	//回车提交
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			$('#roleForm').each(function() {
				event.preventDefault();
			});
		}
	});
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			$('#submitEdit').click();
			//event.preventDefault();
		}
	});
	// 获取编辑用户的界面
	$("#rightContainer").on("click", ".blog-edit-role", function() {
		$.ajax({
			url : "/roles/edit/" + $(this).attr("roleId"),
			success : function(data) {
				$("#roleFormContainer").html(data);
			},
			error : function() {
				toastr.error("error!");
			}
		});
	});

	// 提交变更后，清空表单
	$("#submitEdit").click(function() {
		$.ajax({
			url : "/roles",
			type : 'POST',
			data : $('#roleForm').serialize(),
			success : function(data) {
				$('#roleForm')[0].reset();
				if (data.success) {
					// 从新刷新主界面
					getRoleByName(0, _pageSize);
				} else {
					toastr.error(data.message);
				}
			},
			error : function() {
				toastr.error("error!");
			}
		});
	});

	// 删除用户
	$("#rightContainer").on("click", ".blog-delete-role", function() {
		// 获取 CSRF Token
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url : "/roles/" + $(this).attr("roleId"),
			type : 'DELETE',
			beforeSend : function(request) {
				request.setRequestHeader(csrfHeader, csrfToken); // 添加 CSRF
				// Token
			},
			success : function(data) {
				if (data.success) {
					// 从新刷新主界面
					getRoleByName(0, _pageSize);
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