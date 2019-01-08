<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=8">
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-control" content="no-cache">
		<meta http-equiv="Cache" content="no-cache">
		<title>会员专享</title>
		<script src="../js/mui.min.js"></script>
		<link href="../css/mui.min.css" rel="stylesheet" />
		<script src="../js/common.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" charset="utf-8">
			mui.init();
		</script>
	</head>

	<body>
		<center>
			<br />
			<h2>您可在开通会员后使用<br/><br/><span style="color:red;">${msg}</span></h2>
			<br />
			<a href="vipIndex.html">
				<button type="button" class="mui-btn mui-btn-blue mui-btn-block">前往会员中心</button>
			</a>
		</center>
	</body>

</html>