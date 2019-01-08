<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html><head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8">
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-control" content="no-cache">
		<meta http-equiv="Cache" content="no-cache">
		<title>错误率最高的题</title>
		<script src="../../js/mui.min.js"></script>
		<link href="../../css/mui.min.css" rel="stylesheet">
		<script src="../../js/common.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" charset="utf-8">
			mui.init();
		</script>
	</head>

	<body>
		<center>
			<span style="margin-top:10px;margin-bottom:10px;font-size:19px;float:left;">没时间复习？刷的题多数是正确的在重复，没有针对性？想直达错题？快来看看别人错的最多的题吧！</span>
			<span style="margin-bottom:5px;margin-left:10px;font-size:22px;float:left;"><strong>说明：</strong></span>
			<span style="margin-bottom:15px;line-height:27px;font-size:22px;float:left;">从后台所有提交记录中，分别按三种题型，选出了<strong>错误率</strong>最高的前15%题目</span>
			<a href="${pageContext.request.contextPath}/easterEgg?name=mostWrong&method=toPage&questionType=multiple">
				<button type="button" class="mui-btn mui-btn-block">多选题：25道</button>
			</a>
			<a href="${pageContext.request.contextPath}/easterEgg?name=mostWrong&method=toPage&questionType=single">
				<button type="button" class="mui-btn mui-btn-block">单选题：100道</button>
			</a>
			<a href="${pageContext.request.contextPath}/easterEgg?name=mostWrong&method=toPage&questionType=check">
				<button type="button" class="mui-btn mui-btn-block">判断题：80道</button>
			</a>
			<a href="../easterEggIndex.html">
				<button type="button" class="mui-btn mui-btn-purple mui-btn-block">返回彩蛋列表</button>
			</a>
		</center>
	</body>
</html>