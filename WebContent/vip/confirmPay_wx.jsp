<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>确认订单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0"
    />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name='apple-touch-fullscreen' content='yes'>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
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
</head>

<body id="order-check" data-com="pagecommon">
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
	<button id="btn_alipay" class="mui-btn mui-btn-blue mui-btn-block">支付宝支付</button>
    <div id="pay-methods-panel" class="pay-methods-panel">
        <form id="pay_form" method="POST" action="https://openapi.alipay.com/gateway.do"> 
            <input type="hidden" name="format" value="json"/>
            <input type="hidden" name="sign" value="${sign}"/>
            <input type="hidden" name="sign_type" value="RSA2" />
            <input type="hidden" name="charset" value="UTF-8" />
            <input type="hidden" name="app_id" value="2018021402198213" />
            <input type="hidden" name="method" value="alipay.trade.wap.pay" />
            <input type="hidden" name="version" value="1.0" />
            <input type="hidden" name="timestamp" value="${timestamp}" />
            <input type="hidden" name="return_url" value="${return_url}" />
            <input type="hidden" name="notify_url" value="${notify_url}" />
        </form>
    </div>
    <script type="text/javascript" src="ap.js"></script>
    <script>
        var btn = document.querySelector(".J-btn-submit");
        document.getElementById("btn_alipay").addEventListener("tap", function (e) {
        	mui(this).button('loading');
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();
            var bizMap = {
                "out_trade_no":"${orderId}",
                "product_code":"QUICK_WAP_PAY",
                "subject":"${subject}",
                "total_amount":"${amount}"
            };
            var bizStr = JSON.stringify(bizMap);

            var queryParam = '';
            queryParam += 'biz_content=' + encodeURIComponent(bizStr);
            Array.prototype.slice.call(document.querySelectorAll("input[type=hidden]")).forEach(function (ele) {
                queryParam += '&' + ele.name + "=" + encodeURIComponent(ele.value);
            });
            var gotoUrl = document.querySelector("#pay_form").getAttribute('action') + '?' + queryParam;
            _AP.pay(gotoUrl);
            return false;
        }, false);
    </script>
</body>

</html>