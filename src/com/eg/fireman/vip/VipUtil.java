package com.eg.fireman.vip;

import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.RandomUtil;

public class VipUtil {
	/**
	 * 用户是否是vip
	 * 
	 * @param user
	 * @return
	 */
	public static boolean isVip(String userId) {
		// 查数据库，拿到user
		User userByUserId = UserJdbc.getUserByUserId(userId);
		// 是vip，并且没到期
		if (userByUserId.getIsVip() == 1) {
			long currentTimeMillis = System.currentTimeMillis();
			long vipExpireAt = Long.parseLong(userByUserId.getVipExpireAt());
			if (vipExpireAt > currentTimeMillis) {
				return true;
			}
		}
		return false;
	}

	public static long updateUserVipState(String userId, long days) {
		User userByUserId = UserJdbc.getUserByUserId(userId);
		long lengthTimestamp = days * 24 * 60 * 60 * 1000;
		long startTimestamp = System.currentTimeMillis();
		String vipExpireAt = userByUserId.getVipExpireAt();
		// 如果之前已经开过会员
		if (vipExpireAt != null) {
			long expireAt = Long.parseLong(vipExpireAt);
			// 如果会员还没到期
			if (System.currentTimeMillis() < expireAt) {
				startTimestamp = expireAt;
			}
		}
		// 到这里的是，两种情况：
		// 1，以前没开过会员
		// 2，以前开过会员，但是现在已经过期
		// 修改用户的会员到期时间
		long updateExpireAt = startTimestamp + lengthTimestamp;
		VipJdbc.updateExpireAtByUserId(userId, updateExpireAt + "");
		return updateExpireAt;
	}

	/**
	 * 创建支付宝订单
	 * 
	 * @param userId
	 * @param day
	 * @return
	 */
	public static AlipayOrder createAlipayOrder(String userId, int day, String userAgent) {
		AlipayOrder alipayOrder = new AlipayOrder();
		alipayOrder.setUserId(userId);
		alipayOrder.setDay(day);
		String amountString = VipConstants.PRICE_15;
		if (day == 30) {
			amountString = VipConstants.PRICE_30;
		}
		alipayOrder.setAmountDouble(Double.parseDouble(amountString));
		alipayOrder.setAmountString(amountString);
		alipayOrder.setOrderId(RandomUtil.getUuid());
		alipayOrder.setState(0);
		alipayOrder.setUserAgent(userAgent);
		VipJdbc.saveAlipayOrder(alipayOrder);
		return alipayOrder;
	}

}
