package com.eg.fireman.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云发邮件工具类
 * 
 * @author Administrator
 *
 */
public class AliyunMailUtil {
	/**
	 * 发邮件
	 * 
	 * @param to收件人地址
	 * @param subject主题
	 * @param htmlBody内容
	 * @return
	 */
	public static String send(String to, String subject, String htmlBody) {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAISAs9wCvO4aoE",
				"Vtq5DPQK4CeDTQY6Nj2VdEKmaFpiGh");
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName("support@qbserver.cn");
			request.setFromAlias("查班神器");
			request.setAddressType(1);
			request.setTagName("label");
			request.setReplyToAddress(true);
			request.setToAddress(to);
			request.setSubject(subject);
			request.setHtmlBody(htmlBody);
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			String requestId = httpResponse.getRequestId();
			System.out.println("aliyun-mail-to:" + to);
			return requestId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
