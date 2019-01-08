<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<title>我的错题本</title>
<script src="../js/mui.min.js"></script>
<link href="../css/mui.min.css" rel="stylesheet" />
<script src="../js/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
</head>

<body>
	<center>
		<h3>
			总错题：<span id="sp_total"></span>道
		</h3>
		<h3>
			多选：<span id="sp_multiple"></span>道
		</h3>
		<h3>
			单选：<span id="sp_single"></span>道
		</h3>
		<h3>
			判断：<span id="sp_check"></span>道
		</h3>
	</center>
	<a href="${pageContext.request.contextPath}/wrong?method=toMultiple">
		<button type="button" class="mui-btn mui-btn-block">错题重做：多选</button>
	</a>
	<a href="${pageContext.request.contextPath}/wrong?method=toSingle">
		<button type="button" class="mui-btn mui-btn-block">错题重做：单选</button>
	</a>
	<a href="${pageContext.request.contextPath}/wrong?method=toCheck">
		<button type="button" class="mui-btn mui-btn-block">错题重做：判断</button>
	</a>
	<button id="btn_wrongGarbage" type="button" class="mui-btn mui-btn-block">错题垃圾箱</button>
	<a href="../index.html">
		<button type="button" class="mui-btn mui-btn-purple mui-btn-block">返回主页</button>
	</a>
	<script type="text/javascript">
		mui.post(baseurl + '/getWrongQuestionInfo', {}, function(data) {
			if (data.result == "toLogin") {
				window.location.href = "../login.html";
				return;
			}
			document.getElementById("sp_total").innerHTML = data.total;
			document.getElementById("sp_multiple").innerHTML = data.multiple;
			document.getElementById("sp_single").innerHTML = data.single;
			document.getElementById("sp_check").innerHTML = data.check;
		}, 'json');
		document.getElementById("btn_wrongGarbage").addEventListener('tap', function() {
			mui.alert("Hello World!","敬请期待");
		});
	</script>
</body>

</html>