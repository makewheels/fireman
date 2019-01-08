package alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;

/**
 * 支付宝工具类
 * 
 * @author Administrator
 *
 */
public class AlipayUtil {

	/**
	 * 返回手机网站支付表单
	 * 
	 * @param subject商品名
	 * @param orderId我的订单号
	 * @param amount金额
	 * @return
	 */
	public static String getWapForm(String subject, String orderId, String amount) {
		AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
				AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
				AlipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(orderId);
		model.setSubject(subject);
		model.setTotalAmount(amount);
		model.setProductCode("QUICK_WAP_WAY");
		alipay_request.setBizModel(model);
		alipay_request.setNotifyUrl(AlipayConfig.notify_url);
		alipay_request.setReturnUrl(AlipayConfig.return_url);
		String form = null;
		try {
			form = client.pageExecute(alipay_request).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return form;
	}

	public static void main(String[] args) {
		String wapForm = AlipayUtil.getWapForm("huaquan", System.currentTimeMillis() + "", "0.50");
		System.out.println(wapForm);
	}
}
