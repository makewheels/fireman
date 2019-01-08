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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../js/mui.min.js"></script>
<link href="../css/mui.min.css" rel="stylesheet" />
<script src="../js/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
<title>确认订单</title>
</head>
<body>
	<style>
button {
	margin-top: 20px;
}
</style>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell">订单号：${orderId}</li>
		<li class="mui-table-view-cell">用户名：${username}</li>
		<li class="mui-table-view-cell">名称：${subject}</li>
		<li class="mui-table-view-cell">金额：${amount}元</li>
	</ul>
	<a
		href="${pageContext.request.contextPath}/vip?method=requestAlipay&orderId=${orderId}">
		<button type="submit" class="mui-btn mui-btn-blue mui-btn-block">支付宝支付</button>
	</a>
</body>
</html>