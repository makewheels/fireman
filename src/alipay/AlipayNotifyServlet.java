package alipay;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.eg.fireman.util.AliyunMailUtil;
import com.eg.fireman.vip.AlipayOrder;
import com.eg.fireman.vip.VipJdbc;
import com.eg.fireman.vip.VipUtil;

/**
 * 支付宝notify商户服务器
 * 
 * @author Administrator
 *
 */
public class AlipayNotifyServlet extends HttpServlet {
	private static final long serialVersionUID = 2369242728294935328L;

	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
		// 商户订单号
		System.out.println("start nofify");
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("out_trade_no: " + out_trade_no);
		// 买家支付时间
		String gmt_payment = request.getParameter("gmt_payment");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String orderPayTime = null;
		try {
			orderPayTime = simpleDateFormat.parse(gmt_payment).getTime() + "";
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("trade_no: " + trade_no);
		// 订单金额
		String total_amount = request.getParameter("total_amount");
		// 实收金额
		String receipt_amount = request.getParameter("receipt_amount");
		// 付款金额
		String buyer_pay_amount = request.getParameter("buyer_pay_amount");
		// 买家支付宝用户号
		String alipayBuyerId = request.getParameter("buyer_id");
		// 买家支付宝账号
		String alipayBuyerAccount = request.getParameter("buyer_logon_id");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
		boolean verify_result = false;
		try {
			verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
					"RSA2");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		if (verify_result) {
			// 验签名成功
			// 请在这里加上商户的业务逻辑程序代码
			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED")) {
				// 交易结束，不可退款
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				// 如果有做过处理，不执行商户的业务程序
				// 注意：
				// 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				// 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// 交易支付成功
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				// 如果有做过处理，不执行商户的业务程序
				// 注意：
				// 如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				// 修改数据库中的的订单信息
				AlipayOrder alipayOrder = new AlipayOrder();
				alipayOrder.setAlipayOrderId(trade_no);
				alipayOrder.setOrderId(out_trade_no);
				alipayOrder.setAlipayBuyerPayAmount(buyer_pay_amount);
				alipayOrder.setAlipayReceiptAmount(receipt_amount);
				alipayOrder.setAlipayBuyerId(alipayBuyerId);
				alipayOrder.setAlipayPayTime(orderPayTime);
				alipayOrder.setAlipayState(trade_status);
				alipayOrder.setAlipayBuyerAccount(alipayBuyerAccount);
				alipayOrder.setAlipayAmount(total_amount);
				alipayOrder.setParameterMapJsonString(JSON.toJSONString(request.getParameterMap()));
				VipJdbc.updateAlipayOrderByOrderId(alipayOrder);
				AlipayOrder alipayOrderByOrderId = VipJdbc.getAlipayOrderByOrderId(out_trade_no);
				// 修改数据库中的用户信息，延长vip时间
				VipUtil.updateUserVipState(alipayOrderByOrderId.getUserId(), alipayOrderByOrderId.getDay());
				// 发通知
				new Thread() {
					@Override
					public void run() {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						AliyunMailUtil.send("finalbird@foxmail.com",
								simpleDateFormat.format(new Date()) + " FireMan有人开会员",
								alipayOrder.getAlipayReceiptAmount() + "<br>" + alipayOrder.toString());
					}
				}.start();
			}
			out.flush();
			out.println("success");
			System.out.println("notify success");
		} else {
			out.println("fail");
			System.out.println("notify fail");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
