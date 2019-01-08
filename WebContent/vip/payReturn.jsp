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
<title>支付结果</title>
</head>
<body>
	<style>
button {
	margin-top: 20px;
}

h1 {
	margin-top: 20px;
	margin-bottom: 20px;
}
h3{
	color:green;
	margin-top:10px;
	margin-left:10px;
	margin-right:10px;
}
</style>
	<center>
		<h1>${payResult}</h1>
	</center>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell">订单号：${out_trade_no}</li>
		<li class="mui-table-view-cell">支付宝订单号：${trade_no}</li>
		<li class="mui-table-view-cell">金额：${amount}元</li>
		<li class="mui-table-view-cell">会员到期时间：${vipExpireAt}</li>
	</ul>
	<h3>如果您的支付从微信发起，请手动返回微信，点击"已完成支付"</h3>
</body>
</html>