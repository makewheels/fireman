package com.eg.fireman.util;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

public class SmsSender {
	public static boolean sendBindPhone(String phone, String code) {
		SendSmsResponse response = AliyunSmsUtil.sendSms(phone, AliyunSmsUtil.TEMPLATE_BIND_PHONE,
				"{\"code\":\"" + code + "\"}");
		String responseCode = response.getCode();
		if (responseCode != null && responseCode.equalsIgnoreCase("OK")) {
			return true;
		} else {
			return false;
		}
	}
}
